package br.com.caelum.leilao.servico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;

public class Avaliador {

	private double maiorDeTodos = Double.NEGATIVE_INFINITY; // inicializada com o menor valor que cabe em um double
	private double menorDeTodos = Double.POSITIVE_INFINITY;

	private List<Lance> maiores;

	public void avalia(Leilao leilao) {

		if (leilao.getLances().size() == 0) // teste para usar o AssetFail
			throw new RuntimeException("Não é possível avaliar um leilão sem lances");

		for (Lance lance : leilao.getLances()) {
			if (lance.getValor() > maiorDeTodos) {
				maiorDeTodos = lance.getValor();
				// } else if (lance.getValor() < menorDeTodos) { simulando um bug
			}
			if (lance.getValor() < menorDeTodos) { // bug corrigido
				menorDeTodos = lance.getValor();
			}
		}

		if (leilao.getLances().size() > 1) {
			pegaOsMaioresNo(leilao);
		}

	}

	public double getMaiorLance() {
		return maiorDeTodos;
	}

	public double getMenorLance() {
		return menorDeTodos;
	}

	public double valorMedioLances(Leilao leilao) {
		double soma = 0.00;

		for (Lance lance : leilao.getLances()) {
			soma = soma + lance.getValor();
		}

		return soma / leilao.getLances().size();
	}

	private void pegaOsMaioresNo(Leilao leilao) {
		maiores = new ArrayList<Lance>(leilao.getLances());
		Collections.sort(maiores, new Comparator<Lance>() {
			public int compare(Lance o1, Lance o2) {
				if (o1.getValor() < o2.getValor())
					return 1;
				if (o1.getValor() > o2.getValor())
					return -1;
				return 0;
			}
		});
		maiores = maiores.subList(0, 3);
	}

	public List<Lance> getTresMaiores() {
		return this.maiores;
	}
}