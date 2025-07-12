import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject, takeUntil } from 'rxjs';
import { IncomeResponse } from '../../models/IncomeResponse';
import { IncomeService } from '../../services/income-service/income.service';
import { ListItem } from '../../models/ListItem';
import { ItemAction } from '../../models/enums/ItemAction';
import { IncomeCategory } from '../../models/enums/IncomeCategory';
import { MatDialog } from '@angular/material/dialog';
import { ItemModalComponent } from '../shared/item-modal/item-modal.component';

@Component({
  selector: 'app-renda-dashboard',
  templateUrl: './renda-dashboard.component.html',
  styleUrl: './renda-dashboard.component.css'
})
export class RendaDashboardComponent implements OnInit, OnDestroy {
  private destroy$ = new Subject<void>();
  public rendas!: ListItem[];

  constructor(
    private incomeService: IncomeService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.getIncomes();
  }

  getIncomes(): void {
    this.incomeService.getAllIncomes()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (incomes: IncomeResponse[]) => {
          this.rendas = incomes.map(income => ({
            id: income.id,
            name: income.name,
            description: income.description,
            amount: income.amount,
            date: income.date,
            userId: income.userId,
            category: income.category
          }));
        },
        error: (error) => {
          console.error('Error fetching incomes:', error);
        }
      });
  }

  handleIncomeAction(event: { item: ListItem, action: ItemAction }): void {
    switch (event.action) {
      case ItemAction.Create:
        this.createIncome(event.item);
        break;
      case ItemAction.Update:
        this.updateIncome(event.item);
        break;
      case ItemAction.Delete:
        this.deleteIncome(event.item.id);
        break;
      default:
        console.warn('Unknown action:', event.action);
    }
  }

  createIncome(item: ListItem): void {
    this.incomeService.createIncome({
      name: item.name,
      description: item.description,
      amount: item.amount,
      date: item.date,
      category: String(item.category?.toUpperCase()) as IncomeCategory
    })
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (createdIncome: IncomeResponse) => {
          console.log('Income created successfully:', createdIncome);
          this.getIncomes();
        },
        error: (error) => {
          console.error('Error creating income:', error);
        }
      });
  }

  updateIncome(item: ListItem): void {
    this.incomeService.updateIncome(item.id, {
      name: item.name,
      description: item.description,
      amount: item.amount,
      date: item.date,
      category: String(item.category?.toUpperCase()) as IncomeCategory
    })
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (updatedIncome: IncomeResponse) => {
          console.log('Income updated successfully:', updatedIncome);
          this.getIncomes();
        },
        error: (error) => {
          console.error('Error updating income:', error);
        }
      });
  }

  deleteIncome(id: number): void {
    this.incomeService.deleteIncome(id)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: () => {
          console.log('Income deleted successfully');
          this.getIncomes();
        },
        error: (error) => {
          console.error('Error deleting income:', error);
        }
      });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
