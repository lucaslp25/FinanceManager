import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { catchError, Observable, throwError } from 'rxjs';

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
  private apiUrl = 'http://localhost:8080/api/withdrawcategory';

  // load
  public loadWithdrawCategories(): Observable<WithdrawCategoryResponseDTO[]>{
    return this.http.get<WithdrawCategoryResponseDTO[]>(this.apiUrl)
    .pipe(catchError(this.handleError));
  };

  // create 
  public createCategory(name: string){
    const dto = {
      name
    };
    return this.http.post<WithdrawCategoryResponseDTO>(`${this.apiUrl}/create `, dto)
    .pipe(catchError(this.handleError));
  }

  public updateCategory(name: string, id: number){
    const dto = {
      name
    };
    return this.http.put<WithdrawCategoryResponseDTO>(`${this.apiUrl}/${id}/update`, dto)
    .pipe(catchError(this.handleError));
  }

  public deleteCategory(id: number){
    return this.http.delete<WithdrawCategoryResponseDTO>(`${this.apiUrl}/${id}/delete`)
    .pipe(catchError(this.handleError));
  }


    handleError(err: any){
      console.error('FinanceAutentication Error: ', err);
      return throwError(() => err);
    }
}
