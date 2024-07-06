import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ContaCorrente } from '../common/conta-corrente';
import { Lancamento } from '../common/lancamento';
import { Posicao } from '../common/posicao';

@Injectable({
  providedIn: 'root',
})
export class ContaCorrenteService {
  constructor(private httpClient: HttpClient) {}

  consultarSaldo(dataPosicao: string): Observable<any> {
    return this.httpClient.get<ContaCorrente>(
      'http://localhost:8080/api/v0/contas-corrente/1/saldo/' + dataPosicao,
    );
  }

  consultarPosicoes(dataPosicao: string): Observable<any> {
    return this.httpClient.get<Posicao[]>(
      'http://localhost:8080/api/v0/contas-corrente/1/posicoes/' + dataPosicao,
    );
  }
}
