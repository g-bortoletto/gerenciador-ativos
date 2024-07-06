import { Component } from '@angular/core';
import { AtivoFinanceiroService } from '../../services/ativo-financeiro.service';
import { ValorMercado } from '../../common/valor-mercado';
import { AtivoFinanceiro } from '../../common/ativo-financeiro';

@Component({
  selector: 'app-valor-mercado',
  templateUrl: './valor-mercado.component.html',
  styleUrl: './valor-mercado.component.css',
})
export class ValorMercadoComponent {
  public ativoFinanceiro: AtivoFinanceiro = new AtivoFinanceiro(
    0,
    '',
    'RV',
    new Date().toISOString(),
    new Date().toISOString(),
  );
  public ativosFinanceiros: AtivoFinanceiro[] = [];
  public valoresMercado: ValorMercado[] = [];
  public valorMercado: ValorMercado = new ValorMercado(
    0,
    0,
    new Date().toISOString(),
  );

  constructor(public ativoFinanceiroService: AtivoFinanceiroService) {}

  ngOnInit(): void {
    this.consultarAtivosFinanceiros();
  }

  consultarAtivosFinanceiros(): void {
    this.ativoFinanceiroService.consultarAtivosFinanceiros().subscribe({
      next: (response) => (this.ativosFinanceiros = response),
    });
  }

  consultarValoresMercado(): void {
    this.ativoFinanceiroService
      .consultarValoresMercado(this.ativoFinanceiro)
      .subscribe({
        next: (response) => (this.valoresMercado = response),
      });
  }

  incluirValorMercado(): void {
    this.ativoFinanceiroService
      .incluirValorMercado(this.ativoFinanceiro, this.valorMercado)
      .subscribe({
        next: (response) => {
          this.valorMercado = response;
          this.consultarValoresMercado();
        },
      });
  }

  removerValorMercado(
    ativoFinanceiro: AtivoFinanceiro,
    valorMercado: ValorMercado,
  ) {
    this.ativoFinanceiroService
      .removerValorMercado(ativoFinanceiro.id, valorMercado)
      .subscribe({
        next: (_) => {
          this.consultarValoresMercado();
        },
      });
  }
}
