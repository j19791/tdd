package br.com.caelum.leilao.servico;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
//O m�todo assertEquals() � est�tico, e portanto, podemos import�-lo de maneira est�tica
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
// junit.framework n�o dever� ser importado. � o mais antigo
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;

public class AvaliadorTest { // convencao: o nome da classe que estamos testando (no caso, Avaliador) mais o
								// sufixo Test

	private Avaliador leiloeiro;
	private Usuario joao;
	private Usuario jose;
	private Usuario maria;

	@Before // pedir para o JUnit rodar esse m�todo automaticamente, antes de executar cada
	// teste da classe
	public void criaAvaliador() {
		this.leiloeiro = new Avaliador();
		this.joao = new Usuario("Joao");
		this.jose = new Usuario("Jos�");
		this.maria = new Usuario("Maria");
	}

	// public static void main(String[] args) {
	@Test // JUnit: um m�todo de teste deve sempre ser p�blico, de inst�ncia (isto �, n�o
			// pode ser static) e n�o receber nenhum par�metro;
	public void deveEntenderLancesEmOrdemCrescente() {// os nomes dos testes j� nos dessem uma boa no��o do que estamos
														// testando em cada m�todo

		// cen�rio: manual

		/*
		 * Leilao leilao = new Leilao("Playstation 3 Novo");
		 * 
		 * // a��o: ja automatizado leilao.propoe(new Lance(joao, 200.0));
		 * leilao.propoe(new Lance(jose, 300.0)); leilao.propoe(new Lance(maria,
		 * 400.0));
		 */

		// refatorando o c�digo aberto - //classe que usa o padr�o de projeto builder
		Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo").lance(joao, 100.0).lance(maria, 200.0)
				.lance(joao, 300.0).lance(maria, 400.0).constroi();

		// criaAvaliador(); utilizando @Before
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
		assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.0001);
		assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.0001);
		// double tem problemas de arredondamento. 0.00001 : a diferen�a entre o
		// esperado e o calculado pode ser de at� 0.00001, que o JUnit entender� como
		// erro normal de arredondamento.

		Assert.assertEquals(300, leiloeiro.valorMedioLances(leilao), 0.0001);

	}

	@Test // escrever testes para as classes de equival�ncia
	public void deveEntenderLeilaoComApenasUmLance() {
		// Usuario joao = new Usuario("Joao"); refatorado em criaAvaliador

		// Leilao leilao = new Leilao("Playstation 3 Novo");

		// a��o: ja automatizado
		// leilao.propoe(new Lance(joao, 200.0));

		Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo").lance(joao, 200.00).constroi();

		// criaAvaliador(); utilizando @Before
		leiloeiro.avalia(leilao);

		double maiorEsperado = 200;
		double menorEsperado = 200;

		assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.0001);
		assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.0001);

	}

	@Test
	public void deveEncontrarOsTresMaioresLances() {

		// Usuario joao = new Usuario("Jo�o");
		// Usuario maria = new Usuario("Maria");
		// refatorado em criaAvaliador

		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.propoe(new Lance(joao, 100.0));
		leilao.propoe(new Lance(maria, 200.0));
		leilao.propoe(new Lance(joao, 300.0));
		leilao.propoe(new Lance(maria, 400.0));

		// criaAvaliador(); utilizando @Before
		leiloeiro.avalia(leilao);

		List<Lance> maiores = leiloeiro.getTresMaiores();

		/*
		 * assertEquals(3, maiores.size());
		 * 
		 * assertEquals(200, maiores.get(2).getValor(), 0.0001); assertEquals(300,
		 * maiores.get(1).getValor(), 0.0001); assertEquals(400,
		 * maiores.get(0).getValor(), 0.0001);
		 */

		// utilizando o hamcrest
		assertThat(maiores, hasItems(new Lance(maria, 400), new Lance(joao, 300), new Lance(maria, 200)));

	}

	@Test
	public void deveEntenderLancesEmOrdemAleat�ria() {
		// testando em cada m�todo

		// cen�rio: manual - refatorado
		/*
		 * Usuario joao = new Usuario("Joao"); Usuario jose = new Usuario("Jos�");
		 * Usuario maria = new Usuario("Maria");
		 */

		Leilao leilao = new Leilao("Playstation 3 Novo");

		// a��o: ja automatizado
		leilao.propoe(new Lance(joao, 450.0));
		leilao.propoe(new Lance(jose, 120.0));
		leilao.propoe(new Lance(maria, 200.0));
		leilao.propoe(new Lance(maria, 700.0));
		leilao.propoe(new Lance(jose, 630.0));
		leilao.propoe(new Lance(joao, 200.0));

		// criaAvaliador(); utilizando @Before
		leiloeiro.avalia(leilao);

		double maiorEsperado = 700;
		double menorEsperado = 120;

		assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.0001);
		assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.0001);

	}

	@Test
	public void deveEntenderLancesEmOrdemDecrescente() {
		// testando em cada m�todo

		// cen�rio: manual - refatorado
		/*
		 * Usuario joao = new Usuario("Joao"); Usuario jose = new Usuario("Jos�");
		 * Usuario maria = new Usuario("Maria");
		 */

		Leilao leilao = new Leilao("Playstation 3 Novo");

		// a��o: ja automatizado
		leilao.propoe(new Lance(joao, 450.0));
		leilao.propoe(new Lance(maria, 200.0));

		leilao.propoe(new Lance(jose, 120.0));

		// criaAvaliador(); utilizando @Before
		leiloeiro.avalia(leilao);

		double maiorEsperado = 450;
		double menorEsperado = 120;

		// assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.0001);
		// assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.0001);

		assertThat(leiloeiro.getMenorLance(), equalTo(120.0)); // utilizando o framework hamcrest

	}

	@Test(expected = RuntimeException.class) // fazermos o teste falhar caso a exce��o n�o seja lan�ada.
	public void naoDeveAvaliarLeiloesSemNenhumLanceDado() {
		Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo").constroi();

		leiloeiro.avalia(leilao); // dever� gerar uma exce��o (RuntimeException esperada) para o teste passar
	}

	@After // usado quando nossos testes consomem recursos que precisam ser finalizados
	public void finaliza() {
		System.out.println("fim");
	}

	@BeforeClass // temos algum recurso que precisa ser inicializado apenas uma vez e que pode
					// ser consumido por todos os m�todos de teste sem a necessidade de ser
					// reinicializado.
	public static void testandoBeforeClass() {
		System.out.println("before class");
	}

	@AfterClass
	public static void testandoAfterClass() {
		System.out.println("after class");
	}
}
