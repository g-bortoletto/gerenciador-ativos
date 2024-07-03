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

  private urlBase = "http://localhost:8080/api/contas-corrente/1";

  constructor(private httpClient: HttpClient) { }

  consultarSaldo(): Observable<any> {
    return this.httpClient
      .get<ContaCorrente>(this.urlBase + "/saldo");
  }

  incluirLancamento(detalhesLancamento: Lancamento): Observable<any> {
    return this.httpClient
      .post<ContaCorrente>(this.urlBase + "/lancamentos", detalhesLancamento);
  }

}
