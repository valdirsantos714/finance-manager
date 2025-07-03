import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ExpenseResponse } from '../../models/ExpenseResponse';
import { Observable, of } from 'rxjs';
import { ExpenseRequest } from '../../models/ExpenseRequest';

@Injectable({
  providedIn: 'root'
})
export class ExpenseService {
  private baseUrl: string = 'http://localhost:8080/despesas';

  constructor(private http: HttpClient) { }

  listaExpenses: ExpenseResponse[] = [
    {
      id: 1,
      name: "Aluguel",
      description: "Pagamento mensal do aluguel",
      value: 1500,
      date: "2023-10-01"
    },
    {
      id: 2,
      name: "Supermercado",
      description: "Compras mensais de supermercado",
      value: 800,
      date: "2023-10-05"
    }
  ];

  getAllExpenses():Observable<ExpenseResponse[]> {
    return of(this.listaExpenses);
  }

  updateExpense(idExpense: number, updatedExpense: ExpenseRequest): Observable<ExpenseResponse> {
    return this.http.put<ExpenseResponse>(this.baseUrl + '/' + idExpense, updatedExpense);
  }

  deleteExpense(idExpense: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${idExpense}`);
  }
}

