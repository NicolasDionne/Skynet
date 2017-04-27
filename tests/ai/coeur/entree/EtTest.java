package tests.ai.coeur.entree;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import ai.coeur.Neurone;
import ai.coeur.entree.Et;

public class EtTest {
	Et e1;
	Neurone nEntree1, nEntree2, nSortie;

	@Before
	public void setUp() throws Exception {
		e1 = new Et();
		nEntree1 = new Neurone();
		nEntree2 = new Neurone();
		nSortie = new Neurone();
		nEntree1.setSortie(4);
		nEntree2.setSortie(8);
		nSortie.ajouterLienEntree(nEntree1);
		nSortie.ajouterLienEntree(nEntree2);
		nSortie.setFonctionEntree(e1);
	}

	@Test
	public void testGetSortie() {
		assertTrue(e1.getSortie(nSortie.getLiensEntree()) == 1);
		nEntree2.setSortie(0.4);
		assertTrue(e1.getSortie(nSortie.getLiensEntree()) == 0);
	}

}
