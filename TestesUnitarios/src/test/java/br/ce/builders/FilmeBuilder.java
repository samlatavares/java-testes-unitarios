package br.ce.builders;

import br.ce.entidades.Filme;

public class FilmeBuilder {
	
	private Filme filme;
	
	private FilmeBuilder() {
		
	}
	
	public static FilmeBuilder getFilmeBuilder() {
		FilmeBuilder builder = new FilmeBuilder();
		builder.filme = new Filme();
		builder.filme.setEstoque(2);
		builder.filme.setNome("Pride and Prejudice");
		builder.filme.setPrecoLocacao(10.0);
		return builder;
	}
	
	public Filme getFilme() {
		return filme;
	}
}
