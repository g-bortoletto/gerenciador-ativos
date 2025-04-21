# Gerenciador de Ativos Financeiros

Essa aplicação tem como propósito ajudar pessoas a gerirem seus ativos financeiros e acompanharem o crescimento de seu patrimônio e saldo em sua conta corrente.

## Erros e status codes

Decidi criar algumas exceções personalizadas, apesar de não ter costume de fazer esse tipo de coisa. Geralmente, prefiro utilizar uma exceção que já é mais conhecida e colocar uma mensagem descritiva junto.

Uso o mesmo raciocínio para status codes, prefiro utilizar os que já são mais conhecidos, visto que lançar algum status obscuro pode dificultar a resolução de problemas.


| EXCEÇÃO                              | DESCRIÇÃO                                                                                                     |
| ------------------------------------ | ------------------------------------------------------------------------------------------------------------- |
| ContaInexistenteException            | Utilizada quando tenta-se realizar uma consulta em uma conta que não existe.                                  |
| DataMovimentacaoInvalidaException    | Acontece quando é realizada uma tentativa de movimentação ou lançamento em uma data inválida.                 |
| QuantidadeAtivoInsuficienteException | Acontece quando não existem ativos suficientes para vender.                                                   |
| SaldoInsuficienteException           | Ocorre quando não há saldo suficiente para  realizar um lançamento ou movimentação.                           |
| SaldoNegativoException               | Ocorre quando algo crítico deu errado e o saldo está negativo, de alguma forma. Esse erro não deve acontecer. |

| HTTP STATUS | DESCRIÇÃO                                                                                                                |
| ----------- | ------------------------------------------------------------------------------------------------------------------------ |
| OK          | Utilizado para todas as operações que finalizam com sucesso.                                                             |
| Bad request | Utilizado para problemas com parâmetros inválidos, como valores ou datas.                                                |
| Not found   | Utilizado quando é realizada uma tentativa de acessar um objeto que não está presente em uma lista ou no banco de dados. |

## Estrutura do programa

O programa é relativamente simples e utiliza uma estrutura de pacotes  padrão:

- **controller:** utilizado para guardar componentes que interagem com a web.
- **dto:** utilizado para armazenar objetos de transferência de dados.
- **entity:** guarda entidades mapeadas ao banco de dados pela ORM.
- **exception:** todas as classes de exceção personalizadas.
- **model:** objetos que são basicamente dados, mas não precisam ser mapeados no banco de dados.
- **repository:** coleções de entidades mapeadas pelo banco de dados.
- **service:** unidades onde acontece a lógica do programa.
	- **helper:** para manter a separação dos domínios, foi criada uma classe para facilitar interação entre alguns objetos.

Esse é um modelo que representa as entidades do banco de dados e como elas interagem:


![image](https://github.com/g-bortoletto/gerenciador-ativos/assets/20934524/66720594-e8f2-4b2a-9974-7f926f3486c5)


Considerei datas desde o início, tanto saldo como posição são calculados a partir de datas, não são dados fixos guardados no banco de dados.

Existem três *controllers*, que gerenciam APIs de conta corrente, lançamentos e ativos financeiros. Para fornecer os dados desses *controllers*, existem cinco serviços. Gostaria de ter separado melhor as responsabilidades entre esses serviços, mas acabei ficando sem tempo.

## Testes

As funcionalidades do programa foram testadas através de testes unitários, que utilizam mock para evitar interação com o banco de dados, mas também através de testes de integração, que vão desde a requisição HTTP até o banco de dados, realizando o fluxo completo do programa.

No geral, a experiência foi legal, provavelmente não vou conseguir entregar tudo o que é pedido, mas espero que consiga mostrar que consigo ser um programador competente, mesmo quando preciso aprender coisas novas e me virar.

Agradeço a oportunidade de participar do processo seletivo, espero que me considerem como um candidato apto para trabalhar na empresa.

🙂
