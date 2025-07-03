import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject, takeUntil } from 'rxjs';
import { IncomeResponse } from '../../models/IncomeResponse';
import { IncomeService } from '../../services/income-service/income.service';

@Component({
  selector: 'app-renda-dashboard',
  templateUrl: './renda-dashboard.component.html',
  styleUrl: './renda-dashboard.component.css'
})
export class RendaDashboardComponent implements OnInit, OnDestroy {
  private destroy$ = new Subject<void>();
  public rendas!: IncomeResponse[];

  constructor(private incomeService: IncomeService) { 
    // You can initialize any properties or services here if needed
  }
  
  ngOnInit(): void {
    this.getIncomes();
  }

  getIncomes(): void {
    this.incomeService.getAllIncomes()
    .pipe(takeUntil(this.destroy$))
    .subscribe({
      next: (rendas: IncomeResponse[]) => {
        this.rendas = rendas;
      },
      error: (error) => {
        console.error('Error fetching rendas:', error);
      }
    });
  }

  updateIncome(idRenda: number): void {
    this.incomeService.updateIncome(idRenda)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (updatedRenda: IncomeResponse) => {
          console.log('Renda updated successfully:', updatedRenda);
          this.getIncomes(); 
        },
        error: (error) => {
          console.error('Error updating renda:', error);
        }
      });
  }

  deleteIncome(idRenda: number): void {
    this.incomeService.deleteIncome(idRenda)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: () => {
          console.log('Renda deleted successfully');
          this.getIncomes(); 
        },
        error: (error) => {
          console.error('Error deleting renda:', error);
        }
      });
  }

  
  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
