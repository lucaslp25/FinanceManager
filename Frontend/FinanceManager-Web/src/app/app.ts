import { Component, inject, OnInit, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { WithdrawCategory } from './core/services/withdraw-category';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App implements OnInit{
  protected readonly title = signal('FinanceManager-Web');

  private categoryService = inject(WithdrawCategory);

  ngOnInit(): void {
    this.categoryService.loadWithdrawCategories();
  };
}
