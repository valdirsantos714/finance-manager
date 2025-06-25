import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RendaDashboardComponent } from './renda-dashboard.component';

describe('RendaDashboardComponent', () => {
  let component: RendaDashboardComponent;
  let fixture: ComponentFixture<RendaDashboardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RendaDashboardComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RendaDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
