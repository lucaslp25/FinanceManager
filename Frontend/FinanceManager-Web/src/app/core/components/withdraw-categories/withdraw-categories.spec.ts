import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WithdrawCategories } from './withdraw-categories';

describe('WithdrawCategories', () => {
  let component: WithdrawCategories;
  let fixture: ComponentFixture<WithdrawCategories>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WithdrawCategories]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WithdrawCategories);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
