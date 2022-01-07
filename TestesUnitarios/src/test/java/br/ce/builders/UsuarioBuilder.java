package br.ce.builders;

import br.ce.entidades.Usuario;

public class UsuarioBuilder {
	private Usuario usuario;
	
	//Método privado para que apenas o próprio Builder possa se instanciar
	private UsuarioBuilder() {
		
	}
	
	//Estático para que possa ser chamado externamente sem necessidade de instanciar o builder
	public static UsuarioBuilder getUsuario() {
		UsuarioBuilder builder = new UsuarioBuilder();
		builder.usuario = new Usuario();
		builder.usuario.setNome("Sam");
		return builder;
	}
	
	//Chaining method
	public Usuario agora() {
		return usuario;
	}
}
