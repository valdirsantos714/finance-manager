import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import path from 'path';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { RendaDashboardComponent } from './components/renda-dashboard/renda-dashboard.component';
import { DespesaDashboardComponent } from './components/despesa-dashboard/despesa-dashboard.component';

const routes: Routes = [
  {
    path: '',
    component: DashboardComponent
  },
  {
    path: 'gerenciar-renda',
    component: RendaDashboardComponent
  },
  {
    path: 'gerenciar-despesa',
    component: DespesaDashboardComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
