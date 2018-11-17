package br.com.caelum.leilao.servico;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;

public class Avaliador {

	private double maiorDeTodos = Double.NEGATIVE_INFINITY; // inicializada com o menor valor que cabe em um double
	private double menorDeTodos = Double.POSITIVE_INFINITY;

	public void avalia(Leilao leilao) {

		for (Lance lance : leilao.getLances()) {
			if (lance.getValor() > maiorDeTodos) {
				maiorDeTodos = lance.getValor();
				// } else if (lance.getValor() < menorDeTodos) { simulando um bug
			}
			if (lance.getValor() < menorDeTodos) { // bug corrigido
				menorDeTodos = lance.getValor();
			}
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
}