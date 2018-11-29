package br.com.caelum.leilao.servico;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
//O método assertEquals() é estático, e portanto, podemos importá-lo de maneira estática
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
// junit.framework não deverá ser importado. è o mais antigo
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

	@Before // pedir para o JUnit rodar esse método automaticamente, antes de executar cada
	// teste da classe
	public void criaAvaliador() {
		this.leiloeiro = new Avaliador();
		this.joao = new Usuario("Joao");
		this.jose = new Usuario("José");
		this.maria = new Usuario("Maria");
	}

	// public static void main(String[] args) {
	@Test // JUnit: um método de teste deve sempre ser público, de instância (isto é, não
			// pode ser static) e não receber nenhum parâmetro;
	public void deveEntenderLancesEmOrdemCrescente() {// os nomes dos testes já nos dessem uma boa noção do que estamos
														// testando em cada método

		// cenário: manual

		/*
		 * Leilao leilao = new Leilao("Playstation 3 Novo");
		 * 
		 * // ação: ja automatizado leilao.propoe(new Lance(joao, 200.0));
		 * leilao.propoe(new Lance(jose, 300.0)); leilao.propoe(new Lance(maria,
		 * 400.0));
		 */

		// refatorando o código aberto - //classe que usa o padrão de projeto builder
		Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo").lance(joao, 100.0).lance(maria, 200.0)
				.lance(joao, 300.0).lance(maria, 400.0).constroi();

		// criaAvaliador(); utilizando @Before
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
		assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.0001);
		assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.0001);
		// double tem problemas de arredondamento. 0.00001 : a diferença entre o
		// esperado e o calculado pode ser de até 0.00001, que o JUnit entenderá como
		// erro normal de arredondamento.

		Assert.assertEquals(300, leiloeiro.valorMedioLances(leilao), 0.0001);

	}

	@Test // escrever testes para as classes de equivalência
	public void deveEntenderLeilaoComApenasUmLance() {
		// Usuario joao = new Usuario("Joao"); refatorado em criaAvaliador

		// Leilao leilao = new Leilao("Playstation 3 Novo");

		// ação: ja automatizado
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

		// Usuario joao = new Usuario("João");
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
	public void deveEntenderLancesEmOrdemAleatória() {
		// testando em cada método

		// cenário: manual - refatorado
		/*
		 * Usuario joao = new Usuario("Joao"); Usuario jose = new Usuario("José");
		 * Usuario maria = new Usuario("Maria");
		 */

		Leilao leilao = new Leilao("Playstation 3 Novo");

		// ação: ja automatizado
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
		// testando em cada método

		// cenário: manual - refatorado
		/*
		 * Usuario joao = new Usuario("Joao"); Usuario jose = new Usuario("José");
		 * Usuario maria = new Usuario("Maria");
		 */

		Leilao leilao = new Leilao("Playstation 3 Novo");

		// ação: ja automatizado
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

	@Test(expected = RuntimeException.class) // fazermos o teste falhar caso a exceção não seja lançada.
	public void naoDeveAvaliarLeiloesSemNenhumLanceDado() {
		Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo").constroi();

		leiloeiro.avalia(leilao); // deverá gerar uma exceção (RuntimeException esperada) para o teste passar
	}

	@After // usado quando nossos testes consomem recursos que precisam ser finalizados
	public void finaliza() {
		System.out.println("fim");
	}

	@BeforeClass // temos algum recurso que precisa ser inicializado apenas uma vez e que pode
					// ser consumido por todos os métodos de teste sem a necessidade de ser
					// reinicializado.
	public static void testandoBeforeClass() {
		System.out.println("before class");
	}

	@AfterClass
	public static void testandoAfterClass() {
		System.out.println("after class");
	}
}
