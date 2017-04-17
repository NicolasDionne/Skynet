package tests.ai.coeur;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ai.coeur.Importance;
import ai.coeur.Lien;
import ai.coeur.Neurone;
import ai.coeur.Niveau;
import ai.coeur.entree.Et;
import ai.coeur.entree.SommeImportance;
import ai.coeur.transfers.Etape;
import ai.coeur.transfers.Rampe;

public class NeuroneTest {

	Neurone n1, n2, n3;

	@Before
	public void setUp() throws Exception {
		n1 = new Neurone();
		n2 = new Neurone();
		n3 = new Neurone();
	}

	@Test
	public void testNeurone() {
		Neurone n = new Neurone();
		assertNotNull(n);
		assertTrue(n.getFonctionEntree().getClass() == SommeImportance.class);
		assertTrue(n.getFonctionTransfers().getClass() == Etape.class);
		assertNotNull(n.getLiensEntree());
		assertTrue(n.getLiensEntree().size() == 0);
		assertNotNull(n.getLiensSortie());
		assertTrue(n.getLiensSortie().size() == 0);
	}

	@Test
	public void testNeuroneFonctionEntreeFonctionTransfers() {
		Neurone n = new Neurone(new Et(), new Rampe());
		assertNotNull(n);
		assertTrue(n.getFonctionEntree().getClass() == Et.class);
		assertTrue(n.getFonctionTransfers().getClass() == Rampe.class);
		assertNotNull(n.getLiensEntree());
		assertTrue(n.getLiensEntree().size() == 0);
		assertNotNull(n.getLiensSortie());
		assertTrue(n.getLiensSortie().size() == 0);
	}

	@Test
	public void testCalculer() {
		n2.setSortie(4);
		n3.setSortie(1);
		Lien lienAvecN2 = new Lien(n2, n1, 7), lienAvecN3 = new Lien(n3, n1, 95);
		n1.ajouterLienEntree(lienAvecN2);
		n1.ajouterLienEntree(lienAvecN3);

		n1.calculer();
		assertTrue(n1.getTotalEntrees() == 4 * 7 + 1 * 95);
		assertTrue(n1.getSortie() == 1);
	}

	@Test
	public void testSetTotalEntrees() {
		assertTrue(n1.getTotalEntrees() == 0);

		n1.setTotalEntrees(8);

		assertTrue(n1.getTotalEntrees() == 8);
	}

	@Test
	public void testGetTotalEntrees() {
		assertTrue(n1.getTotalEntrees() == 0);

		n1.setTotalEntrees(8);

		assertTrue(n1.getTotalEntrees() == 8);
	}

	@Test
	public void testGetSortie() {
		assertTrue(n1.getSortie() == 0);
		n1.setSortie(8999.351);
		assertTrue(n1.getSortie() == 8999.351);
	}

	@Test
	public void testALiensEntree() {
		assertFalse(n1.aLiensEntree());
		n1.ajouterLienEntree(n2);
		assertTrue(n1.aLiensEntree());
	}

	@Test
	public void testALiensSortie() {
		assertFalse(n1.aLiensSortie());
		n2.ajouterLienEntree(n1);
		assertTrue(n1.aLiensSortie());
	}

	@Test
	public void testALienVers() {
		n1.ajouterLienEntree(n2);
		assertTrue(n2.aLienVers(n1));
	}

	@Test
	public void testALienAPartirDe() {
		n1.ajouterLienEntree(n2);
		assertTrue(n1.aLienAPartirDe(n2));
	}

	@Test
	public void testAjouterLienEntreeLien() {
		Lien l = new Lien(n2, n1);
		n1.ajouterLienEntree(l);
		assertTrue(n1.aLienAPartirDe(n2));
	}

	@Test
	public void testAjouterLienEntreeNeurone() {
		n1.ajouterLienEntree(n2);
		assertTrue(n1.aLienAPartirDe(n2));
	}

	@Test
	public void testAjouterLienEntreeNeuroneDouble() {
		n1.ajouterLienEntree(n2, 8);
		assertTrue(n1.aLienAPartirDe(n2));
		assertTrue(n1.getLiensEntree().get(0).getImportance().getValImportance() == 8);
	}

	@Test
	public void testAjouterLienSortie() {
		Lien l = new Lien(n2, n1);
		n2.ajouterLienSortie(l);
		assertTrue(n2.getLiensSortie().get(0).getJusquANeurone() == n1);
	}

	@Test
	public void testGetLiensEntree() {
		assertNotNull(n1.getLiensEntree());
		assertTrue(n1.getLiensEntree().size() == 0);
	}

	@Test
	public void testGetLiensSortie() {
		assertNotNull(n1.getLiensSortie());
		assertTrue(n1.getLiensSortie().size() == 0);
	}

	@Test
	public void testRetirerLienEntreeLien() {
		n1.ajouterLienEntree(n2);
		Lien l = n1.getLiensEntree().get(0);
		n1.retirerLienEntree(l);
		assertFalse(n1.aLiensEntree());
	}

	@Test
	public void testRetirerLienEntreeNeurone() {
		n1.ajouterLienEntree(n2);
		n1.retirerLienEntree(n2);
		assertFalse(n1.aLienAPartirDe(n2));
	}

	@Test
	public void testRetirerLienSortieLien() {
		n1.ajouterLienEntree(n2);
		Lien l = n1.getLiensEntree().get(0);
		n2.retirerLienSortie(l);
		assertFalse(n2.aLiensSortie());
	}

	@Test
	public void testRetirerLienSortieNeurone() {
		n1.ajouterLienEntree(n2);
	}

	@Test
	public void testRetirerTousLiensEntree() {
		n1.ajouterLienEntree(n2);
		n1.ajouterLienEntree(n3);
		assertTrue(n1.getLiensEntree().size() == 2);

		n1.retirerTousLiensEntree();
		assertFalse(n1.aLiensEntree());
	}

	@Test
	public void testRetirerTousLiensSortie() {
		n2.ajouterLienEntree(n1);
		n3.ajouterLienEntree(n1);
		assertTrue(n1.aLiensSortie());
		n1.retirerTousLiensSortie();
		assertFalse(n1.aLiensSortie());
	}

	@Test
	public void testRetirerTousLiens() {
		n1.ajouterLienEntree(n2);
		n3.ajouterLienEntree(n1);
		assertTrue(n1.aLiensEntree() && n1.aLiensSortie());
		n1.retirerTousLiens();
		assertFalse(n1.aLiensEntree() && n1.aLiensSortie());
	}

	@Test
	public void testGetLienAPartirDeNeurone() {
		n1.ajouterLienEntree(n2);
		Lien l = n1.getLiensEntree().get(0);
		assertTrue(n1.getLienAPartirDeNeurone(n2) == l);
	}

	@Test
	public void testSetFonctionEntree() {
		assertEquals(n1.getFonctionEntree().getClass(), SommeImportance.class);

		Et et = new Et();
		n1.setFonctionEntree(et);
		assertEquals(n1.getFonctionEntree(), et);
	}

	@Test
	public void testSetFonctionTransfers() {
		assertTrue(n1.getFonctionTransfers().getClass() == Etape.class);

		Rampe r = new Rampe();
		n1.setFonctionTransfers(r);
		assertEquals(r, n1.getFonctionTransfers());
	}

	@Test
	public void testGetFonctionEntree() {
		assertEquals(n1.getFonctionEntree().getClass(), SommeImportance.class);
	}

	@Test
	public void testGetFonctionTransfers() {
		assertTrue(n1.getFonctionTransfers().getClass() == Etape.class);
	}

	@Test
	public void testGetNiveauParent() {
		Niveau niv = new Niveau();
		n1.setNiveauParent(niv);
		assertEquals(n1.getNiveauParent(), niv);
	}

	@Test
	public void testSetNiveauParent() {
		Niveau niv1 = new Niveau(), niv2 = new Niveau();
		n1.setNiveauParent(niv1);
		assertEquals(n1.getNiveauParent(), niv1);
		n1.setNiveauParent(niv2);
		assertNotEquals(n1.getNiveauParent(), niv1);
	}

	@Test
	public void testGetImportancesEntree() {
		Lien l1 = new Lien(n2, n1, 900), l2 = new Lien(n3, n1, 8);
		n1.ajouterLienEntree(l1);
		n1.ajouterLienEntree(l2);

		Importance[] tabImp = { new Importance(900), new Importance(8) };

		assertEquals(tabImp[0], n1.getImportancesEntree()[0]);
		assertEquals(tabImp[1], n1.getImportancesEntree()[1]);
	}

	@Test
	public void testGetErreur() {
		assertTrue(n1.getErreur() == 0);
	}

	@Test
	public void testSetErreur() {
		assertTrue(n1.getErreur() == 0);
		n1.setErreur(5);
		assertTrue(n1.getErreur() == 5);
	}

	@Test
	public void testSetSortie() {
		assertTrue(n1.getSortie() == 0);
		n1.setSortie(8999.3554561);
		assertTrue(n1.getSortie() == 8999.3554561);
	}

	@Test
	public void testInitialiserImportanceLiensEntree() {
		Lien l1 = new Lien(n2, n1, 900), l2 = new Lien(n3, n1, 8);
		n1.ajouterLienEntree(l1);
		n1.ajouterLienEntree(l2);

		n1.initialiserImportanceLiensEntree(7);

		for (Lien lien : n1.getLiensEntree()) {
			assertTrue(lien.getImportance().getValImportance() == 7);
		}
	}

	@Test
	public void testGetNom() {
		assertNull(n1.getNom());
	}

	@Test
	public void testSetNom() {
		assertNull(n1.getNom());
		n1.setNom("test");
		assertEquals("test", n1.getNom());
	}

	@Test
	public void testClone() {
		try {
			Neurone n = (Neurone) n1.clone();
			assertTrue(n1.equals(n));
		} catch (CloneNotSupportedException e) {
			fail("erreur");
			e.printStackTrace();
		}
	}

}
