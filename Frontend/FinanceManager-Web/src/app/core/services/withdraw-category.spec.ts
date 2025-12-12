import { TestBed } from '@angular/core/testing';

import { WithdrawCategory } from './withdraw-category';

describe('WithdrawCategory', () => {
  let service: WithdrawCategory;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WithdrawCategory);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
