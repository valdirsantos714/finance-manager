import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DespesaDashboardComponent } from './despesa-dashboard.component';

describe('DespesaDashboardComponent', () => {
  let component: DespesaDashboardComponent;
  let fixture: ComponentFixture<DespesaDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DespesaDashboardComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DespesaDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
