package br.com.caelum.leilao.servico;

import static br.com.caelum.matcher.LeilaoMatcher.temUmLance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;

public class LeilaoTest {
	@Test
	public void deveReceberUmLance() {
		Leilao leilao = new Leilao("Macbook Pro 15");
		assertEquals(0, leilao.getLances().size());

		leilao.propoe(new Lance(new Usuario("Steve Jobs"), 2000));

		assertEquals(1, leilao.getLances().size());
		assertEquals(2000, leilao.getLances().get(0).getValor(), 0.00001);
	}

	@Test
	public void deveReceberVariosLances() {
		Leilao leilao = new Leilao("Macbook Pro 15");
		leilao.propoe(new Lance(new Usuario("Steve Jobs"), 2000));
		leilao.propoe(new Lance(new Usuario("Steve Wozniak"), 3000));

		assertEquals(2, leilao.getLances().size());
		assertEquals(2000, leilao.getLances().get(0).getValor(), 0.00001);
		assertEquals(3000, leilao.getLances().get(1).getValor(), 0.00001);
	}

	@Test
	public void naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario() {
		Leilao leilao = new Leilao("Macbook Pro 15");
		Usuario steveJobs = new Usuario("Steve Jobs");
		Usuario bill = new Usuario("bill");

		leilao.propoe(new Lance(steveJobs, 2000));

		leilao.propoe(new Lance(bill, 3000));
		// leilao.propoe(new Lance(steveJobs, 3000));
		// leilao.propoe(new Lance(bill, 3000));

		assertEquals(2, leilao.getLances().size());
		assertEquals(2000, leilao.getLances().get(0).getValor(), 0.00001);

		// encontrar o último lance dado por este usuário e criar um novo lance com o
		// dobro do lance anterior.
		leilao.propoe(leilao.dobralance(steveJobs));

		leilao.propoe(leilao.dobralance(bill));

		assertEquals(4, leilao.getLances().size());
		assertEquals(4000, leilao.getLances().get(2).getValor(), 0.00001);
		assertEquals(6000, leilao.getLances().get(3).getValor(), 0.00001);

		leilao.propoe(leilao.dobralance(steveJobs));

		leilao.propoe(leilao.dobralance(bill));

		assertEquals(6, leilao.getLances().size());
		assertEquals(8000, leilao.getLances().get(4).getValor(), 0.00001);
		assertEquals(12000, leilao.getLances().get(5).getValor(), 0.00001);

		leilao.propoe(leilao.dobralance(steveJobs));
		leilao.propoe(leilao.dobralance(bill));
		assertEquals(8, leilao.getLances().size());
		assertEquals(16000, leilao.getLances().get(6).getValor(), 0.00001);
		assertEquals(24000, leilao.getLances().get(7).getValor(), 0.00001);

	}

	@Test
	public void naoDeveAceitarMaisDoQue5LancesDeUmMesmoUsuario() {
		Leilao leilao = new Leilao("Macbook Pro 15");
		Usuario steveJobs = new Usuario("Steve Jobs");
		Usuario billGates = new Usuario("Bill Gates");

		leilao.propoe(new Lance(steveJobs, 2000));
		leilao.propoe(new Lance(billGates, 3000));
		leilao.propoe(new Lance(steveJobs, 4000));
		leilao.propoe(new Lance(billGates, 5000));
		leilao.propoe(new Lance(steveJobs, 6000));
		leilao.propoe(new Lance(billGates, 7000));
		leilao.propoe(new Lance(steveJobs, 8000));
		leilao.propoe(new Lance(billGates, 9000));
		leilao.propoe(new Lance(steveJobs, 10000));
		leilao.propoe(new Lance(billGates, 11000));

		// deve ser ignorado
		leilao.propoe(new Lance(steveJobs, 12000));

		assertEquals(10, leilao.getLances().size());
		int ultimo = leilao.getLances().size() - 1;
		Lance ultimoLance = leilao.getLances().get(ultimo);
		assertEquals(11000.0, ultimoLance.getValor(), 0.00001);
	}

	@Test(expected = IllegalArgumentException.class)
	public void deveLancarExcecaoQuandoLanceMenorOuIgualaZero() {
		Leilao leilao = new Leilao("Macbook Pro 15");
		Usuario steveJobs = new Usuario("Steve Jobs");

		leilao.propoe(new Lance(steveJobs, 0));
	}

	@Test
	public void deveGarantirQueExisteUmLanceDentro() {
		Leilao leilao = new Leilao("Macbook Pro 15");

		Lance lance = new Lance(new Usuario("Steve Jobs"), 2000);
		leilao.propoe(lance);

		assertThat(leilao, temUmLance(lance));
	}

}
