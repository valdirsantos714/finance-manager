import { DashboardComponent } from './dashboard.component';
import { FinancialService } from '../../services/financial-service/financial.service';
import { of, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { TestBed } from '@angular/core/testing';

describe('DashboardComponent', () => {
  let component: DashboardComponent;
  let financialServiceMock: any;
  let routerMock: any;

  beforeEach(() => {
    financialServiceMock = {
      getFinancialSummary: jest.fn()
    };

    routerMock = {
      navigate: jest.fn()
    };

    TestBed.configureTestingModule({
      providers: [
        DashboardComponent,
        { provide: FinancialService, useValue: financialServiceMock },
        { provide: Router, useValue: routerMock }
      ]
    });

    component = TestBed.inject(DashboardComponent);
  });

  it('should call getFinancialSummary on init and set financialSummary', () => {
    // Given
    financialServiceMock.getFinancialSummary.mockReturnValue(of({
      name: 'Test User',
      totalIncome: 1000,
      totalExpenses: 500,
      balance: 500
    }));

    // When
    component.ngOnInit();

    // Then
    expect(financialServiceMock.getFinancialSummary).toHaveBeenCalled();
    expect(component.financialSummary.name).toBe('Test User');
    expect(component.financialSummary.totalIncome).toBe(1000);
    expect(component.financialSummary.totalExpenses).toBe(500);
    expect(component.financialSummary.balance).toBe(500);
  });

  it('should log error if getFinancialSummary fails', () => {
    // Given
    const error = new Error('Failed');
    financialServiceMock.getFinancialSummary.mockReturnValue(throwError(() => error));
    const consoleSpy = jest.spyOn(console, 'error').mockImplementation(() => {});

    // When
    component.ngOnInit();

    // Then
    expect(consoleSpy).toHaveBeenCalledWith('Error fetching financial summary:', error);
  });

  it('should navigate to /gerenciar-renda and log message when gerenciarRenda is called', () => {
    // Given
    jest.spyOn(console, 'log').mockImplementation(() => {});

    // When
    component.gerenciarRenda();

    // Then
    expect(routerMock.navigate).toHaveBeenCalledWith(['/gerenciar-renda']);
    expect(console.log).toHaveBeenCalledWith('Navigating to Gerenciar Renda');
  });

  it('should navigate to /gerenciar-despesa when gerenciarDespesa is called', () => {
    // When
    component.gerenciarDespesa();

    // Then
    expect(routerMock.navigate).toHaveBeenCalledWith(['/gerenciar-despesa']);
  });

  it('should complete destroy$ subject on ngOnDestroy', () => {
    // Given
    const nextSpy = jest.spyOn(component['destroy$'], 'next');
    const completeSpy = jest.spyOn(component['destroy$'], 'complete');

    // When
    component.ngOnDestroy();

    // Then
    expect(nextSpy).toHaveBeenCalled();
    expect(completeSpy).toHaveBeenCalled();
  });
});
