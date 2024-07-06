import { ValorMercado } from './valor-mercado';

describe('ValorMercado', () => {
  it('should create an instance', () => {
    expect(new ValorMercado(1, 100, new Date().toISOString())).toBeTruthy();
  });
});
