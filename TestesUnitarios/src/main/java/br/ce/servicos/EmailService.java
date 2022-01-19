package br.ce.servicos;

import br.ce.entidades.Usuario;

public interface EmailService {
	
	public void notificarAtraso(Usuario usuario);

}
