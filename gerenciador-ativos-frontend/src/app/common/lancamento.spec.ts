import { Lancamento } from './lancamento';

describe('Lancamento', () => {
  it('should create an instance', () => {
    expect(
      new Lancamento(1, 'ENTRADA', 100, 'default', new Date().toISOString())
    ).toBeTruthy();
  });
});
