import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AtivoFinanceiro } from '../common/ativo-financeiro';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class AtivoFinanceiroService {

  constructor(private httpClient: HttpClient) { }

  incluirAtivoFinanceiro(ativoFinanceiro: AtivoFinanceiro): Observable<any> {
    return this.httpClient
      .post<AtivoFinanceiro>("http://localhost:8080/api/v0/ativos-financeiros", ativoFinanceiro);
  }

  editarAtivoFinanceiro(ativoFinanceiro: AtivoFinanceiro): Observable<any> {
    return this.httpClient
      .put<AtivoFinanceiro>("http://localhost:8080/api/v0/ativos-financeiros", ativoFinanceiro);
  }

  consultarAtivosFinanceiros(): Observable<any> {
    return this.httpClient
      .get<AtivoFinanceiro[]>("http://localhost:8080/api/v0/ativos-financeiros");
  }

  removerAtivoFinanceiro(ativoFinanceiro: AtivoFinanceiro): Observable<any> {
    return this.httpClient
      .delete<AtivoFinanceiro>("http://localhost:8080/api/v0/ativos-financeiros");
  }

}
