package br.ce.servicos;

import java.util.Date;

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
		Assert.assertTrue(locacao.getValor() == 10.0);
		Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
		Assert.assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));
	}
}
