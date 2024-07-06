import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ContaCorrenteComponent } from './components/conta-corrente/conta-corrente.component';
import { AtivoFinanceiroComponent } from './components/ativo-financeiro/ativo-financeiro.component';
import { PosicaoComponent } from './components/posicao/posicao.component';
import { LancamentoComponent } from './components/lancamento/lancamento.component';
import { MovimentacaoComponent } from './components/movimentacao/movimentacao.component';
import { ValorMercadoComponent } from './components/valor-mercado/valor-mercado.component';

const routes: Routes = [
  { path: '', redirectTo: '/saldo', pathMatch: 'full' },
  { path: 'saldo', component: ContaCorrenteComponent },
  { path: 'posicoes', component: PosicaoComponent },
  { path: 'lancamentos', component: LancamentoComponent },
  { path: 'ativos-financeiros', component: AtivoFinanceiroComponent },
  { path: 'movimentacoes', component: MovimentacaoComponent },
  { path: 'valores-mercado', component: ValorMercadoComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
