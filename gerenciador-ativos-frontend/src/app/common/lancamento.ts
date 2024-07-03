import { ContaCorrente } from "./conta-corrente";

export class Lancamento {

	constructor(
		public tipo: string,
		public valor: number,
		public descricao: string,
		public data: Date) {
	}

}
