import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { catchError, Observable, throwError } from 'rxjs';

export interface DepositCategoryResponseDTO{
  id: number;
  name: string;
}
export interface DepositCategoryDTO{
  name: string;
}

@Injectable({
  providedIn: 'root',
})
export class DepositCategoryService {

  
  private http = inject(HttpClient);
  private UrlBase = `${environment.apiUrl}/deposit-category`;

  // load
  public loadDepositCategories(): Observable<DepositCategoryResponseDTO[]>{
    return this.http.get<DepositCategoryResponseDTO[]>(this.UrlBase)
    .pipe(catchError(this.handleError));
  };

  // create 
  public createCategory(name: string){
    const dto = {
      name
    };
    return this.http.post<DepositCategoryResponseDTO>(`${this.UrlBase}/create `, dto)
    .pipe(catchError(this.handleError));
  }

  public updateCategory(name: string, id: number){
    const dto = {
      name
    };
    return this.http.put<DepositCategoryResponseDTO>(`${this.UrlBase}/${id}/update`, dto)
    .pipe(catchError(this.handleError));
  }

  public deleteCategory(id: number){
    return this.http.delete<DepositCategoryResponseDTO>(`${this.UrlBase}/${id}/delete`)
    .pipe(catchError(this.handleError));
  }

  handleError(err: any){
    console.error('FinanceAutentication Error: ', err);
    return throwError(() => err);
  }
  
}
