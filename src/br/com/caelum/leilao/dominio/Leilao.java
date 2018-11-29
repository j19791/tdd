package br.com.caelum.leilao.dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Leilao {

	private String descricao;
	private List<Lance> lances;

	public Leilao(String descricao) {
		this.descricao = descricao;
		this.lances = new ArrayList<Lance>();
	}

	public void propoe(Lance lance) {

		// TDD: implementado nova regra de negócio que não passou no teste
		// naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario() e
		// naoDeveAceitarMaisDoQue5LancesDeUmMesmoUsuario
		if (lances.isEmpty() || podeDarLance(lance.getUsuario())) {
			lances.add(lance);
		}

	}

	private boolean podeDarLance(Usuario usuario) {
		return !ultimoLanceDado().getUsuario().equals(usuario) && qtdDelancesDo(usuario) < 5;
	}

	private int qtdDelancesDo(Usuario usuario) {
		int total = 0;
		for (Lance l : lances) {
			if (l.getUsuario().equals(usuario))
				total++;
		}
		return total;
	}

	// codigo refatorado - uilizado Refactor > Extract Method
	private Lance ultimoLanceDado() {
		return lances.get(lances.size() - 1);
	}

	public String getDescricao() {
		return descricao;
	}

	public List<Lance> getLances() {
		return Collections.unmodifiableList(lances);
	}

	public Lance dobralance(Usuario usuario) {
		double valor = 0.00;
		for (Lance l : lances) {
			if (l.getUsuario().equals(usuario)) {
				valor = l.getValor() * 2;
			}

		}
		Lance novoLance = new Lance(usuario, valor);

		return novoLance;

	}

}
