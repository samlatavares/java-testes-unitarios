# java-testes-unitarios
<p align="center">
	<a href="https://github.com/samlatavares/java-testes-unitarios/blob/main/README.md">English</a> | <span>Português</span>
</p>

Projeto feito durante o curso "<a href="https://www.udemy.com/course/testes-unitarios-em-java/" target="_blank">Testes unitários em JAVA: Domine JUnit, Mockito e TDD</a>" da Udemy. É um projeto Java com testes unitários utilizando JUnit e outras tecnologias detalhadas abaixo. O projeto base foi disponibilizado pelo professor e é um projeto de locação de filmes.

## Tecnologias Utilizadas
- Java Language.
- JUnit.
- Mockito.
- PowerMock.
- Maven.

## Estrutura

### Importante
- Eu mantive alguns trechos de código comentados porque esse foi um projeto feito durante um curso e esse código comentado contém formas diferentes de escrever o código que está rodando.
- Eu pensei que isso seria importante para revisar o conteúdo que aprendi.

### src/main
- Pacote principal que contém DAOs, serviços entre outros.

#### br.ce.daos
- Contém exemplos de DAOs, mas como esse foi um projeto para praticar Testes Unitários, eles são bem simples.
	- LocacaoDAO: Interface do DAO da classe de locação que será "mockada".
	- LocacaoDAOFake: Examplo da implementação do DAO da classe de locação.
	
#### br.ce.entidades
- Contém todas as entidades do projeto.
	- Filme.
	- Locacao.
	- Usuario.
		
#### br.ce.exceptions
- Contém exceções customizadas.
	- FilmeSemEstoqueException: Utilizada quando o filme que o usuário está tentando alugar está esgotado.
	- LocadoraException: Utilizada para indicar que algo deu errado durante o processo de aluguel.
	- NaoPodeDividirPorZeroException: Utilizada quando há uma divisão por zero durante o cálculo do aluguel.

#### br.ce.services
- Contém toda a lógica.
	- Calculadora: Um exemplo de calculadora, mostrando as principais operações e utilizando exceções customizadas.
	- EmailService: Uma interface para simular as operações de e-mail.
	- LocacaoService: Uma classe com todas as operações de aluguel.
	- SPCService: Uma interface para simular um serviço que consulta a base de dados to SPC para verificar se o usuário possui restrições de crédito.
	- TimerService: Um serviço com um método para recuperar a data atual. Pode ser usado como uma alternativa para "mockar" a data atual utilizando o Mockito.

#### br.ce.utils
- Contém as classes utilitárias.
	- DataUtils.
	
### src/test
- Pacote que contém todas as classes com os Testes de Unidade.

#### br.ce.builders
- Contém todos os Data Builders das entidades.
	- FilmeBuilder.
	- LocacaoBuilder.
	- UsuarioBuilder.
	
#### br.ce.runners
- Contém as implementações dos Runners.
	- ParallelRunner: Contém um exemplo de implementação que pode ser utilizada para executar Testes Paralelos.

#### br.ce.servicos
- Contém todas as classes de testes.
	- CalculadoraMockTest: Contém alguns exemplos de como Spy e Mock Annotations funcionam.
	- CalculadoraTest: Contém os testes da classe Calculadora.
	- CalculoValorLocacaoTest: Contém os testes de cálculo da classe Locacao.
	- LocacaoServiceTest: Contém os testes da classe Locacao.
	- LocacaoServiceTest_PowerMock: Contém exemplos de métodos da classe LocacaoServiceTest que poderiam utilizar PowerMock.	

#### br.ce.servicos.matchers
- Contém todos os matchers customizados que podem ser utilizados nas assertivas dos testes.

#### br.ce.suites
- Contém um Suite de testes que roda todos os testes das classes CalculoValorLocacaoTest e LocacaoServiceTest.
