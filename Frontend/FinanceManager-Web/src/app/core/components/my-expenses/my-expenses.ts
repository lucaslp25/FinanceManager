import { Component, inject, OnInit, signal } from '@angular/core';
import { Transaction } from '../../services/transaction';
import { WithdrawTransactionResponseDTO } from '../../models/transaction';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-my-expenses',
  imports: [CommonModule],
  templateUrl: './my-expenses.html',
  styleUrl: './my-expenses.scss',
})
export class MyExpenses implements OnInit {
  
  private service = inject(Transaction);

  public expenses = signal<WithdrawTransactionResponseDTO[]>([]);
  public isLoading = signal(true);

  ngOnInit(): void {
    this.service.loadMyExpenseList().subscribe({
      next: (data) =>{
        console.log("Expenses loaded.")

        this.expenses.set(data);
        this.isLoading.set(false);
      },
      error: (err) =>{
        console.error("Error in load the expense list. ", err)
        this.isLoading.set(false);
      }
    });
  }
   
}
