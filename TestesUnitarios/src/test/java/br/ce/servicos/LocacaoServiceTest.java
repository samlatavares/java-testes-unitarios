package br.ce.servicos;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import java.util.Date;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.entidades.Filme;
import br.ce.entidades.Locacao;
import br.ce.entidades.Usuario;
import br.ce.exceptions.FilmeSemEstoqueException;
import br.ce.utils.DataUtils;

public class LocacaoServiceTest {
	
	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void salvarLocacaoTeste() throws Exception {
		//arrange
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Samla");
		Filme filme = new Filme("Pride and Prejudice", 1, 10.0);
		
		//act
		Locacao locacao = service.alugarFilme(usuario, filme);
			
		//assert
		error.checkThat(locacao.getValor(), is(equalTo(10.0)));
		error.checkThat(locacao.getValor(), not(12.0));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));
	}
	
	@Test(expected=FilmeSemEstoqueException.class)
	public void testLocacao_filmeSemEstoque() throws Exception {
		//arrange
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Samla");
		Filme filme = new Filme("Pride and Prejudice", 0, 10.0);
		
		//act
		Locacao locacao = service.alugarFilme(usuario, filme);
	}
	
	@Test 
	public void testLocacao_usuarioVazio() {
		
	}
	
}
