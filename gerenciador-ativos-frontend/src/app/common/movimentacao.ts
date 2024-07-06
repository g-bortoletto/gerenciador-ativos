import { AtivoFinanceiro } from './ativo-financeiro';

export class Movimentacao {
  constructor(
    public id: number,
    public tipo: string,
    public contaCorrente: number,
    public ativoFinanceiro: AtivoFinanceiro,
    public quantidade: number,
    public valor: number,
    public data: string,
  ) {}
}
