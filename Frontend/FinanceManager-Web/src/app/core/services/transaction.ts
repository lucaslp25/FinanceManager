import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { BalanceDTO } from '../models/wallet';
import { catchError, Observable, throwError } from 'rxjs';
import { TransactionResponseDTO, WithdrawDTO, WithdrawTransactionResponseDTO } from '../models/transaction';

@Injectable({
  providedIn: 'root',
})
export class Transaction {

  private http = inject(HttpClient);

  private UrlBase = 'http://localhost:8080/api/transaction';

  public depositBalance(dto: BalanceDTO):Observable<TransactionResponseDTO>{
    return this.http.post<TransactionResponseDTO>(`${this.UrlBase}/deposit`, dto)
    .pipe(catchError(this.handleError));
  }
  
  public withdrawBalance(dto: WithdrawDTO):Observable<TransactionResponseDTO>{
    return this.http.post<TransactionResponseDTO>(`${this.UrlBase}/withdraw`, dto)
    .pipe(catchError(this.handleError));
  }
  
  public loadMyExpenseList(): Observable<WithdrawTransactionResponseDTO[]>{
    return this.http.get<WithdrawTransactionResponseDTO[]>(`${this.UrlBase}/withdraw-transactions`)
    .pipe(catchError(this.handleError));
  }

  handleError(err: any){
      console.error('FinanceAutentication Error: ', err);
      return throwError(() => err);
  }
  
}
