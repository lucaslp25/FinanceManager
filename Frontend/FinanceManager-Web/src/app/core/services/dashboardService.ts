import { inject, Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface DashAllocationResponseDTO {
  name: string;
  total: number;
}

export interface DashSummaryDTO {
  year: number;
  month: number;
}

export interface DashSummaryResponseDTO {
  ano: number;
  mes: number;
  totalDeposit: number;
  totalWithdraw: number;
}

export interface AllocationDTO {
  year: number;
  month: number;
}

export interface DashEvolutionDTO{
  month: number;
  income: number;
  expense: number;
}

export interface DashTransactionDTO{
  categoryName: string;
  description: string;
  amount: number;
  date: Date;
}


@Injectable({
  providedIn: 'root',
})
export class DashboardService {

  private http = inject(HttpClient);
  urlBase = `${environment.apiUrl}/dashboard`

  public getAllocation(year: number | null, month: number | null): Observable<DashAllocationResponseDTO[]>{

    let dto: any = {};

    if (year && month){
      dto = {
        year: year,
        month: month
      }
    }

    return this.http.get<DashAllocationResponseDTO[]>(`${this.urlBase}/category-allocation`, {
      params: { ...dto }
    })
  }
  
  public getSummary(year: number | null, month: number | null): Observable<DashSummaryResponseDTO[]>{

    let dto: any = {};

    if (year && month){
      dto = {
        year: year,
        month: month
      }
    }

    return this.http.get<DashSummaryResponseDTO[]>(`${this.urlBase}/summary`, {
      params: { ... dto}
    })
  }

  public getEvolution(year: number | null): Observable<DashEvolutionDTO[]> {
    let params = new HttpParams();

    if (year !== null) {
      params = params.set('year', year.toString());
    }

    return this.http.get<DashEvolutionDTO[]>(`${this.urlBase}/evolution`, { params });
  }

  public getTopExpenses(year: number | null,  month: number | null, size: number | null) : Observable<DashTransactionDTO[]>{
    let queryParams: any = {};

    if (year && month){
      queryParams.year = year;
      queryParams.month = month;
    }

    if (size){
      queryParams.size = size;
    }

    return this.http.get<DashTransactionDTO[]>(`${this.urlBase}/top-expenses`, { params: queryParams } )
  }


}
