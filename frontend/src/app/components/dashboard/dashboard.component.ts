import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit, OnDestroy {

  constructor(private router: Router) {
  }

  ngOnInit(): void {
    // Code to run when the component is initialized
    console.log('DashboardComponent initialized');
  }

  gerenciarRenda(): void {
    console.log('Navigating to Gerenciar Renda');
    this.router.navigate(['/gerenciar-renda']);
  }

  gerenciarDespesa(): void {
    this.router.navigate(['/gerenciar-despesa']);
  }

  ngOnDestroy(): void {
    // Cleanup logic can go here
    console.log('DashboardComponent destroyed');
  }

}
