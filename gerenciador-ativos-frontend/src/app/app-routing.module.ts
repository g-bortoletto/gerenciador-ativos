import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ContaCorrenteComponent } from './components/conta-corrente/conta-corrente.component';
import { AtivoFinanceiroComponent } from './components/ativo-financeiro/ativo-financeiro.component';

const routes: Routes = [
  { path: '', redirectTo: '/contas-corrente', pathMatch: 'full' },
  { path: 'contas-corrente', component: ContaCorrenteComponent },
  { path: 'ativos-financeiros', component: AtivoFinanceiroComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
