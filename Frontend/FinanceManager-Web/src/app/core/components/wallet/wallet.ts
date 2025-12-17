import { Component, inject, OnInit, signal } from '@angular/core';
import { WalletState } from '../../states/wallet-state';
import { CommonModule } from '@angular/common';
import { Modal } from '../../../shared/components/modal/modal';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgxMaskDirective } from 'ngx-mask';
import { WithdrawCategory } from '../../services/withdraw-category';
import { Footer } from "../../../layouts/footer/footer";

// control the modal state
type ModalMode = 'ADD' | 'WITHDRAW' | null;

@Component({
  selector: 'app-wallet',
  standalone: true,
  imports: [CommonModule, Modal, ReactiveFormsModule, NgxMaskDirective, Footer],
  templateUrl: './wallet.html',
  styleUrl: './wallet.scss',
})
export class Wallet implements OnInit {

  public state = inject(WalletState);
  public fb = inject(FormBuilder);
  public categoryService = inject(WithdrawCategory);

  public modalMode = signal<ModalMode>(null);

  public isCategoryModalOpen = signal(false);
  newCategoryControl = new FormControl('', [Validators.required]);

  openNewCategoryModal(){
    this.newCategoryControl.reset();
    this.isCategoryModalOpen.set(true);
  };

  closeNewCategoryModal(){
    this.isCategoryModalOpen.set(false);
  }

  saveNewCategory(){
    if(this.newCategoryControl.invalid) return;

    const name = this.newCategoryControl.value || '';

    this.categoryService.createCategory(name).subscribe({
      next: (next) => {
        console.log("Adding new category: ", name);

        // select automatic the category created
        this.withdrawForm.get('category')?.setValue(next.id);

        this.isCategoryModalOpen.set(false);
      }, 
      error: (err) => console.error("Error in add new category. ", err)
    })
  };

  // -- FORMS --

  // starts with null.
  amountControl = new FormControl<string | number>('', [Validators.required]);

  withdrawForm: FormGroup = this.fb.group({
    amount: ['', [Validators.required]],
    category: ['', [Validators.required]],
    description: ['']
  });

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
    this.categoryService.createCategory(value).subscribe({
      next: (next) => {
        console.log("Adding new category: ", value);
      }, 
      error: (err) => console.error("Error in add new category. ", err)
    });
  }


  confirmWithdraw(){
    if(this.withdrawForm.invalid) return;

    const dto = {
      amount: this.withdrawForm.value.amount,
      categoryId: this.withdrawForm.value.category,
      description: this.withdrawForm.value.description
    };

    console.log(`DADOS DO MEU DTO: ${dto.amount}, ${dto.categoryId}, ${dto.description}`)

    this.state.withdrawBalance(dto).subscribe({

      next: (next) =>{
        console.log(`send the ${dto} for backend...`);
      },
      error: (err) => {console.error('Error in request of add balance. ', err)}
    });    
    
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
  }

}
