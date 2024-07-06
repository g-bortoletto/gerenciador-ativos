import { TestBed } from '@angular/core/testing';

import { MovimentacaoService } from './movimentacao.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('MovimentacaoService', () => {
  let service: MovimentacaoService;
  let httpClient: HttpClientTestingModule;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    service = TestBed.inject(MovimentacaoService);
    httpClient = TestBed.inject(HttpClientTestingModule);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
