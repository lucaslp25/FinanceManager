import { inject, Injectable, signal } from '@angular/core';
import { BalanceDTO, WalletResponseDTO } from '../models/wallet';
import { Wallet } from '../services/wallet';
import { catchError, tap, throwError } from 'rxjs';
import { TransactionResponseDTO, WithdrawDTO } from '../models/transaction';
import { TransactionState } from './transaction-state';

@Injectable({
  providedIn: 'root',
})
export class WalletState {

  private walletService = inject(Wallet);
  private transactionState = inject(TransactionState);

  private _wallet = signal<WalletResponseDTO | null>(null);

  public myWallet = this._wallet.asReadonly();

  // Load wallet

  public loadMyWallet(){
    return this.walletService.getWallet().pipe(
      tap((data: WalletResponseDTO) => {
        console.log("State: updating wallet data.. ", data);
        this._wallet.set(data);
      }),

      catchError(err =>{
        console.error("State: error on wallet request.. ", err);
        return throwError(() => err);
      })
    )
  };

  public addBalance(dto: BalanceDTO){
    this.transactionState.addBalance(dto).subscribe({
      next: (next) => {
          const dto = {
          balance: next.newBalance,
          userId: next.userId
        };
        this._wallet.set(dto);
      }
    });
  }  

  public withdrawBalance(dto: WithdrawDTO){
    this.transactionState.withdrawBalance(dto).subscribe({
         next: (next) => {
          const dto = {
          balance: next.newBalance,
          userId: next.userId
        };
        this._wallet.set(dto);
      }
    })    
  }
}
