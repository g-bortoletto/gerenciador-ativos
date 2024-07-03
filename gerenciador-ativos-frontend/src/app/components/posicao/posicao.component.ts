import { Component } from '@angular/core';
import { ContaCorrenteService } from '../../services/conta-corrente.service';
import { Posicao } from '../../common/posicao';

@Component({
  selector: 'app-posicao',
  templateUrl: './posicao.component.html',
  styleUrl: './posicao.component.css'
})

export class PosicaoComponent {

  public posicoes: Posicao[] = [];

  constructor(public contaCorrenteService: ContaCorrenteService) {}

  ngOnInit(): void {
    this.consultarPosicoes();
  }

  consultarPosicoes(): void {
    this.contaCorrenteService.consultarPosicoes().subscribe({
      next: response => { this.posicoes = response; console.log(response) },
      error: response => console.error(response),
      complete: () => console.log(this.posicoes)
    })
  }

}
