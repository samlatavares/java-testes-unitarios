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
- Contains all Data Builders of the Entities.
	- FilmeBuilder: Movie builder.
	- LocacaoBuilder: Rent builder.
	- UsuarioBuilder: User builder.
	
#### br.ce.runners
- Contains the runners implementations.
	- ParallelRunner: Contains an example of an implementantion that can be used to run Parallel Tests.

#### br.ce.servicos
- Contains all test classes.
	- CalculadoraMockTest: Contains some examples of how Spy and Mock Annotations works.
	- CalculadoraTest: Contains the tests of the Calculadora (Calculator) class.
	- CalculoValorLocacaoTest: Contains the test of the rent calculus.
	- LocacaoServiceTest: Contains all tests of the LocacaoService (rent) class.
	- LocacaoServiceTest_PowerMock: Contains examples of methods of the LocacaoServiceTest that could use PowerMock.	

#### br.ce.servicos.matchers
- Contains all custom matchers that can be used on the tests asserts.

#### br.ce.suites
- Contains a Tests Suite that runs all tests from CalculoValorLocacaoTest and LocacaoServiceTest classes.
