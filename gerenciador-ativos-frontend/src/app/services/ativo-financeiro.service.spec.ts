import { TestBed } from '@angular/core/testing';

import { AtivoFinanceiroService } from './ativo-financeiro.service';

import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('AtivoFinanceiroService', () => {
  let service: AtivoFinanceiroService;
  let httpClient: HttpClientTestingModule;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    service = TestBed.inject(AtivoFinanceiroService);
    httpClient = TestBed.inject(HttpClientTestingModule)
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
