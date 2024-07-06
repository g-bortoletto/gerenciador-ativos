import { AtivoFinanceiro } from './ativo-financeiro';

describe('AtivoFinanceiro', () => {
  it('should create an instance', () => {
    expect(
      new AtivoFinanceiro(
        0,
        'TESTE_100',
        'RV',
        new Date().toISOString(),
        new Date().toISOString()
      )
    ).toBeTruthy();
  });
});
