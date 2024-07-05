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

  public listaAtivosFinanceiros: AtivoFinanceiro[] = [];

  public mostrarEdicao: boolean = false;

  constructor(public ativoFinanceiroService: AtivoFinanceiroService) { }

  incluirAtivoFinanceiro(): void {
    this.ativoFinanceiroService.incluirAtivoFinanceiro(this.ativoFinanceiro).subscribe({
      next: response => { this.ativoFinanceiro = response; },
      error: response => console.error(response),
      complete: () => console.log(this.ativoFinanceiro)
    })
  }

  editarAtivoFinanceiro(): void {
    this.ativoFinanceiroService.editarAtivoFinanceiro(this.ativoFinanceiro).subscribe( {
      next: response => { this.ativoFinanceiro = response; this.mostrarEdicao = false; }
    })
  }

  consultarAtivoFinanceiro(): boolean {
    this.ativoFinanceiroService.consultarAtivoFinanceiro(this.ativoFinanceiro).subscribe({
      next: response => { this.ativoFinanceiro = response; return true; },
      error: response => console.error(response),
      complete: () => console.log(this.listaAtivosFinanceiros)
    })
    return false;
  }

  consultarAtivosFinanceiros(): void {
    this.ativoFinanceiroService.consultarAtivosFinanceiros().subscribe({
      next: response => { this.listaAtivosFinanceiros = response; },
      error: response => console.error(response),
      complete: () => console.log(this.listaAtivosFinanceiros)
    })
  }

  removerAtivoFinanceiro(): void {
    this.ativoFinanceiroService.removerAtivoFinanceiro(this.ativoFinanceiro).subscribe({
      next: _ => { this.consultarAtivosFinanceiros(); },
      error: response => console.error(response),
    })
  }

  consultarValoresMercado(): void {
    
  }

}
