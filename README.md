# Processo Seletivo - Guilherme Bortoletto

## Gerenciador de Ativos Financeiros

Um conhecido seu está montando uma startup com uma solução de finanças pessoais e te pediu uma ajuda com o desenvolvimento. 

A aplicação tem como propósito ajudar pessoas a gerirem seus ativos financeiros e acompanharem o crescimento de seu patrimônio e saldo em sua conta corrente.

## Comentários iniciais

Acredito que consegui fazer tudo que pede no nível 1 e a maior parte das coisas que pede no nível dois pensando no backend. O frontend é onde tenho menos experiência, apesar de já ter feito alguns cursos, portanto acabei enroscando um pouco lá.

Como eu já trabalho, eu tive pouco tempo pra mexer no projeto, e a minha cidade inteira ficou sem internet no sábado, pra ajudar...

Não tive tempo de realizar testes no frontend e não sei se vou conseguir finalizar todas as funcionalidades dele no prazo, mas espero que o que eu entregar seja suficiente pra vocês me avaliarem e tomarem uma decisão. 🥲

## Linguagem

Escolhi o Java 17 (Spring) como linguagem de programação do backend e TypeScript (Angular) como linguagem do frontend, por serem linguagens que sempre tive interesse em aprender e me aprofundar, além de serem tecnologias utilizadas na Maps.

Como sistema de build eu escolhi o Maven, pois testei o Gradle, que diz ser um sistema mais robusto, e ele demorava mais que o dobro pra compilar um projeto vazio. Como precisava de agilidade no processo, decidi utilizar a ferramenta mais simples.

O banco de dados escolhido foi o H2, pois ele é facilmente embarcado utilizando o Spring e tem uma abordagem relacional, que eu, pessoalmente, prefiro.

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


Acredito que o programa contempla tudo que foi pedido no nível 1 e algumas coisas do nível 2. Considerei datas desde o início, tanto saldo como posição são calculados a partir de datas, não são dados fixos guardados no banco de dados.

Existem três *controllers*, que gerenciam APIs de conta corrente, lançamentos e ativos financeiros. Para fornecer os dados desses *controllers*, existem cinco serviços. Gostaria de ter separado melhor as responsabilidades entre esses serviços, mas acabei ficando sem tempo.

## Testes

As funcionalidades do programa foram testadas através de testes unitários, que utilizam mock para evitar interação com o banco de dados, mas também através de testes de integração, que vão desde a requisição HTTP até o banco de dados, realizando o fluxo completo do programa.

## Comentários

Olhando pra trás, se pudesse voltar, provavelmente não escolheria uma linguagem que não domino para fazer o projeto. Estou bem mais acostumado com tecnologias .NET, que, apesar de parecidas, têm detalhes bastante descasados. Também não tenho uma grande experiência com desenvolvimento web e os padrões utilizados, mas foi divertido aumentar essa familiaridade.

A lógica de programação é tranquila, nunca tive muito problema com isso, mas os frameworks em si são complicados e têm muitos detalhes. Gostaria de ter tido mais tempo para aperfeiçoar o código, entender como é a performance dessas tecnologias e fazer muito mais testes.

Também decidi usar o Hibernate, não sei se isso é utilizado aí na Maps ou não. Não sou o maior fã de ORMs, mas quis experimentar, já que foi o que o Spring recomendou.

No geral, a experiência foi legal, provavelmente não vou conseguir entregar tudo o que é pedido, mas espero que consiga mostrar que consigo ser um programador competente, mesmo quando preciso aprender coisas novas e me virar.

Agradeço a oportunidade de participar do processo seletivo, espero que me considerem como um candidato apto para trabalhar na empresa.

🙂
