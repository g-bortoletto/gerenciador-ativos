import { Injectable } from '@angular/core';
import { Lancamento } from '../common/lancamento';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class LancamentoService {
  constructor(private httpClient: HttpClient) {}

  incluirLancamento(lancamento: Lancamento): Observable<any> {
    return this.httpClient.post<Lancamento>(
      'http://localhost:8080/api/v0/lancamentos',
      lancamento,
    );
  }

  consultarLancamentos(dataInicio: string, dataFim: string): Observable<any> {
    return this.httpClient.get<Lancamento[]>(
      'http://localhost:8080/api/v0/lancamentos/1?data-i=' +
        dataInicio +
        '&data-f=' +
        dataFim,
    );
  }
}
