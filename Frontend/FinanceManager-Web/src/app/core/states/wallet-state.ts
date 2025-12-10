import { inject, Injectable, signal } from '@angular/core';
import { BalanceDTO, WalletResponseDTO } from '../models/wallet';
import { Wallet } from '../services/wallet';
import { catchError, tap, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class WalletState {

  private service = inject(Wallet);

  private _wallet = signal<WalletResponseDTO | null>(null);

  public myWallet = this._wallet.asReadonly();

  // Load wallet

  public loadMyWallet(){
    return this.service.getWallet().pipe(
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
    return this.service.addBalance(dto).pipe(
      tap((data: WalletResponseDTO) =>{
        console.log("State: updating wallet data.. ", data);
        this._wallet.set(data);
      }),
       catchError(err =>{
        console.error("State: error on add balance request.. ", err);
        return throwError(() => err);
      })
    )
  }

  
}
