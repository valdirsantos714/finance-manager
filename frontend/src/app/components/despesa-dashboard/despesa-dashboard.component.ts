import { Component, OnDestroy, OnInit } from '@angular/core';
import { DespesasResponse } from '../../models/DespesasResponse';
import { Subject, takeUntil } from 'rxjs';
import { DespesaService } from '../../services/despesa-service/despesa.service';
import { DespesasRequest } from '../../models/DespesasRequest';

@Component({
  selector: 'app-despesa-dashboard',
  templateUrl: './despesa-dashboard.component.html',
  styleUrl: './despesa-dashboard.component.css'
})
export class DespesaDashboardComponent implements OnInit, OnDestroy {
  private destroy$ = new Subject<void>();
  public despesas!: DespesasResponse[];

  constructor(private despesaService: DespesaService ) { }

  ngOnInit(): void {
    this.getDespesas();
  }

  getDespesas(): void {
    this.despesaService.getAllDespesas()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (despesas: DespesasResponse[]) => {
          this.despesas = despesas;
        },
        error: (error) => {
          console.error('Error fetching despesas:', error);
        }
      });
  }

  updateDespesa(idDespesa: number, updatedDespesa: DespesasRequest): void {
    this.despesaService.updateDespesa(idDespesa, updatedDespesa)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (updatedDespesaResponse: DespesasResponse) => {
          console.log('Despesa updated successfully:', updatedDespesaResponse);
          this.getDespesas(); // Refresh the list after update
        },
        error: (error) => {
          console.error('Error updating despesa:', error);
        }
      });
  }

  deleteDespesa(idDespesa: number): void {
    this.despesaService.deleteDespesa(idDespesa)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: () => {
          console.log('Despesa deleted successfully');
          this.getDespesas(); // Refresh the list after deletion
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
