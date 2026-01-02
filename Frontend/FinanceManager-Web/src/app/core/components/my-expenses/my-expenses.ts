import { Component, inject, OnInit, signal } from '@angular/core';
import { WithdrawTransactionResponseDTO } from '../../models/transaction';
import { CommonModule } from '@angular/common';
import { Modal } from "../../../shared/components/modal/modal";
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { WithdrawCategoryState } from '../../states/withdraw-category-state';
import { TransactionState } from '../../states/transaction-state';

@Component({
  selector: 'app-my-expenses',
  imports: [CommonModule, Modal, ReactiveFormsModule],
  templateUrl: './my-expenses.html',
  styleUrl: './my-expenses.scss',
})
export class MyExpenses implements OnInit {

  state = inject(TransactionState);

  public expenses = this.state.myExpenses;
  public isLoading = signal(true);

  public categoryState = inject(WithdrawCategoryState);

  public isEditModalOpen = signal(false);
  public isExcludeModalOpen = signal(false);
  public currentTransaction = signal<WithdrawTransactionResponseDTO | null>(null);


  editForm = new FormGroup({
    id: new FormControl<string | null>(null), 
    amount: new FormControl<number | null>(null, [Validators.required]),
    description: new FormControl('', [Validators.required]),
    date: new FormControl('', [Validators.required]), 
    categoryId: new FormControl<number | null>(null, [Validators.required])
  })


  onExclude(transaction: WithdrawTransactionResponseDTO): void{
    this.isExcludeModalOpen.set(true);
    this.currentTransaction.set(transaction);
  }


  onEdit(transaction: WithdrawTransactionResponseDTO):void{
    const formattedDate = this.formatDateForInput(transaction.date);

    this.editForm.patchValue({
      id: transaction.transactionId,
      amount: transaction.amount,
      description: transaction.description,
      date: formattedDate,
      categoryId: transaction.categoryId
    })

    this.currentTransaction.set(transaction);
    this.isEditModalOpen.set(true);
  }

  onConfirmEdit():void{

    if (this.editForm.invalid) return;
    // cab put a message error in a signal here

    const dto = {
      amount: this.editForm.value.amount ?? null,
      description: this.editForm.value.description ?? null,
      categoryId: this.editForm.value.categoryId ?? null,
      date: new Date(this.editForm.value.date as string).toISOString() ?? null
    }

    const transactionId = this.currentTransaction()?.transactionId;
    
    if (transactionId){
      this.state.editWithdrawTransaction(dto, transactionId);
    }

    this.isEditModalOpen.set(false);
  }
  
  onConfirmDelete(){
    if (this.currentTransaction === null) return;
    
    const transactionId = this.currentTransaction()?.transactionId;
    
    this.state.deleteWithdrawTransactio(transactionId!);
    this.isExcludeModalOpen.set(false);
  }

  ngOnInit(): void {
    this.state.loadExpenses();

    this.categoryState.load();
  }

  private formatDateForInput(dateString: string | Date): string {
    const date = new Date(dateString);
    date.setMinutes(date.getMinutes() - date.getTimezoneOffset());
    return date.toISOString().slice(0, 16);
  }
   
}
