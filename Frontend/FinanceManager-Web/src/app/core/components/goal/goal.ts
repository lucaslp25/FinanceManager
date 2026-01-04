import { Component, inject, OnInit, signal } from '@angular/core';
import { Modal } from "../../../shared/components/modal/modal";
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { GoalState } from '../../states/goal-state';
import { CurrencyPipe, DatePipe } from '@angular/common';
import { GoalResponseDTO } from '../../services/goal-service';

@Component({
  selector: 'app-goal',
  imports: [Modal, ReactiveFormsModule, CurrencyPipe, DatePipe],
  templateUrl: './goal.html',
  styleUrl: './goal.scss',
})
export class Goal implements OnInit {

  public isNewGoalModalOpen = signal(false);
  public isEditGoalModalOpen = signal(false);
  public isDeleteGoalModalOpen = signal(false);

  public state = inject(GoalState);

  public currentGoal = signal<GoalResponseDTO | null>(null);
  

  goalForm = new FormGroup({
    name: new FormControl('', [Validators.required, Validators.minLength(3)]),
    targetAmount: new FormControl<number | null> (null, [Validators.required, Validators.min(1)]),
    description: new FormControl(''),
    deadline: new FormControl('')
  })



  ngOnInit(): void {
    this.state.loadMyGoals();
  }

  onNewGoal(){
    this.goalForm.reset();
    this.isNewGoalModalOpen.set(true);
  }

  onDeleteGoal(data: GoalResponseDTO){
    this.currentGoal.set(data);
    this.isDeleteGoalModalOpen.set(true);
  }
  
  onEditGoal(data: GoalResponseDTO){
    this.currentGoal.set(data);
    this.goalForm.reset();
    this.isEditGoalModalOpen.set(true);

    this.goalForm.patchValue({
      name: data.name,
      targetAmount: data.targetAmount,
      description: data.description,
      deadline: this.formatDateForInput(data.deadline) 
    });
  }

  onCloseNewGoalModal(){
    this.isNewGoalModalOpen.set(false);
    this.isEditGoalModalOpen.set(false);
    this.isDeleteGoalModalOpen.set(false);  
  }

  onConfirmGoal(){
    if (this.goalForm.invalid) return;

    const formValues = this.goalForm.getRawValue();
    
    const dto = {
      name: formValues.name ?? null,
      targetAmount: formValues.targetAmount ?? 0,
      description: formValues.description ?? null,
      deadline: formValues.deadline ? new Date(formValues.deadline) : null   
    }

    console.log("Criando Meta...", dto);

    this.state.createGoal(dto).subscribe({
      next: (next) => console.log("Goal created: ", next),
      error: (err) => console.error("Error in create goal requets", err)
    });

    this.onCloseNewGoalModal();
  }

  onConfirmDelete(){
    
    if(!this.currentGoal()) return;
    
    this.state.deleteGoal(this.currentGoal()!.id)?.subscribe({
      
      next: () =>{
        console.log(`Goal ${this.currentGoal()?.name} has successfully deleted.`);
      }, 
      error: (err) => console.error("Error in delete goal..", err)
    });
    
    this.isDeleteGoalModalOpen.set(false);
  }
  
  onConfirmUpdate() {

    if(!this.currentGoal()) return;

    const formValues = this.goalForm.getRawValue();
    
    const dto = {
      name: formValues.name,
      targetAmount: formValues.targetAmount,
      description: formValues.description,
      deadline: formValues.deadline ? new Date(formValues.deadline) : null
    };

    this.state.editGoal(dto, this.currentGoal()!.id).subscribe({
      next: () => console.log(`Goal ${this.currentGoal()?.name} has successfully edit.`),
      error: (err) => console.error("Error in edit goal..", err)
    })

    this.isEditGoalModalOpen.set(false);
    
  }
  
  private formatDateForInput(dateParam: Date | string | null): string {
    if (!dateParam) return '';
    
    const date = new Date(dateParam);
    
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    
    return `${year}-${month}-${day}`;
  }
}
