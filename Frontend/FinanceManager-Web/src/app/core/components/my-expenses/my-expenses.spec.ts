import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyExpenses } from './my-expenses';

describe('MyExpenses', () => {
  let component: MyExpenses;
  let fixture: ComponentFixture<MyExpenses>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MyExpenses]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MyExpenses);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
