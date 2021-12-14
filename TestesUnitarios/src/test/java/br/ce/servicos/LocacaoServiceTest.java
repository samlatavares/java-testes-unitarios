package br.ce.servicos;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

import java.util.Date;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import br.ce.entidades.Filme;
import br.ce.entidades.Locacao;
import br.ce.entidades.Usuario;
import br.ce.utils.DataUtils;

public class LocacaoServiceTest {
	
	@Test
	public void salvarLocacaoTeste() {
		//arrange
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Samla");
		Filme filme = new Filme("Pride and Prejudice", 1, 10.0);
		
		//act
		Locacao locacao = service.alugarFilme(usuario, filme);
		
		//assert
		Assert.assertThat(locacao.getValor(), is(equalTo(10.0)));
		Assert.assertThat(locacao.getValor(), not(12.0));
		Assert.assertThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		Assert.assertThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));
	}
}
