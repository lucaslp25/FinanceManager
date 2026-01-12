import { Component, inject, OnInit, signal } from '@angular/core';
import { TransactionState } from '../../states/transaction-state';
import { WithdrawCategoryState } from '../../states/withdraw-category-state';
import { CurrencyPipe, DatePipe, NgClass } from '@angular/common';
import { Modal } from "../../../shared/components/modal/modal";
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { DepositCategoryState } from '../../states/deposit-category-state';
import { TransactionResponseDTO } from '../../models/transaction';

@Component({
  selector: 'app-my-extract',
  imports: [CurrencyPipe, DatePipe, NgClass, Modal, ReactiveFormsModule],
  templateUrl: './my-extract.html',
  styleUrl: './my-extract.scss',
})
export class MyExtract implements OnInit {

  public state = inject(TransactionState);
  public transactions = this.state.transactions;

  public withdrawCategoryState = inject(WithdrawCategoryState);
  public depositCategoryState = inject(DepositCategoryState);

  public currentTransaction = signal<TransactionResponseDTO | null>(null);

  public EditModal = signal(false);
  public removeModal = signal(false);

  editForm = new FormGroup({
    id: new FormControl<string | null>(null), 
    amount: new FormControl<number | null>(null, [Validators.required]),
    description: new FormControl('', [Validators.required]),
    date: new FormControl('', [Validators.required]), 
    categoryId: new FormControl<number | null>(null, [Validators.required])
  })

  // REQUESTS 

  onConfirmDelete(): void {
    if(!this.currentTransaction) return;
    this.state.deleteTransaction(this.currentTransaction()?.transactionId!);
    this.removeModal.set(false);
    this.currentTransaction.set(null);
  }
  
  
  onConfirmEdit(): void{
    if(!this.currentTransaction) return;
    
    const dto = {
      amount: this.editForm.value.amount ?? null,
      description: this.editForm.value.description ?? null,
      categoryId: this.editForm.value.categoryId ?? null,
      date: new Date(this.editForm.value.date as string).toISOString() ?? null,
      transactionType: this.currentTransaction()?.transactionType ?? "WITHDRAW" // tenho que padronizar isso
    }

    const transactionId = this.currentTransaction()?.transactionId;
    
    if (transactionId){
      this.state.editTransaction(dto, transactionId);
    }
    
    this.EditModal.set(false);
    this.currentTransaction.set(null);
  }


  // MODAL

  onEdit(transaction: TransactionResponseDTO){

    const formattedDate = this.formatDateForInput(transaction.date);

    this.editForm.patchValue({
      id: transaction.transactionId,
      amount: transaction.amount,
      description: transaction.description,
      date: formattedDate,
      categoryId: transaction.categoryId
    })

    this.EditModal.set(true);
    this.currentTransaction.set(transaction);
  }


  private formatDateForInput(dateString: string | Date): string {
    const date = new Date(dateString);
    date.setMinutes(date.getMinutes() - date.getTimezoneOffset());
    return date.toISOString().slice(0, 16);
  }

  onExclude(dto: TransactionResponseDTO){
    this.removeModal.set(true);
    this.currentTransaction.set(dto);
  }

  onCloseModal(){
    this.EditModal.set(false);
    this.removeModal.set(false);
    this.currentTransaction.set(null);
  }
  
  public ngOnInit(): void {
    this.state.loadAllTransactios();
    this.withdrawCategoryState.load();
    this.depositCategoryState.load();
    console.log(this.transactions())
  }

}
