# java-testes-unitarios
Project made during "<a href="https://www.udemy.com/course/testes-unitarios-em-java/" target="_blank">Unit Tests in Java: Master JUnit, Mockito and TDD</a>" course on Udemy. It's a Java Project with Unit Testing using JUnit and other technologies detailed below. The basis project were made available by the tutor.

## Used Technologies
- Java Language.
- JUnit.
- Mockito.
- PowerMock.
- Maven.

## Structure
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
