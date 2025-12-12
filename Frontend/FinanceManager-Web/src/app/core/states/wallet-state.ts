import { inject, Injectable, signal } from '@angular/core';
import { BalanceDTO, WalletResponseDTO } from '../models/wallet';
import { Wallet } from '../services/wallet';
import { catchError, tap, throwError } from 'rxjs';
import { Transaction } from '../services/transaction';
import { TransactionResponseDTO, WithdrawDTO } from '../models/transaction';
import { WithdrawCategoryResponseDTO } from '../services/withdraw-category';

@Injectable({
  providedIn: 'root',
})
export class WalletState {

  private walletService = inject(Wallet);
  private transactioService = inject(Transaction);

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
    return this.transactioService.depositBalance(dto).pipe(
      tap((data: TransactionResponseDTO) => {
        const dto = {
          balance: data.newBalance,
          userId: data.userId
        };
        console.log("State: updating wallet data.. ", data);
        this._wallet.set(dto);
      }),
        catchError(err =>{
        console.error("State: error on add balance request.. ", err);
        return throwError(() => err);
       })
    )
  }  

  public withdrawBalance(dto: WithdrawDTO){
    return this.transactioService.withdrawBalance(dto).pipe(
      tap((data: TransactionResponseDTO) =>{
        const dto = {
          balance: data.newBalance,
          userId: data.userId
        };
        console.log("State: updating wallet data.. ", data);
        this._wallet.set(dto);
      }),
      catchError(err =>{
        console.error("State: error on withdraw balance request.. ", err);
        return throwError(() => err);
       })
    )
  }
}
