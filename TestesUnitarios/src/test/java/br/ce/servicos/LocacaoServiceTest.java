package br.ce.servicos;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.entidades.Filme;
import br.ce.entidades.Locacao;
import br.ce.entidades.Usuario;
import br.ce.exceptions.FilmeSemEstoqueException;
import br.ce.exceptions.LocadoraException;
import br.ce.utils.DataUtils;

public class LocacaoServiceTest {
	
	private LocacaoService service;
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void before() {
		service = new LocacaoService();
	}
	
	@After
	public void after() {
		
	}
	
	@BeforeClass
	public static void beforeClass() {
	}
	
	@AfterClass
	public static void afterClass() {
		
	}
	
	@Test
	public void deveAlugarFilmeComSucesso() throws Exception {
		//arrange
		Usuario usuario = new Usuario("Samla");
		List<Filme> filmes = Arrays.asList(new Filme("Pride and Prejudice", 1, 10.0));
		
		//act
		Locacao locacao = service.alugarFilme(usuario, filmes);
			
		//assert
		error.checkThat(locacao.getValor(), is(equalTo(10.0)));
		error.checkThat(locacao.getValor(), not(12.0));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		
		Date dataAtual = new Date();
		if(DataUtils.verificarDiaSemana(dataAtual, Calendar.SATURDAY)) {
			error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(2)), is(true));
		} else {
			error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));
		}
	}
	
	@Test(expected=FilmeSemEstoqueException.class)
	public void naoDeveAlugarFilmeSemEstoque() throws Exception {
		//arrange
		Usuario usuario = new Usuario("Samla");
		List<Filme> filmes = Arrays.asList(new Filme("Pride and Prejudice", 0, 10.0));
		
		//act
		Locacao locacao = service.alugarFilme(usuario, filmes);
	}
	
	@Test 
	public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException {
		//arrange
		LocacaoService service = new LocacaoService();
		List<Filme> filmes = Arrays.asList(new Filme("Pride and Prejudice", 1, 10.0));
		
		//act
		try {
			service.alugarFilme(null, filmes);
			Assert.fail();
		} catch (LocadoraException e) {
			Assert.assertThat(e.getMessage(), is("Usu�rio Vazio!"));
		}
	}
	
	@Test 
	public void naoDeveAlugarFilmeSemFilme() throws LocadoraException, FilmeSemEstoqueException {
		
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme Vazio!");
		
		//arrange
		Usuario usuario = new Usuario("Samla");
		
		//act
		service.alugarFilme(usuario, null);
		
	}
	
	
	@Test
	public void deveDevolverNaSegundaAoAlugarNoSabado() throws FilmeSemEstoqueException, LocadoraException {
		Date dataAtual = new Date();
		Assume.assumeTrue(DataUtils.verificarDiaSemana(dataAtual, Calendar.SATURDAY));
				
		//arrange
		Usuario usuario = new Usuario("Samla");
		List<Filme> filmes = Arrays.asList(new Filme("Pride and Prejudice", 1, 10.0));
		
		//act
		Locacao locacao = service.alugarFilme(usuario, filmes);
		
		//assert
		boolean ehSegunda = DataUtils.verificarDiaSemana(locacao.getDataRetorno(), Calendar.MONDAY);
		assertTrue(ehSegunda);		
	}
}