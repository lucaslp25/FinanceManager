import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { BalanceDTO, WalletResponseDTO } from '../models/wallet';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class Wallet {

  private http = inject(HttpClient);

  private baseURL = 'http://localhost:8080/api/wallet';

  public getWallet(): Observable<WalletResponseDTO>{
    return this.http.get<WalletResponseDTO>(`${this.baseURL}/my-wallet`);
  }

  public addBalance(dto: BalanceDTO): Observable<WalletResponseDTO>{
    return this.http.patch<WalletResponseDTO>(`${this.baseURL}/my-wallet/add`, dto);
  }

  public decreaseBalance(dto: BalanceDTO): Observable<WalletResponseDTO>{
    return this.http.patch<WalletResponseDTO>(`${this.baseURL}/my-wallet/decrease`, dto);
  }
  
}
