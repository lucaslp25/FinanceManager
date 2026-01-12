import { TestBed } from '@angular/core/testing';

import { DepositCategoryService } from './deposit-category-service';

describe('DepositCategoryService', () => {
  let service: DepositCategoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DepositCategoryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
