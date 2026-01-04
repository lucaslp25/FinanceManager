import { TestBed } from '@angular/core/testing';

import { GoalState } from './goal-state';

describe('GoalState', () => {
  let service: GoalState;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GoalState);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
