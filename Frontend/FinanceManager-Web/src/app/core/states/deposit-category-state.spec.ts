import { TestBed } from '@angular/core/testing';

import { DepositCategoryState } from './deposit-category-state';

describe('DepositCategoryState', () => {
  let service: DepositCategoryState;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DepositCategoryState);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
