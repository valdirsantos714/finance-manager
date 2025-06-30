import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject, takeUntil } from 'rxjs';
import { RendasResponse } from '../../models/RendasResponse';
import { RendaService } from '../../services/renda-service/renda.service';

@Component({
  selector: 'app-renda-dashboard',
  templateUrl: './renda-dashboard.component.html',
  styleUrl: './renda-dashboard.component.css'
})
export class RendaDashboardComponent implements OnInit, OnDestroy {
  private destroy$ = new Subject<void>();
  public rendas!: RendasResponse[];

  constructor(private rendaService: RendaService) {
  }
  
  ngOnInit(): void {
    this.getRendas();
  }

  getRendas(): void {
    this.rendaService.getAllRendas()
    .pipe(takeUntil(this.destroy$))
    .subscribe({
      next: (rendas: RendasResponse[]) => {
        this.rendas = rendas;
      },
      error: (error) => {
        console.error('Error fetching rendas:', error);
      }
    });
  }

  updateRenda(idRenda: number): void {
    this.rendaService.updateRenda(idRenda)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (updatedRenda: RendasResponse) => {
          console.log('Renda updated successfully:', updatedRenda);
          this.getRendas(); 
        },
        error: (error) => {
          console.error('Error updating renda:', error);
        }
      });
  }

  deleteRenda(idRenda: number): void {
    this.rendaService.deleteRenda(idRenda)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: () => {
          console.log('Renda deleted successfully');
          this.getRendas(); 
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
