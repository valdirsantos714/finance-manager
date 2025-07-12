import { Component, OnDestroy, OnInit } from '@angular/core';
import { ExpenseResponse } from '../../models/ExpenseResponse';
import { Subject, takeUntil } from 'rxjs';
import { ExpenseService } from '../../services/expense-service/expense.service';
import { ExpenseRequest } from '../../models/ExpenseRequest';
import { ListItem } from '../../models/ListItem';
import { ItemAction } from '../../models/enums/ItemAction';
import { ExpenseCategory } from '../../models/enums/ExpenseCategory';
import { MatDialog } from '@angular/material/dialog';
import { ItemModalComponent } from '../shared/item-modal/item-modal.component';

@Component({
  selector: 'app-despesa-dashboard',
  templateUrl: './despesa-dashboard.component.html',
  styleUrl: './despesa-dashboard.component.css'
})
export class DespesaDashboardComponent implements OnInit, OnDestroy {
  private destroy$ = new Subject<void>();
  public despesas!: ListItem[];

  constructor(
    private expenseService: ExpenseService,
    private dialog: MatDialog
  ) { }

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
      case ItemAction.Create:
        this.createExpense(event.item);
        break;
      case ItemAction.Update:
        this.updateExpense(event.item);
        break;
      case ItemAction.Delete:
        this.deleteExpense(event.item.id);
        break;
      default:
        console.warn('Unknown action:', event.action);
    }
  }

  createExpense(item: ListItem): void {
    this.expenseService.createExpense({
      name: item.name,
      description: item.description,
      amount: item.amount,
      date: item.date,
      category: String(item.category?.toUpperCase()) as ExpenseCategory
    })
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (createdExpenseResponse: ExpenseResponse) => {
          console.log('Expense created successfully:', createdExpenseResponse);
          this.getExpenses();
        },
        error: (error) => {
          console.error('Error creating expense:', error);
        }
      });
  }

  updateExpense(item: ListItem): void {
    this.expenseService.updateExpense(item.id, {
      name: item.name,
      description: item.description,
      amount: item.amount,
      date: item.date,
      category: String(item.category?.toUpperCase()) as ExpenseCategory
    })
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (updatedExpenseResponse: ExpenseResponse) => {
          console.log('Expense updated successfully:', updatedExpenseResponse);
          this.getExpenses();
        },
        error: (error) => {
          console.error('Error updating expense:', error);
        }
      });
  }

  deleteExpense(id: number): void {
    this.expenseService.deleteExpense(id)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: () => {
          console.log('Expense deleted successfully');
          this.getExpenses();
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
