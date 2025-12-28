import { Component, computed, inject, signal } from '@angular/core';
import { DashboardService } from '../../services/dashboardService';
import { toObservable, toSignal } from '@angular/core/rxjs-interop';
import { CurrencyPipe, DatePipe } from '@angular/common';
import { combineLatest, switchMap, tap } from 'rxjs';
import { ChartData} from 'chart.js';
import { BaseChartDirective } from "ng2-charts";
import { createChartOptions } from '../../../shared/utils/chart-config';
import { Loading } from "../../../shared/components/loading/loading";

@Component({
  selector: 'app-dashboard',
  imports: [BaseChartDirective, CurrencyPipe, DatePipe, Loading],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.scss',
})
export class Dashboard {

  private service = inject(DashboardService);

  year = signal(new Date().getFullYear());
  month = signal(new Date().getMonth() + 1);

  isAllTime = signal(false);
  sizeList = signal<number | null> (null);

  isExpensesListLoading = signal(false);
  isAllocationLoading = signal(false);
  isEvolutionLoading = signal(false);

  // create a observable that listen the change in both signals
  private filters$ = combineLatest({
    year: toObservable(this.year),
    month: toObservable(this.month),
    allTime: toObservable(this.isAllTime),
    sizeList: toObservable(this.sizeList)
  });
  
  allocation = toSignal(
    this.filters$.pipe(

      tap(() => this.isAllocationLoading.set(true)),

      switchMap(({ year, month, allTime }) => {

        const obs$ = allTime
         ? this.service.getAllocation(null, null)
         : this.service.getAllocation(year, month);

        return obs$.pipe(
          tap(() => this.isAllocationLoading.set(false))
        ) 
      })
    ),

    { initialValue: [] }
  );

  summary = toSignal(
    this.filters$.pipe(
      switchMap(({ year, month, allTime}) => {
        
        if(allTime){
          return this.service.getSummary(null, null);
        }
        return this.service.getSummary( year, month);
      })
    ),
    { initialValue: [] }
  );

  evolution = toSignal(
    this.filters$.pipe(

      tap(() => this.isEvolutionLoading.set(true)),

      switchMap(({ year, allTime }) => {

        const obs$ = allTime ?
        this.service.getEvolution(null) :
        this.service.getEvolution(year);

        return obs$.pipe(
          tap(() => this.isEvolutionLoading.set(false))
        )
      })
    )
  )

  topExpenses = toSignal(
    this.filters$.pipe(

      tap(() => this.isExpensesListLoading.set(true)),

      switchMap(({ year, month, allTime, sizeList }) => {

        const obs$ = allTime
         ? this.service.getTopExpenses(null, null, sizeList)
         : this.service.getTopExpenses(year, month, sizeList);

         return obs$.pipe(
          tap(() => this.isExpensesListLoading.set(false))
         )
      })
    )
  )

  updateSizeList(value: string){

    // decimal
    const size = parseInt(value, 10);

    if(!isNaN(size) && size > 0){
      this.sizeList.set(size);
    } else {
      this.sizeList.set(null);
    }
  }

  // take the first item of list 
  currentSummary = computed(() =>{
    const list = this.summary();
    return list && list.length > 0 ? list[0] : null;
  })

  balance = computed(() =>{
    const sum = this.currentSummary();
    if (!sum) return 0;
    return (sum.totalDeposit || 0) - (sum.totalWithdraw || 0);
  })

  kpiData = computed(() =>{

    const list = this.summary();

    if(!list || list.length === 0){
      return { totalDeposit: 0, totalWithdraw: 0, balance: 0}
    }
    
    const aggregated = list.reduce((acc, cur) =>{
      return {
        totalDeposit: acc.totalDeposit + cur.totalDeposit,
        totalWithdraw: acc.totalWithdraw + cur.totalWithdraw        
      };
    }, {totalDeposit: 0, totalWithdraw: 0}) // initial values

    const balance = aggregated.totalDeposit - aggregated.totalWithdraw;

    return { ...aggregated, balance}
  });

  periodLabel = computed(() => {
      if (this.isAllTime()) {
        return 'Histórico Completo';
      }
      return new Date(this.year(), this.month() - 1, 1);
  });


  pieChartOptions = computed(() => {
    const titulo = this.isAllTime() 
      ? 'Top 5 Categorias com mais despesas (Histórico)' 
      : `Top 5 Categorias com mais despesas de ${this.month()}/${this.year()}`;
    return createChartOptions(titulo);
  });

  barChartOptions = computed(() => {
    return createChartOptions('Evolução Anual: Receitas vs Despesas');
  });


  chartAllocationData = computed<ChartData<'doughnut'>>(() =>{
    const rawData = this.allocation();

    const labels = rawData.map(item => item.name);
    const data = rawData.map(item => item.total);

    return {
      labels: labels,
      datasets: [{
        data: data,
        backgroundColor: [ 
          '#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0', '#9966FF', '#FF9F40'
        ],
        hoverBackgroundColor: [
          '#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0', '#9966FF', '#FF9F40'
        ]
      }]
    };
    
  });

  chartEvolutionData = computed<ChartData<'bar'>>(() => {

    const rawData = this.evolution() ?? [];

    const getDateName = (monthIndex: number) =>{
      const date = new Date(2000, monthIndex - 1, 1);
      return date.toLocaleString('pt-BR', { month: 'short'});
    };

    return {
      labels: rawData.map(i => getDateName(i.month)),
      datasets: [
        {
          label: 'Receitas',
          data: rawData.map(i => i.income),
          backgroundColor: '#2979FF'
        },
        {
          label: 'Despesas',
          data: rawData.map(i => i.expense),
          backgroundColor: '#E53935'
        }
      ]
    };
  });

}
