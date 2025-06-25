import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DespesaDashboardComponent } from './despesa-dashboard.component';

@NgModule({
  declarations: [DespesaDashboardComponent],
  imports: [CommonModule],
  exports: [DespesaDashboardComponent]
})
export class DespesaDashboardModule { }
