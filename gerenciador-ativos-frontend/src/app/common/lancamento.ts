
export class Lancamento {

	constructor(
		public contaCorrenteId: number,
		public tipo: string,
		public valor: number,
		public descricao: string,
		public data: string) {
	}

}
