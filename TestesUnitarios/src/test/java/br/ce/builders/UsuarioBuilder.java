package br.ce.builders;

import br.ce.entidades.Usuario;

public class UsuarioBuilder {
	private Usuario usuario;
	
	//M�todo privado para que apenas o pr�prio Builder possa se instanciar
	private UsuarioBuilder() {
		
	}
	
	//Est�tico para que possa ser chamado externamente sem necessidade de instanciar o builder
	public static UsuarioBuilder getUsuarioBuilder() {
		UsuarioBuilder builder = new UsuarioBuilder();
		builder.usuario = new Usuario();
		builder.usuario.setNome("Sam");
		return builder;
	}
	
	public UsuarioBuilder comNome(String nome) {
		usuario.setNome(nome);
		return this;
	}
	
	//Chaining method
	public Usuario getUsuario() {
		return usuario;
	}
}
