import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { LoginDTO, LoginResponseDTO, RegisterDTO, RegisterResponseDTO } from '../models/auth';
import { catchError, Observable, throwError } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  private http = inject(HttpClient);

  private baseURL = `${environment.apiUrl}/auth`;

  login(credentials: LoginDTO): Observable<LoginResponseDTO>{
    return this.http.post<LoginResponseDTO>(`${this.baseURL}/login`, credentials)
    .pipe(catchError(this.handleError));
  }

  register(credentials: RegisterDTO): Observable<RegisterResponseDTO>{
    return this.http.post<RegisterResponseDTO>(`${this.baseURL}/register`, credentials)
    .pipe(catchError(this.handleError));
  }
  
  emailConfirmation(token: string): Observable<Boolean>{
    return this.http.post<Boolean>(`${this.baseURL}/${token}/verification`, token)
    .pipe(catchError(this.handleError));
  }
  
  enableAccount(token: string): Observable<any>{
    return this.http.patch<any>(`${this.baseURL}/enable-user`, token)
    .pipe(catchError(this.handleError));
  }
  
  handleError(err: any){
    console.error('FinanceAutentication Error: ', err);
    return throwError(() => err);
  }
}
