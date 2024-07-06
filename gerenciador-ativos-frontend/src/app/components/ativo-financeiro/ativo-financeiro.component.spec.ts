import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AtivoFinanceiroComponent } from './ativo-financeiro.component';
import { AtivoFinanceiroService } from '../../services/ativo-financeiro.service';

describe('AtivoFinanceiroComponent', () => {
  let component: AtivoFinanceiroComponent;
  let fixture: ComponentFixture<AtivoFinanceiroComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AtivoFinanceiroService],
      declarations: [AtivoFinanceiroComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(AtivoFinanceiroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
