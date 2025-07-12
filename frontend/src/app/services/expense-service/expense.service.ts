import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ExpenseResponse } from '../../models/ExpenseResponse';
import { Observable, of } from 'rxjs';
import { ExpenseRequest } from '../../models/ExpenseRequest';
import { JwtService } from '../jwt-service/jwt.service';


@Injectable({
  providedIn: 'root'
})
export class ExpenseService {
  private baseUrl: string = 'http://localhost:8080/api/expenses';

  constructor(private http: HttpClient,
    private jwtService: JwtService
  ) { }

  getAllExpenses():Observable<ExpenseResponse[]> {
    const { email, headers } = this.jwtService.getEmailAndHeaders();

    return this.http.get<ExpenseResponse[]>(`${this.baseUrl}/${email}`, { headers });
  }

  updateExpense(idExpense: number, updatedExpense: ExpenseRequest): Observable<ExpenseResponse> {
    const { email, headers } = this.jwtService.getEmailAndHeaders();

    return this.http.put<ExpenseResponse>(`${this.baseUrl}/${email}/${idExpense}`, updatedExpense, { headers });
  }

  deleteExpense(idExpense: number): Observable<void> {
    const { email, headers } = this.jwtService.getEmailAndHeaders();

    return this.http.delete<void>(`${this.baseUrl}/${email}/${idExpense}`, { headers });
  }
}
