import { TestBed } from '@angular/core/testing';

import { ContaCorrenteService } from './conta-corrente.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('ContaCorrenteService', () => {
  let service: ContaCorrenteService;
  let httpClient: HttpClientTestingModule;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule]
    });
    service = TestBed.inject(ContaCorrenteService);
    httpClient = TestBed.inject(HttpClientTestingModule)
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
