INSERT INTO conta_corrente(id) VALUES (1);

INSERT INTO ativo_financeiro (id, tipo, nome, data_emissao, data_vencimento) VALUES (1, 1, 'TESTE_01', '2024-07-01T00:00:00', '2024-07-05T00:00:00');
INSERT INTO ativo_financeiro (id, tipo, nome, data_emissao, data_vencimento) VALUES (2, 2, 'TESTE_02', '2024-07-01T00:00:00', '2024-07-05T00:00:00');
INSERT INTO ativo_financeiro (id, tipo, nome, data_emissao, data_vencimento) VALUES (3, 0, 'TESTE_03', '2024-07-01T00:00:00', '2024-07-05T00:00:00');
INSERT INTO ativo_financeiro (id, tipo, nome, data_emissao, data_vencimento) VALUES (4, 1, 'TESTE_04', '2024-07-01T00:00:00', '2024-07-05T00:00:00');
INSERT INTO ativo_financeiro (id, tipo, nome, data_emissao, data_vencimento) VALUES (5, 2, 'TESTE_05', '2024-07-01T00:00:00', '2024-07-05T00:00:00');

ALTER TABLE ativo_financeiro ALTER COLUMN id RESTART WITH 6;

INSERT INTO valor_mercado (id, ativo_financeiro_id, valor, data) VALUES (1, 1, 10.00, '2024-07-02T00:00:00');
INSERT INTO valor_mercado (id, ativo_financeiro_id, valor, data) VALUES (2, 1, 11.00, '2024-07-03T00:00:00');
INSERT INTO valor_mercado (id, ativo_financeiro_id, valor, data) VALUES (3, 2, 10.00, '2024-07-02T00:00:00');
INSERT INTO valor_mercado (id, ativo_financeiro_id, valor, data) VALUES (4, 2, 11.00, '2024-07-03T00:00:00');
INSERT INTO valor_mercado (id, ativo_financeiro_id, valor, data) VALUES (5, 3, 10.00, '2024-07-02T00:00:00');
INSERT INTO valor_mercado (id, ativo_financeiro_id, valor, data) VALUES (6, 3, 11.00, '2024-07-03T00:00:00');
INSERT INTO valor_mercado (id, ativo_financeiro_id, valor, data) VALUES (7, 4, 10.00, '2024-07-02T00:00:00');
INSERT INTO valor_mercado (id, ativo_financeiro_id, valor, data) VALUES (8, 4, 11.00, '2024-07-03T00:00:00');
INSERT INTO valor_mercado (id, ativo_financeiro_id, valor, data) VALUES (9, 5, 11.00, '2024-07-02T00:00:00');
INSERT INTO valor_mercado (id, ativo_financeiro_id, valor, data) VALUES (10, 5, 12.00, '2024-07-03T00:00:00');

ALTER TABLE valor_mercado ALTER COLUMN id RESTART WITH 11;

INSERT INTO lancamento (tipo, valor, conta_corrente_id, data, id, descricao) VALUES (0, 1000.0, 1, '2024-07-01T01:00:00',  1, '');
INSERT INTO movimentacao (quantidade, tipo, valor, ativo_financeiro_id, conta_corrente_id, data, id) VALUES (100.0, 0, 1000.0, 1, 1, '2024-07-01T01:00:00', 1);

INSERT INTO lancamento (tipo, valor, conta_corrente_id, data, id, descricao) VALUES (0, 100.0, 1, '2024-07-01T01:00:00',  2, '');
INSERT INTO lancamento (tipo, valor, conta_corrente_id, data, id, descricao) VALUES (0, 100.0, 1, '2024-07-02T01:00:00',  3, '');
INSERT INTO lancamento (tipo, valor, conta_corrente_id, data, id, descricao) VALUES (0, 100.0, 1, '2024-07-03T01:00:00',  4, '');
INSERT INTO lancamento (tipo, valor, conta_corrente_id, data, id, descricao) VALUES (0, 100.0, 1, '2024-07-04T01:00:00',  5, '');
INSERT INTO lancamento (tipo, valor, conta_corrente_id, data, id, descricao) VALUES (0, 100.0, 1, '2024-07-01T05:00:00',  6, '');
INSERT INTO lancamento (tipo, valor, conta_corrente_id, data, id, descricao) VALUES (1,  50.0, 1, '2024-07-01T03:00:00',  7, '');
INSERT INTO lancamento (tipo, valor, conta_corrente_id, data, id, descricao) VALUES (1,  50.0, 1, '2024-07-02T03:00:00',  8, '');
INSERT INTO lancamento (tipo, valor, conta_corrente_id, data, id, descricao) VALUES (1,  50.0, 1, '2024-07-03T03:00:00',  9, '');
INSERT INTO lancamento (tipo, valor, conta_corrente_id, data, id, descricao) VALUES (1,  50.0, 1, '2024-07-04T03:00:00', 10, '');
INSERT INTO lancamento (tipo, valor, conta_corrente_id, data, id, descricao) VALUES (1, 100.0, 1, '2024-07-01T06:00:00', 11, '');

INSERT INTO movimentacao (quantidade, tipo, valor, ativo_financeiro_id, conta_corrente_id, data, id) VALUES (10.0, 0, 100.0, 1, 1, '2024-07-01T01:00:00',  2);
INSERT INTO movimentacao (quantidade, tipo, valor, ativo_financeiro_id, conta_corrente_id, data, id) VALUES (10.0, 0, 100.0, 1, 1, '2024-07-02T01:00:00',  3);
INSERT INTO movimentacao (quantidade, tipo, valor, ativo_financeiro_id, conta_corrente_id, data, id) VALUES (10.0, 0, 100.0, 1, 1, '2024-07-03T01:00:00',  4);
INSERT INTO movimentacao (quantidade, tipo, valor, ativo_financeiro_id, conta_corrente_id, data, id) VALUES (10.0, 0, 100.0, 1, 1, '2024-07-04T01:00:00',  5);
INSERT INTO movimentacao (quantidade, tipo, valor, ativo_financeiro_id, conta_corrente_id, data, id) VALUES (10.0, 0, 100.0, 1, 1, '2024-07-01T05:00:00',  6);
INSERT INTO movimentacao (quantidade, tipo, valor, ativo_financeiro_id, conta_corrente_id, data, id) VALUES (10.0, 1,  50.0, 1, 1, '2024-07-01T03:00:00',  7);
INSERT INTO movimentacao (quantidade, tipo, valor, ativo_financeiro_id, conta_corrente_id, data, id) VALUES (10.0, 1,  50.0, 1, 1, '2024-07-02T03:00:00',  8);
INSERT INTO movimentacao (quantidade, tipo, valor, ativo_financeiro_id, conta_corrente_id, data, id) VALUES (10.0, 1,  50.0, 1, 1, '2024-07-03T03:00:00',  9);
INSERT INTO movimentacao (quantidade, tipo, valor, ativo_financeiro_id, conta_corrente_id, data, id) VALUES (10.0, 1,  50.0, 1, 1, '2024-07-04T03:00:00', 10);
INSERT INTO movimentacao (quantidade, tipo, valor, ativo_financeiro_id, conta_corrente_id, data, id) VALUES (10.0, 1, 100.0, 1, 1, '2024-07-01T06:00:00', 11);

ALTER TABLE lancamento ALTER COLUMN id RESTART WITH 12;
ALTER TABLE movimentacao ALTER COLUMN id RESTART WITH 12;

COMMIT;