package br.ce.servicos;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mockito;

import br.ce.daos.LocacaoDAO;
import br.ce.daos.LocacaoDAOFake;
import br.ce.entidades.Filme;
import br.ce.entidades.Locacao;
import br.ce.entidades.Usuario;
import br.ce.exceptions.FilmeSemEstoqueException;
import br.ce.exceptions.LocadoraException;

@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {

	private LocacaoService service;
	private LocacaoDAO dao;
	private SPCService spc;
	
	@Parameter
	public List<Filme> filmes;
	
	@Parameter(value=1)
	public Double valorLocacao;
	
	@Parameter(value=2)
	public String anotacao;
	
	@Before
	public void before() {
		service = new LocacaoService();
		
		dao = new LocacaoDAOFake();
		service.setLocacaoDAO(dao);
		
		spc = Mockito.mock(SPCService.class);
		service.setSPCService(spc);
	}
	
	private static Filme filme1 = new Filme("Pride and Prejudice", 4, 10.0);
	private static Filme filme2 = new Filme("Kiki's Delivery Service", 3, 10.0);
	private static Filme filme3 = new Filme("A Quiet Place", 2, 10.0);
	private static Filme filme4 = new Filme("Get Out", 2, 10.0);
	private static Filme filme5 = new Filme("The Father", 3, 10.0);
	private static Filme filme6 = new Filme("He even has your eyes", 1, 10.0);
	private static Filme filme7 = new Filme("The Suicide Squad 2", 2, 10.0);

	@Parameters(name="{2}")
	public static Collection<Object[]> getParametros() {
		return Arrays.asList(new Object[][] {
			{Arrays.asList(filme1, filme2, filme3), 27.5, "3 Filmes: 25%"},
			{Arrays.asList(filme1, filme2, filme3, filme4), 32.5, "4 Filmes: 50%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5), 35.0, "5 Filmes: 75%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 35.0, "6 Filmes: 100%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6, filme7), 45.0, "7 Filmes: Sem Desconto"}
		});
	}
	
	@Test
	public void deveCalcularValorLocacaoConsiderandoDescontos() throws FilmeSemEstoqueException, LocadoraException {
		//arrange
		Usuario usuario = new Usuario("Samla");
		
		//act
		Locacao locacao = service.alugarFilme(usuario, filmes);
		
		//assert
		assertThat(locacao.getValor(), is(valorLocacao));
	}
	
}
