import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';
import { environment } from '../../../environments/environment.development';

export interface WithdrawCategoryResponseDTO {
  id: number;
  name: string;
}

export interface WithdrawCategoryDTO {
  name: string;
}

@Injectable({
  providedIn: 'root',
})
export class WithdrawCategory {

  private http = inject(HttpClient);
  private UrlBase = `${environment.apiUrl}/withdrawcategory`;

  // load
  public loadWithdrawCategories(): Observable<WithdrawCategoryResponseDTO[]>{
    return this.http.get<WithdrawCategoryResponseDTO[]>(this.UrlBase)
    .pipe(catchError(this.handleError));
  };

  // create 
  public createCategory(name: string){
    const dto = {
      name
    };
    return this.http.post<WithdrawCategoryResponseDTO>(`${this.UrlBase}/create `, dto)
    .pipe(catchError(this.handleError));
  }

  public updateCategory(name: string, id: number){
    const dto = {
      name
    };
    return this.http.put<WithdrawCategoryResponseDTO>(`${this.UrlBase}/${id}/update`, dto)
    .pipe(catchError(this.handleError));
  }

  public deleteCategory(id: number){
    return this.http.delete<WithdrawCategoryResponseDTO>(`${this.UrlBase}/${id}/delete`)
    .pipe(catchError(this.handleError));
  }


    handleError(err: any){
      console.error('FinanceAutentication Error: ', err);
      return throwError(() => err);
    }
}
