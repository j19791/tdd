package br.com.caelum.leilao.servico;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;

//classe que usa o padrão de projeto builder
public class CriadorDeLeilao {
	private Leilao leilao;

	public CriadorDeLeilao() {
	}

	public CriadorDeLeilao para(String descricao) {
		this.leilao = new Leilao(descricao);
		return this; // para poder encadear os metodos
	}

	public CriadorDeLeilao lance(Usuario usuario, double valor) {
		leilao.propoe(new Lance(usuario, valor));
		return this;// para poder encadear os metodos
	}

	public Leilao constroi() {
		return leilao;
	}
}
