import { ContaCorrente } from './conta-corrente';
import { Lancamento } from './lancamento';

describe('ContaCorrente', () => {
  it('should create an instance', () => {
    expect(new ContaCorrente(1, 1000, [])).toBeTruthy();
  });
});
