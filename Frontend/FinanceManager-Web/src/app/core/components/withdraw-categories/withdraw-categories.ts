import { Component, inject } from '@angular/core';
import { WithdrawCategory } from '../../services/withdraw-category';

@Component({
  selector: 'app-withdraw-categories',
  imports: [],
  templateUrl: './withdraw-categories.html',
  styleUrl: './withdraw-categories.scss',
})
export class WithdrawCategories {

  public service = inject(WithdrawCategory);

}
