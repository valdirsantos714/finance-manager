import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DespesasResponse } from '../../models/DespesasResponse';
import { Observable, of } from 'rxjs';
import { DespesasRequest } from '../../models/DespesasRequest';

@Injectable({
  providedIn: 'root'
})
export class DespesaService {
  private baseUrl: string = 'http://localhost:8080/despesas';

  constructor(private http: HttpClient) { }

  listaDespesas: DespesasResponse[] = [
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

  getAllDespesas():Observable<DespesasResponse[]> {
    return of(this.listaDespesas);
  }

  updateDespesa(idDespesa: number, updatedDespesa: DespesasRequest): Observable<DespesasResponse> {
    return this.http.put<DespesasResponse>(this.baseUrl + '/' + idDespesa, updatedDespesa);
  }

  deleteDespesa(idDespesa: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${idDespesa}`);
  }
}

