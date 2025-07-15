import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import { IncomeService } from '../../../services/income-service/income.service';
import { ExpenseService } from '../../../services/expense-service/expense.service';
import { IncomeRequest } from '../../../models/IncomeRequest';
import { ExpenseRequest } from '../../../models/ExpenseRequest';
import { IncomeResponse } from '../../../models/IncomeResponse';
import { ExpenseResponse } from '../../../models/ExpenseResponse';
import { IncomeCategory } from '../../../models/enums/IncomeCategory';
import { ExpenseCategory } from '../../../models/enums/ExpenseCategory';
import { ListItem } from '../../../models/ListItem';
import { get } from 'node:http';

type ItemType = 'income' | 'expense';
type ItemRequest = IncomeRequest | ExpenseRequest;

@Component({
  selector: 'app-item-modal',
  templateUrl: './item-modal.component.html',
  styleUrls: ['./item-modal.component.css']
})
export class ItemModalComponent implements OnInit, OnDestroy {
  itemForm!: FormGroup;
  isEditMode = false;
  private destroy$ = new Subject<void>();
  categories: string[] = [];
  itemType!: ItemType;

  constructor(
    private readonly fb: FormBuilder,
    private readonly incomeService: IncomeService,
    private readonly expenseService: ExpenseService,
    private readonly dialogRef: MatDialogRef<ItemModalComponent>,
    @Inject(MAT_DIALOG_DATA) public readonly data: ListItem | null
  ) {}

  ngOnInit(): void {
    this.initializeForm();
    if (this.data) {
      this.isEditMode = true;
      this.patchFormValues();
      this.itemType = (this.data as IncomeResponse).category in IncomeCategory ? 'income' : 'expense';
    } else {
      // Determine itemType based on context, e.g., from a service or route param
      // For now, we'll assume 'income' if no data is provided, this needs to be refined
      // based on how the modal is opened from RendaDashboardComponent or DespesaDashboardComponent
      this.itemType = 'income'; // Placeholder, will be properly set by the calling component
    }
    this.loadCategories();
  }

  private initializeForm(): void {
    this.itemForm = this.fb.group({
      id: [null],
      name: ['', Validators.required],
      description: ['', Validators.required],
      amount: ['', [Validators.required, Validators.min(0.01)]],
      category: ['', Validators.required],
      date: ['', Validators.required]
    });
  }

  private loadCategories(): void {
    if (this.itemType === 'income') {
      this.categories = Object.values(IncomeCategory);
    } else {
      this.categories = Object.values(ExpenseCategory);
    }
  }

  private patchFormValues(): void {
    const formattedDate = new Date(this.data!.date);
    this.itemForm.patchValue({
      id: this.data!.id || null,
      name: this.data!.name,
      description: this.data!.description,
      amount: this.data!.amount,
      category: this.data!.category,
      date: formattedDate
    });
  }

  updateDate(event: MatDatepickerInputEvent<Date>) {
    this.itemForm.patchValue({
      date: event.value
    });
  }

  save(): void {
    const item: ItemRequest = {
      name: this.itemForm.value.name,
      description: this.itemForm.value.description,
      amount: this.itemForm.value.amount,
      category: this.itemForm.value.category,
      date: this.itemForm.value.date.toISOString()
    };

    if (this.isEditMode && this.data) {
      const updatedItem: any = {
        ...item,
        id: this.data.id
      };
      if (this.itemType === 'income') {
        this.incomeService.updateIncome(this.itemForm.get('id')?.value, updatedItem)
          .pipe(takeUntil(this.destroy$))
          .subscribe({
            next: () => {
              console.log('Income updated successfully');
              this.getAllIncomes();
              this.dialogRef.close(item);
            },
            error: (error) => {
              console.log('id: ',this.itemForm.value.id);

            }
          });
      } else {
        this.expenseService.updateExpense(this.itemForm.get('id')?.value, updatedItem)
          .pipe(takeUntil(this.destroy$))
          .subscribe({
            next: () => {
              this.dialogRef.close(item)
              console.log('Expense updated successfully');
              this.getAllExpenses();
            },
            error: (error: any) => {
              console.error('Error updating expense:', error);
              
            }
          });
      }
    } else {
      if (this.itemType === 'income') {
        this.incomeService.createIncome(item as IncomeRequest)
          .pipe(takeUntil(this.destroy$))
          .subscribe({
            next: () => {
              this.dialogRef.close(item);
              this.getAllIncomes();
              console.log('Income created successfully');
            },
            error: (error: any) => {
              console.error('Error creating income:', error);
            }
          });
      } else {
        this.expenseService.createExpense(item as ExpenseRequest)
          .pipe(takeUntil(this.destroy$))
          .subscribe({
            next: () => {
              this.dialogRef.close(item)
              this.getAllExpenses();
            },
            error: (error: any) => {
              console.error('Error creating expense:', error);
            }
          });
      }
    }
  }

  private getAllIncomes(): void {
    this.incomeService.getAllIncomes()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (incomes: IncomeResponse[]) => {
          console.log('Incomes fetched successfully:', incomes);
        },
        error: (error) => {
          console.error('Error fetching incomes:', error);
        }
      });

  }

  private getAllExpenses(): void {
    this.expenseService.getAllExpenses()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (expenses: ExpenseResponse[]) => {
          console.log('Expenses fetched successfully:', expenses);
        },
        error: (error) => {
          console.error('Error fetching expenses:', error);
        }
      });
  }

  close(): void {
    this.dialogRef.close(false);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
