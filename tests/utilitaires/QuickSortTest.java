package tests.utilitaires;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import ai.apprentissage.nonsupervise.competitionInter.CompetitionInterReseaux;
import ai.coeur.Reseau;
import modele.reseau.GenerateurReseauCIR;
import utilitaires.QuickSort;

public class QuickSortTest {
	Reseau<CompetitionInterReseaux> r1, r2, r3, r4;

	@Before
	public void setUp() throws Exception {
		GenerateurReseauCIR g = new GenerateurReseauCIR();
		g.genererReseauCIR(4, 1, 1, 1, 2, 1, 2);
		r1 = g.getReseauxCIR().get(0);
		r2 = g.getReseauxCIR().get(1);
		r3 = g.getReseauxCIR().get(2);
		r4 = g.getReseauxCIR().get(3);
	}

	@Test
	public void testSort() {
		r1.setScore(87);
		r2.setScore(7);
		r3.setScore(9001);
		r4.setScore(8);

		ArrayList<Reseau<CompetitionInterReseaux>> listeReseauxCIR = new ArrayList<>();
		listeReseauxCIR.add(r1);
		listeReseauxCIR.add(r2);
		listeReseauxCIR.add(r3);
		listeReseauxCIR.add(r4);

		QuickSort.sort(listeReseauxCIR);

		assertEquals(r3, listeReseauxCIR.get(0));
		assertEquals(r1, listeReseauxCIR.get(1));
		assertEquals(r4, listeReseauxCIR.get(2));
		assertEquals(r2, listeReseauxCIR.get(3));
	}

}
