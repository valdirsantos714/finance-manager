import { HttpClientModule } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RendasResponse } from '../models/RendasResponse';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RendaService {

  constructor(private http: HttpClientModule) { }

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

}