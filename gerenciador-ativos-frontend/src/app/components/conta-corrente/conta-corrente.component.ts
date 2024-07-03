import { Component, OnInit } from '@angular/core';
import { ContaCorrenteService } from '../../services/conta-corrente.service';
import { ContaCorrente } from '../../common/conta-corrente';
import { Lancamento } from '../../common/lancamento';

@Component({
  selector: 'app-conta-corrente',
  templateUrl: './conta-corrente.component.html',
  styleUrl: './conta-corrente.component.css'
})
export class ContaCorrenteComponent implements OnInit {

  public contaCorrente: ContaCorrente | null = null;
  public tipoLancamento: string = "ENTRADA";
  public valorLancamento: number = 0;
  public descricaoLancamento: string = "";

  constructor(
    private contaCorrenteService: ContaCorrenteService) {
  }
  
  ngOnInit(): void {
    this.consultarSaldo();
  }

  consultarSaldo(): void {
    this.contaCorrenteService.consultarSaldo().subscribe({
        next: response => { this.contaCorrente = response; console.log(response) },
        error: response => console.error(response),
        complete: () => console.log(this.contaCorrente?.saldo)
    });
  }

  incluirLancamento(): void {
    if (this.contaCorrente != null) {
      this.contaCorrenteService.incluirLancamento(new Lancamento(
        this.tipoLancamento,
        this.valorLancamento,
        this.descricaoLancamento,
        new Date()))
        .subscribe({
          next: response => console.log(response),
          error: response => console.error(response),
          complete: () => this.consultarSaldo()
        });
    }
  }

}
