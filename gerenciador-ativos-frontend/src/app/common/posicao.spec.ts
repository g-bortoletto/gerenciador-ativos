import { AtivoFinanceiro } from './ativo-financeiro';
import { Posicao } from './posicao';

describe('Posicao', () => {
  it('should create an instance', () => {
    expect(
      new Posicao(
        new AtivoFinanceiro(
          0,
          'TESTE_100',
          'RV',
          new Date().toISOString(),
          new Date().toISOString()
        ),
        100,
        1,
        0.5,
        50
      )
    ).toBeTruthy();
  });
});
