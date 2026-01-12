import { Component, inject, OnInit, signal } from '@angular/core';
import { WalletState } from '../../states/wallet-state';
import { CommonModule } from '@angular/common';
import { Modal } from '../../../shared/components/modal/modal';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgxMaskDirective } from 'ngx-mask';
import { WithdrawCategoryState } from '../../states/withdraw-category-state';
import { DepositCategoryState } from '../../states/deposit-category-state';

// control the modal state
type ModalMode = 'ADD' | 'WITHDRAW' | null;

@Component({
  selector: 'app-wallet',
  standalone: true,
  imports: [CommonModule, Modal, ReactiveFormsModule, NgxMaskDirective],
  templateUrl: './wallet.html',
  styleUrl: './wallet.scss',
})
export class Wallet implements OnInit {

  public state = inject(WalletState);
  public withdrawState = inject(WithdrawCategoryState);
  public depositState = inject(DepositCategoryState);
  public fb = inject(FormBuilder);

  public modalMode = signal<ModalMode>(null);

  public errorMessage = signal<string | null>(null);

  public isWithdrawCategoryModalOpen = signal(false);
  public isDepositCategoryModalOpen = signal(false);


  newCategoryControl = new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(30)]);

  openWithdrawCategoryModal(){
    this.newCategoryControl.reset();
    this.isWithdrawCategoryModalOpen.set(true);
  };

  openDepositCategoryModal(){
    this.newCategoryControl.reset();
    this.isDepositCategoryModalOpen.set(true);
  };

  closeNewCategoryModal(){
    this.isWithdrawCategoryModalOpen.set(false);
    this.isDepositCategoryModalOpen.set(false);
    this.errorMessage.set(null);
  }

  saveNewWithdrawCategory(){

    if(this.newCategoryControl.invalid){
      this.errorMessage.set('Nome inválido! Precisa ter entre 3 e 30 caracteres.');
      return;
    } 

    console.log(this.errorMessage());

    const name = this.newCategoryControl.value || '';

    this.withdrawState.create(name).subscribe({
      next: () =>{
        this.closeNewCategoryModal();
      }
    })
  };

  saveNewDepositCategory(){

    if(this.newCategoryControl.invalid){
      this.errorMessage.set('Nome inválido! Precisa ter entre 3 e 30 caracteres.');
      return;
    } 
    console.log(this.errorMessage());

    const name = this.newCategoryControl.value || '';

    this.depositState.create(name).subscribe({
      next: () =>{
        this.closeNewCategoryModal();
      }
    })
  };

  // -- FORMS --

  // starts with null.
  mainForm: FormGroup = this.fb.group({
    amount: new FormControl('', [Validators.required]),
    category: new FormControl('', [Validators.required]),
    description: new FormControl(''),
    date: new FormControl(this.getCurrentDateTimeForInput(), [Validators.required])
  });

  private getCurrentDateTimeForInput(): string {
    const now = new Date();
    now.setMinutes(now.getMinutes() - now.getTimezoneOffset())
    return now.toISOString().slice(0, 16);
  }

  openAddSaldoModal(){
    this.mainForm.reset();
    this.modalMode.set('ADD');
  };
  
  OpenWithdrawModal(){
    this.mainForm.reset();
    this.modalMode.set('WITHDRAW');
  };

  closeModal(){
    this.modalMode.set(null);
  }

  onNewCategory(value: string){
    this.withdrawState.create(value);
  }

  confirmWithdraw(){
    if(this.mainForm.invalid) return;

    const dto = {
      amount: this.mainForm.value.amount,
      categoryId: this.mainForm.value.category,
      description: this.mainForm.value.description,
      date: new Date(this.mainForm.value.date as string).toISOString()
    };

    console.log(`DADOS DO MEU DTO: ${dto.amount}, ${dto.categoryId}, ${dto.description}, ${dto.date}`)

    this.state.withdrawBalance(dto);
    
    this.closeModal();
  }

  confirmAdd(){

    if(this.mainForm.invalid) return;

    const dto = {
      amount: this.mainForm.value.amount,
      categoryId: this.mainForm.value.category,
      description: this.mainForm.value.description,
      date: new Date(this.mainForm.value.date as string).toISOString()
    };

    this.state.addBalance(dto);
    this.closeModal();
  }

  ngOnInit(): void {
    this.state.loadMyWallet().subscribe({
      next: () =>{
        console.log("Completed. State updated.")
      },
      error: (err) => console.error("Error in Wallet component: ", err)
    });

    this.withdrawState.load();
    this.depositState.load();
  }

}
