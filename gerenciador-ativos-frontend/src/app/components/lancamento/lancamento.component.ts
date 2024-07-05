import { Component } from '@angular/core';
import { Lancamento } from '../../common/lancamento';
import { LancamentoService } from '../../services/lancamento.service';
import { ContaCorrenteService } from '../../services/conta-corrente.service';

@Component({
  selector: 'app-lancamento',
  templateUrl: './lancamento.component.html',
  styleUrl: './lancamento.component.css'
})
export class LancamentoComponent {

  public lancamento: Lancamento = new Lancamento(1, "ENTRADA", 0, "", new Date().toISOString());
  public lancamentos: Lancamento[] = [];
  public dataInicio: string = "";
  public dataFim: string = "";

  public contaCorrenteSaldo: number = 0;

  constructor(private lancamentoService: LancamentoService, private contaCorrenteService: ContaCorrenteService) {}

  ngOnInit(): void {
    this.consultarSaldo();
  }

  consultarSaldo(): void {
    this.contaCorrenteService.consultarSaldo(new Date().toISOString()).subscribe({
      next: response => { this.contaCorrenteSaldo = response; console.log(response) },
      error: response => console.error(response),
      complete: () => console.log(this.contaCorrenteSaldo)
    });
  }

  incluirLancamento(): void {
    this.lancamentoService.incluirLancamento(this.lancamento).subscribe({
      next: response => { this.lancamento = response; },
      error: response => console.error(response),
      complete: () => this.consultarSaldo()
    });
  }

  consultarLancamentos(): void {
    this.lancamentoService.consultarLancamentos(this.dataInicio, this.dataFim).subscribe({
      next: response => { this.lancamentos = response; }
    })
  }

}
