import { Component, OnInit } from '@angular/core';
import { ContaCorrenteService } from '../../services/conta-corrente.service';

@Component({
  selector: 'app-conta-corrente',
  templateUrl: './conta-corrente.component.html',
  styleUrl: './conta-corrente.component.css'
})
export class ContaCorrenteComponent implements OnInit {

  public contaCorrenteSaldo: number = 0;
  public tipoLancamento: string = "ENTRADA";
  public valorLancamento: number = 0;
  public descricaoLancamento: string = "";
  public dataPosicao: string = new Date().toISOString();

  constructor(
    private contaCorrenteService: ContaCorrenteService) {
  }

  ngOnInit(): void {
    this.consultarSaldo();
  }

  consultarSaldo(): void {
    this.contaCorrenteService.consultarSaldo(this.dataPosicao).subscribe({
      next: response => { this.contaCorrenteSaldo = response; console.log(response) },
      error: response => console.error(response),
      complete: () => console.log(this.contaCorrenteSaldo)
    });
  }
}
