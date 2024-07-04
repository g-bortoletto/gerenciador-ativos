import { TestBed } from '@angular/core/testing';

import { AtivoFinanceiroService } from './ativo-financeiro.service';

describe('AtivoFinanceiroService', () => {
  let service: AtivoFinanceiroService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AtivoFinanceiroService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
