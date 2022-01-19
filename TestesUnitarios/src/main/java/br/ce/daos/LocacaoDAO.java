package br.ce.daos;

import java.util.List;

import br.ce.entidades.Locacao;

public interface LocacaoDAO {
	public void salvar(Locacao locacao);

	public List<Locacao> obterLocacoesPendentes();
}
