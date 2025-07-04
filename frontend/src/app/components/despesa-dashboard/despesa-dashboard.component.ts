import { Component, OnDestroy, OnInit } from '@angular/core';
import { ExpenseResponse } from '../../models/ExpenseResponse';
import { Subject, takeUntil } from 'rxjs';
import { ExpenseService } from '../../services/expense-service/expense.service';
import { ExpenseRequest } from '../../models/ExpenseRequest';
import { ListItem } from '../../models/ListItem';
import { ItemAction } from '../../models/enums/ItemAction';

@Component({
  selector: 'app-despesa-dashboard',
  templateUrl: './despesa-dashboard.component.html',
  styleUrl: './despesa-dashboard.component.css'
})
export class DespesaDashboardComponent implements OnInit, OnDestroy {
  private destroy$ = new Subject<void>();
  public despesas!: ListItem[];

  constructor(private expenseService: ExpenseService ) { }

  ngOnInit(): void {
    this.getExpenses();
  }

  getExpenses(): void {
    this.expenseService.getAllExpenses()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (expenses: ExpenseResponse[]) => {
          this.despesas = expenses.map(expense => ({
            id: expense.id,
            name: expense.name,
            description: expense.description,
            amount: expense.amount,
            date: expense.date,
            userId: expense.userId,
            category: expense.category
          }));
        },
        error: (error) => {
          console.error('Error fetching expenses:', error);
        }
      });
  }

  handleExpenseAction(event: { item: ListItem, action: ItemAction }): void {
    switch (event.action) {
      case ItemAction.Edit:
        this.updateExpense(event.item);
        break;
      case ItemAction.Delete:
        this.deleteExpense(event.item);
        break;
      default:
        console.warn('Unknown action:', event.action);
    }
  }

  updateExpense(item: ListItem): void {
    // You might need to fetch the full ExpenseRequest or create one from ListItem
    // For now, assuming you only need the ID for update/delete
    this.expenseService.updateExpense(item.id, {} as ExpenseRequest) // Placeholder for ExpenseRequest
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (updatedExpenseResponse: ExpenseResponse) => {
          console.log('Expense updated successfully:', updatedExpenseResponse);
          this.getExpenses(); // Refresh the list after update
        },
        error: (error) => {
          console.error('Error updating expense:', error);
        }
      });
  }

  deleteExpense(item: ListItem): void {
    this.expenseService.deleteExpense(item.id)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: () => {
          console.log('Expense deleted successfully');
          this.getExpenses(); // Refresh the list after deletion
        },
        error: (error) => {
          console.error('Error deleting expense:', error);
        }
      });
    }


  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

}
