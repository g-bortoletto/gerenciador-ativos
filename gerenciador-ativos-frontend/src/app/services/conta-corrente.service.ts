import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ContaCorrente } from '../common/conta-corrente';
import { Lancamento } from '../common/lancamento';

@Injectable({
  providedIn: 'root'
})
export class ContaCorrenteService {

  constructor(private httpClient: HttpClient) { }

  consultarSaldo(): Observable<any> {
    return this.httpClient
      .get<ContaCorrente>("http://localhost:8080/api/v0/contas-corrente/1/saldo/" + new Date().toISOString());
  }

  incluirLancamento(detalhesLancamento: Lancamento): Observable<any> {
    return this.httpClient
      .post<ContaCorrente>("http://localhost:8080/api/v0/lancamentos", detalhesLancamento);
  }

}
