import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { TransactionEditDTO, TransactionResponseDTO, WithdrawDTO, BalanceDTO } from '../models/transaction';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class Transaction {

  private http = inject(HttpClient);

  private UrlBase = `${environment.apiUrl}/transaction`;

  public depositBalance(dto: BalanceDTO):Observable<TransactionResponseDTO>{
    return this.http.post<TransactionResponseDTO>(`${this.UrlBase}/deposit`, dto)
    .pipe(catchError(this.handleError));
  }
  
  public withdrawBalance(dto: WithdrawDTO):Observable<TransactionResponseDTO>{
    return this.http.post<TransactionResponseDTO>(`${this.UrlBase}/withdraw`, dto)
    .pipe(catchError(this.handleError));
  }
  
  public loadMyExpenseList(): Observable<TransactionResponseDTO[]>{
    return this.http.get<TransactionResponseDTO[]>(`${this.UrlBase}/withdraw-transactions`)
    .pipe(catchError(this.handleError));
  }

  public loadMyDepositList(): Observable<TransactionResponseDTO[]>{
    return this.http.get<TransactionResponseDTO[]>(`${this.UrlBase}/deposit-transactions`)
    .pipe(catchError(this.handleError));
  }
  
  public loadAllTransactions(): Observable<TransactionResponseDTO[]>{
    return this.http.get<TransactionResponseDTO[]>(`${this.UrlBase}`)
    .pipe(catchError(this.handleError));
  }
  
  public editTransaction(dto: TransactionEditDTO, transactionId: string): Observable<TransactionResponseDTO>{
    return this.http.patch<TransactionResponseDTO>(`${this.UrlBase}/${transactionId}/edit`, dto)
    .pipe(catchError(this.handleError));
  }
  
  public deleteTransaction(transactionId: string): Observable<void>{
    return this.http.delete<void>(`${this.UrlBase}/${transactionId}/delete`)
    .pipe(catchError(this.handleError));
  }

  handleError(err: any){
      console.error('FinanceAutentication Error: ', err);
      return throwError(() => err);
  }
  
}
