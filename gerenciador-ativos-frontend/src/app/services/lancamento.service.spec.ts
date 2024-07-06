import { TestBed } from '@angular/core/testing';

import { LancamentoService } from './lancamento.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('LancamentoService', () => {
  let service: LancamentoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(LancamentoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
