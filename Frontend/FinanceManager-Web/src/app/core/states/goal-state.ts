import { inject, Injectable, signal } from '@angular/core';
import { GoalCreateDTO, GoalResponseDTO, GoalService, GoalUpdateDTO } from '../services/goal-service';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class GoalState {

  private service = inject(GoalService);

  private _goals = signal<GoalResponseDTO[]>([]);

  public goals = this._goals.asReadonly();
  private goalsLoaded = signal(false);

  public createGoal(dto: GoalCreateDTO){
    return this.service.createGoal(dto).pipe(
      tap((data) => {
        this._goals.update(list => [...list, data])
      })
    )
  }

  public loadMyGoals(){

    if(this.goalsLoaded()) return;

    this.service.getAllGoalsByUser().subscribe({
      next: (data) => {
        this._goals.set(data);
        console.log('Goals loaded. ', this.goals)
        this.goalsLoaded.set(true);
      },
      error: (Err) =>{
        console.error("Error in LoadGoals Request. ", Err)
      }
    })
  }

  public deleteGoal(id: number){
    
    if (id == null) return;
   
    return this.service.deleteGoal(id).pipe(
      tap(() =>{
        this._goals.update(currentList => currentList.filter(i => i.id !== id))
      })
    );
  }

  editGoal(dto: GoalUpdateDTO, id: number): Observable<GoalResponseDTO>{

    return this.service.updateGoal(dto, id).pipe(
      tap((data) => {
        this._goals.update(currentList => currentList.map(i => i.id === id ? data : i));
      })
    )
  }
  
}
