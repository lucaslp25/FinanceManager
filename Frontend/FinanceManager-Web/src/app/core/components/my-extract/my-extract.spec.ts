import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyExtract } from './my-extract';

describe('MyExtract', () => {
  let component: MyExtract;
  let fixture: ComponentFixture<MyExtract>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MyExtract]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MyExtract);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
