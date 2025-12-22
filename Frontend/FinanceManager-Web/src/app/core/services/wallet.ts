import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { BalanceDTO, WalletResponseDTO } from '../models/wallet';
import { Observable } from 'rxjs';
import { WithdrawCategory } from './withdraw-category';
import { environment } from '../../../environments/environment.development';

@Injectable({
  providedIn: 'root',
})
export class Wallet {

  private http = inject(HttpClient);

  private UrlBase = `${environment.apiUrl}/wallet`;

  public getWallet(): Observable<WalletResponseDTO>{
    return this.http.get<WalletResponseDTO>(`${this.UrlBase}/my-wallet`);
  }
}
