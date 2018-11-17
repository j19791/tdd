package br.com.caelum.leilao.servico;

// junit.framework não deverá ser importado. è o mais antigo
import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;

public class AvaliadorTest { // convencao: o nome da classe que estamos testando (no caso, Avaliador) mais o
								// sufixo Test

	// public static void main(String[] args) {
	@Test // JUnit: um método de teste deve sempre ser público, de instância (isto é, não
			// pode ser static) e não receber nenhum parâmetro;
	public void deveEntenderLancesEmOrdemCrescente() {// os nomes dos testes já nos dessem uma boa noção do que estamos
														// testando em cada método

		// cenário: manual
		Usuario joao = new Usuario("Joao");
		Usuario jose = new Usuario("José");
		Usuario maria = new Usuario("Maria");

		Leilao leilao = new Leilao("Playstation 3 Novo");

		// ação: ja automatizado
		leilao.propoe(new Lance(joao, 200.0));
		leilao.propoe(new Lance(jose, 300.0));
		leilao.propoe(new Lance(maria, 400.0));

		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);

		// validação da saída: o programador ainda precisa ver o que o programa imprimiu
		// na tela e ver se o resultado bate com o esperado.
		System.out.println(leiloeiro.getMaiorLance()); // imprime 400.0
		System.out.println(leiloeiro.getMenorLance());// devera imprimir 250.0

		// automatiazando a validação comparando a saida com um valor esperado
		double maiorEsperado = 400;
		double menorEsperado = 200;

		System.out.println(maiorEsperado == leiloeiro.getMaiorLance());
		System.out.println(menorEsperado == leiloeiro.getMenorLance());

		// JUnit deverá ser responsável por validar a saída.
		// Assert.assertEquals: quando queremos que o resultado gerado seja igual a
		// saída esperada.
		Assert.assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.0001);
		Assert.assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.0001);
		// double tem problemas de arredondamento. 0.00001 : a diferença entre o
		// esperado e o calculado pode ser de até 0.00001, que o JUnit entenderá como
		// erro normal de arredondamento.

		Assert.assertEquals(300, leiloeiro.valorMedioLances(leilao), 0.0001);

	}

}
