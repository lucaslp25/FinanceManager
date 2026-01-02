import { TestBed } from '@angular/core/testing';

import { TransactionState } from './transaction-state';

describe('TransactionState', () => {
  let service: TransactionState;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TransactionState);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
