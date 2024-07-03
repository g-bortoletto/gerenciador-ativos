import { Component } from '@angular/core';
import { Lancamento } from '../../common/lancamento';
import { LancamentoService } from '../../services/lancamento.service';

@Component({
  selector: 'app-lancamento',
  templateUrl: './lancamento.component.html',
  styleUrl: './lancamento.component.css'
})
export class LancamentoComponent {

  public lancamento: Lancamento = new Lancamento(1, "ENTRADA", 0, "", new Date().toISOString());

  constructor(private lancamentoService: LancamentoService) {}

  incluirLancamento(): void {
    
  }

}
