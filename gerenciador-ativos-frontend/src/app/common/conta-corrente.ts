import { Lancamento } from "./lancamento";

export class ContaCorrente {

	constructor(
		public id: number,
		public saldo: number,
		public listaLancamentos: Lancamento[]) {
	}

}
