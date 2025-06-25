import { Component, OnDestroy, OnInit } from '@angular/core';
import { RendaService } from '../../services/renda.service';
import { Observable, Subject, takeUntil } from 'rxjs';
import { RendasResponse } from '../../models/RendasResponse';

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

  
  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
