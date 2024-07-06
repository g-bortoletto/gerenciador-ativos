import { AtivoFinanceiro } from './ativo-financeiro';
import { Movimentacao } from './movimentacao';

describe('Movimentacao', () => {
  it('should create an instance', () => {
    expect(
      new Movimentacao(
        1,
        'COMPRA',
        1,
        new AtivoFinanceiro(
          0,
          'TESTE_100',
          'RV',
          new Date().toISOString(),
          new Date().toISOString()
        ),
        100,
        1,
        new Date().toISOString()
      )
    ).toBeTruthy();
  });
});
