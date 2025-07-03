import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { IncomeResponse } from '../../models/IncomeResponse';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class IncomeService {
  private baseUrl: string = 'http://localhost:8080/rendas';

  constructor(private http: HttpClient) { }

  listaRendas: IncomeResponse[] = [
    {
      id: 1,
      name: "Salário",
      description: "Salário mensal",
      value: 5000,
      date: "2023-10-01"
    },
    {
      id: 2,
      name: "Renda Extra",
      description: "Venda de produtos online",
      value: 5000,
      date: "2023-10-01"
    }
  ]

  getAllIncomes(): Observable<IncomeResponse[]> {
    return of(this.listaRendas);
  }
  // TODO: Substituir o método acima este aqui debaixo quando o backend estiver pronto
  getAllRendas2(): Observable<IncomeResponse[]> {
    return this.http.get<IncomeResponse[]>(this.baseUrl);
  }

  updateIncome(idRenda: number): Observable<IncomeResponse> {
    return this.http.put<IncomeResponse>(`${this.baseUrl}/${idRenda}`, {});
  }

  deleteIncome(idRenda: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${idRenda}`);
  }

}