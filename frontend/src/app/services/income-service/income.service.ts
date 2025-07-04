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

  listaRendas: IncomeResponse[] = [
    {
      id: 1,
      name: "Salário",
      description: "Salário mensal",
      amount: 5000,
      date: "2023-10-01",
      userId: 1,
      category: IncomeCategory.SALARY
    },
    {
      id: 2,
      name: "Renda Extra",
      description: "Venda de produtos online",
      amount: 1500,
      date: "2023-10-05",
      userId: 1,
      category: IncomeCategory.FREELANCE
    }
  ]

  /*getAllIncomes(): Observable<IncomeResponse[]> {
    return of(this.listaRendas);
  }*/

  // TODO: Substituir o método acima este aqui debaixo quando o backend estiver pronto
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