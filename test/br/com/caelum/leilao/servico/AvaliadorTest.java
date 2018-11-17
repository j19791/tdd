package br.com.caelum.leilao.servico;

// junit.framework n�o dever� ser importado. � o mais antigo
import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;

public class AvaliadorTest { // convencao: o nome da classe que estamos testando (no caso, Avaliador) mais o
								// sufixo Test

	// public static void main(String[] args) {
	@Test // JUnit: um m�todo de teste deve sempre ser p�blico, de inst�ncia (isto �, n�o
			// pode ser static) e n�o receber nenhum par�metro;
	public void deveEntenderLancesEmOrdemCrescente() {// os nomes dos testes j� nos dessem uma boa no��o do que estamos
														// testando em cada m�todo

		// cen�rio: manual
		Usuario joao = new Usuario("Joao");
		Usuario jose = new Usuario("Jos�");
		Usuario maria = new Usuario("Maria");

		Leilao leilao = new Leilao("Playstation 3 Novo");

		// a��o: ja automatizado
		leilao.propoe(new Lance(joao, 200.0));
		leilao.propoe(new Lance(jose, 300.0));
		leilao.propoe(new Lance(maria, 400.0));

		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);

		// valida��o da sa�da: o programador ainda precisa ver o que o programa imprimiu
		// na tela e ver se o resultado bate com o esperado.
		System.out.println(leiloeiro.getMaiorLance()); // imprime 400.0
		System.out.println(leiloeiro.getMenorLance());// devera imprimir 250.0

		// automatiazando a valida��o comparando a saida com um valor esperado
		double maiorEsperado = 400;
		double menorEsperado = 200;

		System.out.println(maiorEsperado == leiloeiro.getMaiorLance());
		System.out.println(menorEsperado == leiloeiro.getMenorLance());

		// JUnit dever� ser respons�vel por validar a sa�da.
		// Assert.assertEquals: quando queremos que o resultado gerado seja igual a
		// sa�da esperada.
		Assert.assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.0001);
		Assert.assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.0001);
		// double tem problemas de arredondamento. 0.00001 : a diferen�a entre o
		// esperado e o calculado pode ser de at� 0.00001, que o JUnit entender� como
		// erro normal de arredondamento.

		Assert.assertEquals(300, leiloeiro.valorMedioLances(leilao), 0.0001);

	}

}
