import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { tap } from 'rxjs';

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

  //global state
  private _categories = signal<WithdrawCategoryResponseDTO[]>([])
  
  //getter
  public categories = this._categories.asReadonly();

  // load
  public loadWithdrawCategories(){
    if(this._categories.length > 0) return;

    this.http.get<WithdrawCategoryResponseDTO[]>(this.apiUrl).subscribe({
      next: (data) =>{
        console.log('Withdraw categories loaded! ', data);
        this._categories.set(data);
      },
      error: (err) => console.error('Error in load the withdraw categories. ', err)
    })
  };

  // create 
  public createCategory(name: string){
    const dto = {
      name
    };
    return this.http.post<WithdrawCategoryResponseDTO>(`${this.apiUrl}/create `, dto).pipe(
      tap((data)=>{
        this._categories.update(list => [...list, data]);
      })
    );
  }
}
