package tests.ai.apprentissage.nonsupervise.competitionInterReseau;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import ai.apprentissage.nonsupervise.CompetitionInterReseaux;
import ai.apprentissage.nonsupervise.competitionInterReseau.Similarite;
import ai.coeur.Lien;
import ai.coeur.Reseau;
import modele.reseau.GenerateurReseau;

public class CompetitionInterReseauxTest {
	CompetitionInterReseaux c;
	GenerateurReseau g;
	ArrayList<Reseau<CompetitionInterReseaux>> listeReseaux;

	@Before
	public void setUp() throws Exception {
		g = new GenerateurReseau();
		g.genererReseauCIR(8, 3, 2, 3, 3, -100, 100);
		c = new CompetitionInterReseaux(g.getReseauxCIR());
		listeReseaux = c.getListeReseauxEnCompetitions();
	}

	@Test
	public void testMAJIR() {
		for (Reseau<CompetitionInterReseaux> reseau : listeReseaux) {
			reseau.randomizerImportances(-100, 100);
		}
		Reseau<CompetitionInterReseaux> r1 = listeReseaux.get(0), r2 = listeReseaux.get(1), r3 = listeReseaux.get(2),
				r4 = listeReseaux.get(3), r5 = listeReseaux.get(4), r6 = listeReseaux.get(5), r7 = listeReseaux.get(6),
				r8 = listeReseaux.get(7);

		r1.setScore(87);
		r2.setScore(7);
		r3.setScore(9001);
		r4.setScore(8);
		r5.setScore(8888);
		r6.setScore(7842);
		r7.setScore(8472);
		r8.setScore(1);

		c.faireUneIterationApprentissage();

		ArrayList<Reseau<CompetitionInterReseaux>> listeMoinsBonsReseaux = c.getListeMoinsBonsReseaux();
		LinkedList<Similarite> listeSimilarites = c.getListeSimilaritesMeilleursReseaux();

		for (Reseau<CompetitionInterReseaux> reseau : listeMoinsBonsReseaux) {
			ArrayList<Lien> listeLiensReseau = reseau.getListeLiens();
			for (Similarite similarite : listeSimilarites) {
				for (Lien lien : listeLiensReseau) {
					if (similarite.getNomLien().equals(lien.getNom())) {
						assertTrue(similarite.getValAAppliquer() == lien.getImportance().valImportance);
					}
				}
			}
		}

	}

	@Test
	public void testCompetitionInterReseaux() {
		CompetitionInterReseaux c = new CompetitionInterReseaux(g.getReseauxCIR());
		for (Reseau<CompetitionInterReseaux> reseau : c.getListeReseauxEnCompetitions()) {
			assertEquals(c, reseau.getRegleApprentissage());
		}
	}

}
