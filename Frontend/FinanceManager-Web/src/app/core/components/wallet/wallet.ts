import { Component, inject, OnInit, signal } from '@angular/core';
import { WalletState } from '../../states/wallet-state';
import { CommonModule } from '@angular/common';
import { Modal } from '../../../shared/components/modal/modal';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgxMaskDirective } from 'ngx-mask';

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
  public fb = inject(FormBuilder);

  public modalMode = signal<ModalMode>(null);

  // public isBalanceOpen = signal(false);

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
      next: (next) => {console.log(`SALDO FOI ATUALIZADO.... ${next.balance}`)},
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
