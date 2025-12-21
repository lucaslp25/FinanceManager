import { inject, Injectable, signal } from '@angular/core';
import { WithdrawCategory, WithdrawCategoryResponseDTO } from '../services/withdraw-category';
import { tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class WithdrawCategoryState {

  private service = inject(WithdrawCategory);
  
  private _categories = signal<WithdrawCategoryResponseDTO[]>([]);

  public categories = this._categories.asReadonly();

  //for know if alredy´s loaded
  private loaded = false;

  public load(){
    if (this.loaded) return;
    
    return this.service.loadWithdrawCategories().subscribe({
      next: (data) =>{
        console.log('Withdraw categories loaded! ', data);
        this._categories.set(data);
        this.loaded = true;
      },
      error: (err) => console.error('Error in load the withdraw categories. ', err)
    })
  }

  public create(name: string){
    return this.service.createCategory(name).pipe(
      tap((newCategory) =>{
                // update add the new item on the final of array 
          this._categories.update(list => [...list, newCategory])
      })
    );
  }

  public update(name: string, id: number){
    return this.service.updateCategory(name, id).pipe(
      tap((newData) => {
        this._categories.update(list => 
          list.map(i => i.id === id ? newData : i)
        )
      })
    );
  }

  public delete(id: number){
    return this.service.deleteCategory(id).pipe(
      tap(() => {
        this._categories.update(currentList => currentList.filter(i => i.id !== id))
      })
    )
  }

}
