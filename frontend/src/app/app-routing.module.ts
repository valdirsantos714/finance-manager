import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { RendaDashboardComponent } from './components/renda-dashboard/renda-dashboard.component';
import { DespesaDashboardComponent } from './components/despesa-dashboard/despesa-dashboard.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full'
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'dashboard',
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
