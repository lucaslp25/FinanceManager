import { TestBed } from '@angular/core/testing';

import { WalletState } from './wallet-state';

describe('WalletState', () => {
  let service: WalletState;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WalletState);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
