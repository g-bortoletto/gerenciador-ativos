import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AtivoFinanceiro } from '../common/ativo-financeiro';
import { Observable } from 'rxjs';
import { ValorMercado } from '../common/valor-mercado';

@Injectable({
  providedIn: 'root',
})
export class AtivoFinanceiroService {
  constructor(private httpClient: HttpClient) {}

  incluirAtivoFinanceiro(ativoFinanceiro: AtivoFinanceiro): Observable<any> {
    return this.httpClient.post<AtivoFinanceiro>(
      'http://localhost:8080/api/v0/ativos-financeiros',
      ativoFinanceiro,
    );
  }

  editarAtivoFinanceiro(ativoFinanceiro: AtivoFinanceiro): Observable<any> {
    return this.httpClient.put<AtivoFinanceiro>(
      'http://localhost:8080/api/v0/ativos-financeiros/' + ativoFinanceiro.id,
      ativoFinanceiro,
    );
  }

  consultarAtivoFinanceiro(ativoFinanceiro: AtivoFinanceiro): Observable<any> {
    return this.httpClient.get<AtivoFinanceiro>(
      'http://localhost:8080/api/v0/ativos-financeiros/' + ativoFinanceiro.id,
    );
  }

  consultarAtivosFinanceiros(): Observable<any> {
    return this.httpClient.get<AtivoFinanceiro[]>(
      'http://localhost:8080/api/v0/ativos-financeiros',
    );
  }

  removerAtivoFinanceiro(ativoFinanceiro: AtivoFinanceiro): Observable<any> {
    return this.httpClient.delete<AtivoFinanceiro>(
      'http://localhost:8080/api/v0/ativos-financeiros/' + ativoFinanceiro.id,
    );
  }

  incluirValorMercado(
    ativoFinanceiro: AtivoFinanceiro,
    valorMercado: ValorMercado,
  ): Observable<any> {
    return this.httpClient.post<ValorMercado>(
      'http://localhost:8080/api/v0/ativos-financeiros/' +
        ativoFinanceiro.id +
        '/valores-mercado',
      valorMercado,
    );
  }

  consultarValoresMercado(ativoFinanceiro: AtivoFinanceiro): Observable<any> {
    return this.httpClient.get<ValorMercado[]>(
      'http://localhost:8080/api/v0/ativos-financeiros/' +
        ativoFinanceiro.id +
        '/valores-mercado',
    );
  }

  removerValorMercado(
    ativoFinanceiro: number,
    valorMercado: ValorMercado,
  ): Observable<any> {
    return this.httpClient.delete(
      'http://localhost:8080/api/v0/ativos-financeiros/' +
        ativoFinanceiro +
        '/valores-mercado/' +
        valorMercado.id,
    );
  }
}
