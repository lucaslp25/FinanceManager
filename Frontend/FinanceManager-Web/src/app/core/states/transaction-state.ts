import { inject, Injectable, signal } from '@angular/core';
import { Transaction } from '../services/transaction';
import { WithdrawTransactionEditDTO, WithdrawTransactionResponseDTO } from '../models/transaction';

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
  
}
