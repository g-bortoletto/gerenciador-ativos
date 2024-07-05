import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { ContaCorrenteService } from './services/conta-corrente.service';
import { ContaCorrenteComponent } from './components/conta-corrente/conta-corrente.component';
import { FormsModule } from '@angular/forms';
import { AtivoFinanceiroComponent } from './components/ativo-financeiro/ativo-financeiro.component';
import { LancamentoComponent } from './components/lancamento/lancamento.component';
import { PosicaoComponent } from './components/posicao/posicao.component';
import { MovimentacaoComponent } from './components/movimentacao/movimentacao.component';
import { ValorMercadoComponent } from './components/valor-mercado/valor-mercado.component';

@NgModule({
  declarations: [
    AppComponent,
    ContaCorrenteComponent,
    AtivoFinanceiroComponent,
    LancamentoComponent,
    PosicaoComponent,
    MovimentacaoComponent,
    ValorMercadoComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [ContaCorrenteService],
  bootstrap: [AppComponent]
})
export class AppModule { }
