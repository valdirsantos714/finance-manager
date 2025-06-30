import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RendasResponse } from '../../models/RendasResponse';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RendaService {
  private baseUrl: string = 'http://localhost:8080/rendas';

  constructor(private http: HttpClient) { }

  listaRendas: RendasResponse[] = [
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

  getAllRendas(): Observable<RendasResponse[]> {
    return of(this.listaRendas);
  }
  // TODO: Substituir o método acima este aqui debaixo quando o backend estiver pronto
  getAllRendas2(): Observable<RendasResponse[]> {
    return this.http.get<RendasResponse[]>(this.baseUrl);
  }

  updateRenda(idRenda: number): Observable<RendasResponse> {
    return this.http.put<RendasResponse>(`${this.baseUrl}/${idRenda}`, {});
  }

  deleteRenda(idRenda: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${idRenda}`);
  }

}