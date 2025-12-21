import { TestBed } from '@angular/core/testing';

import { WithdrawCategoryState } from './withdraw-category-state';

describe('WithdrawCategoryState', () => {
  let service: WithdrawCategoryState;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WithdrawCategoryState);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
