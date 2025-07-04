import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ExpenseResponse } from '../../models/ExpenseResponse';
import { Observable, of } from 'rxjs';
import { ExpenseRequest } from '../../models/ExpenseRequest';

@Injectable({
  providedIn: 'root'
})
export class ExpenseService {
  private baseUrl: string = 'http://localhost:8080/api/expenses';

  constructor(private http: HttpClient) { }

  getAllExpenses():Observable<ExpenseResponse[]> {
    return this.http.get<ExpenseResponse[]>(this.baseUrl);
  }

  updateExpense(idExpense: number, updatedExpense: ExpenseRequest): Observable<ExpenseResponse> {
    return this.http.put<ExpenseResponse>(this.baseUrl + '/' + idExpense, updatedExpense);
  }

  deleteExpense(idExpense: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${idExpense}`);
  }
}
