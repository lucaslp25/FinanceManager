import { inject, Injectable, signal } from '@angular/core';
import { Transaction } from '../services/transaction';
import { TransactionEditDTO, TransactionResponseDTO, WithdrawDTO, BalanceDTO } from '../models/transaction';
import { catchError, tap, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class TransactionState {

  private service = inject(Transaction);

  private _expenses = signal<TransactionResponseDTO[]>([]);
  public myExpenses = this._expenses.asReadonly();

  private _deposits = signal<TransactionResponseDTO[]>([])
  public myDeposits = this._deposits.asReadonly(); 

  private _transactions = signal<TransactionResponseDTO[]>([]);
  public transactions = this._transactions.asReadonly();

  public loadExpenses(){
    return this.service.loadMyExpenseList().subscribe({
      next: (next) => {
        console.log('Expense list loaded.');
        this._expenses.set(next);
      },
      error: (err) => console.error('Error in load expense list.', err)
    })
  }
  public loadAllTransactios(){
    return this.service.loadAllTransactions().subscribe({
      next: (next) => {
        console.log('Transactios list loaded.');
        this._transactions.set(next);
      },
      error: (err) => console.error('Error in load transactions list.', err)
    })
  }
  
  public editTransaction(dto: TransactionEditDTO, id: string){

    if (id){
      this.service.editTransaction(dto, id).subscribe({
        next: (next) => {
          console.log('updating expense...');

          if(dto.transactionType === 'WITHDRAW'){
            this._expenses.update(currentList => 
              currentList.map(item => item.transactionId === id ? next : item))  
          } else {
            this._deposits.update(currentList => 
              currentList.map(item => item.transactionId === id ? next : item))
          }

          this._transactions.update(currentList => 
          currentList.map(item => item.transactionId === id ? next : item))
        },
        error: (err) => console.error('Error in update the expense. ', err)
      })
    }
  }

  public deleteTransaction(transactionId: string){

    if (transactionId){
      this.service.deleteTransaction(transactionId).subscribe({
        next: (next) => {
          console.log('Excluding Transaction...');
          this._expenses.update(currentList => currentList.filter(item => item.transactionId !== transactionId));
          this._deposits.update(currentList => currentList.filter(item => item.transactionId !== transactionId));
          this._transactions.update(currentList => currentList.filter(item => item.transactionId !== transactionId));
        },
        error: (err) => console.error('Error in delete transaction request...', err)
      })
    }
  }

  public addBalance(dto: BalanceDTO){
      return this.service.depositBalance(dto).pipe(
        tap((data: TransactionResponseDTO) => {
          console.log("State: updating wallet data.. ", data);
        }),
          catchError(err =>{
          console.error("State: error on add balance request.. ", err);
          return throwError(() => err);
         })
      )
    }  
  
    public withdrawBalance(dto: WithdrawDTO){
      return this.service.withdrawBalance(dto).pipe(
        tap((data: TransactionResponseDTO) =>{
          console.log("State: updating wallet data.. ", data);
        }),
        catchError(err =>{
          console.error("State: error on withdraw balance request.. ", err);
          return throwError(() => err);
         })
      )
    }
  
}
