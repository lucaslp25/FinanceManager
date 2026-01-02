import { inject, Injectable, signal } from '@angular/core';
import { Transaction } from '../services/transaction';
import { TransactionResponseDTO, WithdrawDTO, WithdrawTransactionEditDTO, WithdrawTransactionResponseDTO } from '../models/transaction';
import { BalanceDTO } from '../models/wallet';
import { catchError, tap, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class TransactionState {

  private service = inject(Transaction);

  private _expenses = signal<WithdrawTransactionResponseDTO[]>([]);
  public myExpenses = this._expenses.asReadonly();

  public loadExpenses(){
    return this.service.loadMyExpenseList().subscribe({
      next: (next) => {
        console.log('Expense list loaded.');
        this._expenses.set(next);
      },
      error: (err) => console.error('Error in load expense list.', err)
    })
  }
  
  public editWithdrawTransaction(dto: WithdrawTransactionEditDTO, id: string){

    if (id){
      this.service.editTransaction(dto, id).subscribe({
        next: (next) => {
          console.log('updating expense...');

          this._expenses.update(currentList => 
            currentList.map(item => item.transactionId === id ? next : item))
        },
        error: (err) => console.error('Error in update the expense. ', err)
      })
    }
  }

  public deleteWithdrawTransactio(transactionId: string){

    if (transactionId){
      this.service.deleteTransaction(transactionId).subscribe({
        next: (next) => {
          console.log('Excluding Transaction...');
          this._expenses.update(currentList => currentList.filter(item => item.transactionId !== transactionId));
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
