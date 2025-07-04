import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatDialogModule, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { ReactiveFormsModule } from '@angular/forms';
import { MatSelectModule } from '@angular/material/select';
import { MAT_DATE_LOCALE, MatNativeDateModule, MatOptionModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { RendaDashboardComponent } from '../components/renda-dashboard/renda-dashboard.component';
import { DespesaDashboardComponent } from '../components/despesa-dashboard/despesa-dashboard.component';
import { ItemListComponent } from '../components/shared/item-list/item-list.component';

@NgModule({
  declarations: [
    RendaDashboardComponent,
    DespesaDashboardComponent,
    ItemListComponent
  ],
  imports: [
    CommonModule,
    // Módulos do Angular Material
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
    MatSelectModule,
    MatOptionModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    MatSnackBarModule,
  ],
  exports: [
    RendaDashboardComponent,
    DespesaDashboardComponent,
    // Exportar módulos reutilizáveis
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
    MatSelectModule,
    MatOptionModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    MatSnackBarModule,
    ItemListComponent
  ],
  providers: [
    { provide: MatDialogRef, useValue: {} },
    { provide: MAT_DIALOG_DATA, useValue: {} },
    { provide: MAT_DATE_LOCALE, useValue: 'pt-BR' }
  ]
})
export class SharedModule { }
