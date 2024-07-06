import { Component } from '@angular/core';
import { MovimentacaoService } from '../../services/movimentacao.service';
import { Movimentacao } from '../../common/movimentacao';

@Component({
  selector: 'app-movimentacao',
  templateUrl: './movimentacao.component.html',
  styleUrl: './movimentacao.component.css',
})
export class MovimentacaoComponent {
  public dataInicio: string = new Date().toISOString();
  public dataFim: string = new Date().toISOString();
  public movimentacoes: Movimentacao[] = [];

  constructor(public movimentacaoService: MovimentacaoService) {}
}
