import { Component } from '@angular/core';
import { AtivoFinanceiro } from '../../common/ativo-financeiro';
import { AtivoFinanceiroService as AtivoFinanceiroService } from '../../services/ativo-financeiro.service';

@Component({
  selector: 'app-ativo-financeiro',
  templateUrl: './ativo-financeiro.component.html',
  styleUrl: './ativo-financeiro.component.css'
})

export class AtivoFinanceiroComponent {

  public ativoFinanceiro: AtivoFinanceiro = new AtivoFinanceiro(
    0, 
    "", 
    "RV", 
    new Date().toISOString(), 
    new Date().toISOString());

  constructor(public ativoFinanceiroService: AtivoFinanceiroService) { }

  incluirAtivoFinanceiro(): void {
    this.ativoFinanceiroService.incluirAtivoFinanceiro(this.ativoFinanceiro).subscribe({
      next: response => { this.ativoFinanceiro = response; },
      error: response => console.error(response),
      complete: () => console.log(this.ativoFinanceiro)
    })
  }

  editarAtivoFinanceiro(): void {

  }

  consultarAtivosFinanceiros(): void {

  }

  removerAtivoFinanceiro(): void {

  }

}
