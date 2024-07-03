import { AtivoFinanceiro } from "./ativo-financeiro";

export class Posicao {

	constructor(
		public ativoFinanceiro: AtivoFinanceiro,
		public quantidadeTotal: number,
		public valorMercadoTotal: number,
		public rendimento: number,
		public lucro: number) {}

}
