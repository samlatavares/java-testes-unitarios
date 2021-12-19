package br.ce.servicos;

import static br.ce.utils.DataUtils.adicionarDias;

import java.util.Date;
import java.util.List;

import br.ce.entidades.Filme;
import br.ce.entidades.Locacao;
import br.ce.entidades.Usuario;
import br.ce.exceptions.FilmeSemEstoqueException;
import br.ce.exceptions.LocadoraException;

public class LocacaoService {
	
	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException {
			
		if(usuario == null) {
			throw new LocadoraException("Usu·rio Vazio!");
		}
		
		if(filmes == null || filmes.isEmpty()) {
			throw new LocadoraException("Filme Vazio!");
		}
		
		for(Filme filme: filmes) {
			if(filme.getEstoque() == 0) {
				throw new FilmeSemEstoqueException();
			}
		}

		
		Locacao locacao = new Locacao();
		locacao.setFilme(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		
		Double valorTotal = 0d;		
		for(Filme filme: filmes) {
			valorTotal += filme.getPrecoLocacao();
		}

		locacao.setValor(valorTotal);
		
		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar m√©todo para salvar
		
		return locacao;
	}
	
}