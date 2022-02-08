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

## Structure

### Disclaimer
- Eu mantive alguns trechos de código comentados porque esse foi um projeto feito durante um curso e esse código comentado contém formas diferentes de escrever o código que está rodando.
- Eu pensei que isso seria importante para revisar o conteúdo que aprendi.

### src/main
- Main package that contains DAOs, Services and more.

#### br.ce.daos
- Contains DAOs examples, but this is a project made to pratice Unit Testing they are really simple.
	- LocacaoDAO: Interface of a rent class that will be mocked.
	- LocacaoDAOFake: Example of the rent class DAO implementantion.
	
#### br.ce.entidades
- Contains all entities of the project.
	- Filme: Movie Entity.
	- Locacao: Movie rental Entity.
	- Usuario: User Entity.
		
#### br.ce.exceptions
- Contains custom Exceptions.
	- FilmeSemEstoqueException: Used when the movie that the User are trying to rent is sold out.
	- LocadoraException: Used to indicate that something went wrong during the rent process.
	- NaoPodeDividirPorZeroException: Used when there are a division per zero during the rent calculus.

#### br.ce.services
- Contains all the logic.
	- Calculadora: A Calculador example, showing the main operations and using some custom exceptions.
	- EmailService: A interface to simulate the Email operations.
	- LocacaoService: A class with all rent operations.
	- SPCService: A interface to simulate a service that consults SPC Database to check if the user have credit restrictions.
	- TimerService: A service with a method for get the current date. Can be used as a workaround to mock the current date with Mockito.

#### br.ce.utils
- Contains the Utility classes.
	- DataUtils: A Utility Date class.
	
### src/test
- Package that contains all unit tests classes.

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
