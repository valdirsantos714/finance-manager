import { Component, OnDestroy, OnInit } from '@angular/core';
import { ExpenseResponse } from '../../models/ExpenseResponse';
import { Subject, takeUntil } from 'rxjs';
import { ExpenseService } from '../../services/expense-service/expense.service';
import { ExpenseRequest } from '../../models/ExpenseRequest';

@Component({
  selector: 'app-despesa-dashboard',
  templateUrl: './despesa-dashboard.component.html',
  styleUrl: './despesa-dashboard.component.css'
})
export class DespesaDashboardComponent implements OnInit, OnDestroy {
  private destroy$ = new Subject<void>();
  public despesas!: ExpenseResponse[];

  constructor(private expenseService: ExpenseService ) { }

  ngOnInit(): void {
    this.getExpenses();
  }

  getExpenses(): void {
    this.expenseService.getAllExpenses()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (despesas: ExpenseResponse[]) => {
          this.despesas = despesas;
        },
        error: (error) => {
          console.error('Error fetching despesas:', error);
        }
      });
  }

  updateExpense(idDespesa: number, updatedDespesa: ExpenseRequest): void {
    this.expenseService.updateExpense(idDespesa, updatedDespesa)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (updatedDespesaResponse: ExpenseResponse) => {
          console.log('Despesa updated successfully:', updatedDespesaResponse);
          this.getExpenses(); // Refresh the list after update
        },
        error: (error) => {
          console.error('Error updating despesa:', error);
        }
      });
  }

  deleteExpense(idDespesa: number): void {
    this.expenseService.deleteExpense(idDespesa)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: () => {
          console.log('Despesa deleted successfully');
          this.getExpenses(); // Refresh the list after deletion
        },
        error: (error) => {
          console.error('Error deleting despesa:', error);
        }
      });
    }


  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

}
