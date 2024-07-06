import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ValorMercadoComponent } from './valor-mercado.component';

describe('ValorMercadoComponent', () => {
  let component: ValorMercadoComponent;
  let fixture: ComponentFixture<ValorMercadoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ValorMercadoComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ValorMercadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
