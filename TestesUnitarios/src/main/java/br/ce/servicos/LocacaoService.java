package br.ce.servicos;

import static br.ce.utils.DataUtils.adicionarDias;

import java.util.Date;

import br.ce.entidades.Filme;
import br.ce.entidades.Locacao;
import br.ce.entidades.Usuario;
import br.ce.exceptions.FilmeSemEstoqueException;
import br.ce.exceptions.LocadoraException;

public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, Filme filme) throws Exception {
		
		if(filme.getEstoque() == 0) {
			throw new FilmeSemEstoqueException();
		}
		
		if(usuario == null) {
			throw new LocadoraException("Usu·rio Vazio!");
		}
		
		if(filme == null) {
			throw new LocadoraException("Filme Vazio!");
		}
		
		Locacao locacao = new Locacao();
		locacao.setFilme(filme);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(filme.getPrecoLocacao());

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar m√©todo para salvar
		
		return locacao;
	}
	
}