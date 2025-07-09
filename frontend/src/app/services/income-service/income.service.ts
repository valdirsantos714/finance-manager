import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { IncomeResponse } from '../../models/IncomeResponse';
import { Observable, of } from 'rxjs';
import { IncomeCategory } from '../../models/enums/IncomeCategory';

@Injectable({
  providedIn: 'root'
})
export class IncomeService {
  private baseUrl: string = 'http://localhost:8080/api/incomes';

  constructor(private http: HttpClient) { }

  getAllIncomes(): Observable<IncomeResponse[]> {
    return this.http.get<IncomeResponse[]>(this.baseUrl);
  }

  updateIncome(idRenda: number): Observable<IncomeResponse> {
    return this.http.put<IncomeResponse>(`${this.baseUrl}/${idRenda}`, {});
  }

  deleteIncome(idRenda: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${idRenda}`);
  }

}