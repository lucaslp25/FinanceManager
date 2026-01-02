import { Component, inject, OnInit, signal } from '@angular/core';
import { WalletState } from '../../states/wallet-state';
import { CommonModule } from '@angular/common';
import { Modal } from '../../../shared/components/modal/modal';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgxMaskDirective } from 'ngx-mask';
import { WithdrawCategoryState } from '../../states/withdraw-category-state';

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
  public fb = inject(FormBuilder);
  public categoryState = inject(WithdrawCategoryState);

  public modalMode = signal<ModalMode>(null);

  public errorMessage = signal<string | null>(null);

  public isCategoryModalOpen = signal(false);
  newCategoryControl = new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(30)]);

  openNewCategoryModal(){
    this.newCategoryControl.reset();
    this.isCategoryModalOpen.set(true);
  };

  closeNewCategoryModal(){
    this.isCategoryModalOpen.set(false);
    this.errorMessage.set(null);
  }

  saveNewCategory(){

    if(this.newCategoryControl.invalid){
      this.errorMessage.set('Nome inválido! Precisa ter entre 3 e 30 caracteres.');
      return;
    } 

    console.log(this.errorMessage());

    const name = this.newCategoryControl.value || '';

    this.categoryState.create(name).subscribe({
      next: () =>{
        this.closeNewCategoryModal();
      }
    })
  };

  // -- FORMS --

  // starts with null.
  amountControl = new FormControl<string | number>('', [Validators.required]);

  withdrawForm: FormGroup = this.fb.group({
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
    this.amountControl.reset();
    this.modalMode.set('ADD');
  };
  
  OpenWithdrawModal(){
    this.withdrawForm.reset();
    this.modalMode.set('WITHDRAW');
  };

 
  closeModal(){
    this.modalMode.set(null);
  }

  onNewCategory(value: string){
    this.categoryState.create(value);
  }


  confirmWithdraw(){
    if(this.withdrawForm.invalid) return;

    const dto = {
      amount: this.withdrawForm.value.amount,
      categoryId: this.withdrawForm.value.category,
      description: this.withdrawForm.value.description,
      date: new Date(this.withdrawForm.value.date as string).toISOString()
    };

    console.log(`DADOS DO MEU DTO: ${dto.amount}, ${dto.categoryId}, ${dto.description}, ${dto.date}`)

    this.state.withdrawBalance(dto);
    
    this.closeModal();
  }

  confirmAdd(){
    if (this.amountControl.invalid) return;

    const rawValue = this.amountControl.value;
    const numericValue = Number(rawValue) || 0;

    console.log(`Raw Value for backend: ${rawValue}`);
    console.log(`Numeric value for backend: ${numericValue}`);

    const dto = {
      amount: numericValue
    }
    
    this.state.addBalance(dto).subscribe({
      next: (next) => {console.log(`Balance updated --> ${next.newBalance}`)},
      error: (err) => {console.error('Error in request of add balance. ', err)}
    })

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
  }

}
