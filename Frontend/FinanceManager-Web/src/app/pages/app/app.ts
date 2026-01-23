import { Component, inject } from '@angular/core';
import { Header } from "../../layouts/header/header";
import { Router, RouterOutlet } from '@angular/router';
import { Sidebar } from "../../layouts/sidebar/sidebar";
import { UiService } from '../../core/services/ui-service';
import { TransactionState } from '../../core/states/transaction-state';
import { WithdrawCategoryState } from '../../core/states/withdraw-category-state';
import { DepositCategoryState } from '../../core/states/deposit-category-state';
import { GoalState } from '../../core/states/goal-state';

@Component({
  selector: 'app-app',
  imports: [Header, Sidebar, RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.scss',
})
export class App {

  private route = inject(Router);
  public ui = inject(UiService);

  private transactionState = inject(TransactionState);
  private withdrawState = inject(WithdrawCategoryState);
  private depositState = inject(DepositCategoryState);
  private goalState = inject(GoalState);

  logoutRequest():void{

    this.transactionState.resetState();
    this.depositState.resetState();
    this.goalState.resetState();
    this.withdrawState.resetState();

    localStorage.removeItem('token');
    this.route.navigate(['/login']);
  }
}
