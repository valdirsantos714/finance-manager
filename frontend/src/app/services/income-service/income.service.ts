import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { IncomeResponse } from '../../models/IncomeResponse';
import { Observable, of } from 'rxjs';
import { IncomeCategory } from '../../models/enums/IncomeCategory';
import { JwtService } from '../jwt-service/jwt.service';
import { IncomeRequest } from '../../models/IncomeRequest';
@Injectable({
  providedIn: 'root'
})
export class IncomeService {
  private baseUrl: string = 'http://localhost:8080/api/incomes';

  constructor(private http: HttpClient,
    private jwtService: JwtService
  ) { }

  getAllIncomes(): Observable<IncomeResponse[]> {
    const token = this.jwtService.getTokenFromCookie();

    const headers = { Authorization: `Bearer ${token}` };

    const email = this.jwtService.decodeToken(token).sub;
    
    return this.http.get<IncomeResponse[]>(`${this.baseUrl}/${email}`, { headers });
  }

  updateIncome(idRenda: number, updatedIncome: IncomeRequest): Observable<IncomeResponse> {
    return this.http.put<IncomeResponse>(`${this.baseUrl}/${idRenda}`, updatedIncome);
  }

  deleteIncome(idRenda: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${idRenda}`);
  }

}