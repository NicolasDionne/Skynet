package tests.ai.apprentissage.nonsupervise.competitionInter;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import ai.apprentissage.nonsupervise.competitionInter.CompetitionInterLiens;
import ai.coeur.Lien;
import ai.coeur.Reseau;
import modele.reseau.GenerateurReseauCIL;

public class CompetitionInterLiensTest {

	ArrayList<Reseau> listeReseaux;
	Reseau r1, r2, r3, r4;
	CompetitionInterLiens competitionInterLiens;

	@Before
	public void setUp() throws Exception {
		GenerateurReseauCIL generateurReseauCIL = new GenerateurReseauCIL();
		generateurReseauCIL.genererReseauCIL(4, 8, 2, 3, 5, -100, 100);
		listeReseaux = generateurReseauCIL.getReseauxCIR();
		r1 = listeReseaux.get(0);
		r2 = listeReseaux.get(1);
		r3 = listeReseaux.get(2);
		r4 = listeReseaux.get(3);

		r1.setScore(87);
		r2.setScore(7);
		r3.setScore(9001);
		r4.setScore(8);

		competitionInterLiens = new CompetitionInterLiens(listeReseaux, 0);
	}

	@Test
	public void testFaireUneIterationApprentissage() {
		competitionInterLiens.faireUneIterationApprentissage();
		listeReseaux = competitionInterLiens.getListeReseauxEnCompetitions();
		for (int i = 0; i < listeReseaux.size() - 1; i++) {
			ArrayList<Lien> listeLiensReseauI = listeReseaux.get(i).getListeLiens();
			for (int j = i + 1; j < listeReseaux.size(); j++) {
				ArrayList<Lien> listeLiensReseauJ = listeReseaux.get(j).getListeLiens();
				for (Lien lienReseauI : listeLiensReseauI) {
					for (Lien lienReseauJ : listeLiensReseauJ) {
						if (lienReseauI.getNom().equals(lienReseauJ.getNom())) {
							assertFalse((lienReseauI.getImportance().getValImportance()) == (lienReseauJ.getImportance()
									.getValImportance()));
							assertTrue(Math.abs((lienReseauI.getImportance().getValImportance())
									- (lienReseauJ.getImportance().getValImportance())) <= (competitionInterLiens
											.getValModification() * 2));
							break;
						}
					}
				}
			}
		}

	}

	@Test
	public void testGetListeReseauxEnCompetitions() {
		assertEquals(listeReseaux, competitionInterLiens.getListeReseauxEnCompetitions());
	}

}
