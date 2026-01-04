import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';


export interface GoalResponseDTO{
  id: number;
  name: string;
  description: string;
  targetAmount: number;
  deadline: Date,
  initDate: Date
}

export interface GoalCreateDTO{
  name: string | null;
  description: string | null;
  targetAmount: number | null;
  deadline: Date | null
}

export interface GoalUpdateDTO{
  name: string | null;
  description: string | null;
  targetAmount: number | null;
  deadline: Date | null
}

@Injectable({
  providedIn: 'root',
})
export class GoalService {

  private http = inject(HttpClient);

  private UrlBase = `${environment.apiUrl}/goals`;

  public getAllGoalsByUser(): Observable<GoalResponseDTO[]>{
    return this.http.get<GoalResponseDTO[]>(`${this.UrlBase}`);
  }

  public createGoal(dto: GoalCreateDTO): Observable<GoalResponseDTO>{
    return this.http.post<GoalResponseDTO>(`${this.UrlBase}/create`, dto)
  }

  public deleteGoal(id: number){
    return this.http.delete<GoalResponseDTO>(`${this.UrlBase}/${id}/delete`)
  }
  
  public updateGoal(dto: GoalUpdateDTO, id: number): Observable<GoalResponseDTO>{
    return this.http.patch<GoalResponseDTO>(`${this.UrlBase}/${id}/update`, dto)
  }
  
}
