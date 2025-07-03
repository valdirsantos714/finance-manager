import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RendaDashboardComponent } from './renda-dashboard.component';
import { ItemListModule } from '../shared/item-list/item-list.module';

@NgModule({
  declarations: [RendaDashboardComponent],
  imports: [CommonModule, ItemListModule],
  exports: [RendaDashboardComponent]
})
export class RendaDashboardModule { }
