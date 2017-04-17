package tests.ai.coeur;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ai.coeur.Importance;
import ai.coeur.Lien;
import ai.coeur.Neurone;

public class LienTest {
	Lien l1, l2, l3, l4;
	Neurone n1, n2;
	Importance i1, i2;

	@Before
	public void setUp() throws Exception {
		n1 = new Neurone();
		n2 = new Neurone();

		i1 = new Importance();
		i2 = new Importance(30);

		l1 = new Lien(n1, n2);
		l2 = new Lien(n2, n1);
		l3 = new Lien(n1, n2, i1);
		l4 = new Lien(n1, n2, i2);
	}

	@Test
	public void testHashCode() {
		fail("Not yet implemented");
	}

	@Test
	public void testLienNeuroneNeurone() {
		Lien l = new Lien(n1, n2);
		assertTrue(l != null);
		assertTrue(n1.equals(l.getAPartirDeNeurone()));
		assertTrue(n2.equals(l.getJusquANeurone()));
		assertNull(l.getImportance());
	}

	@Test
	public void testLienNeuroneNeuroneImportance() {
		Lien l = new Lien(n1, n2, new Importance(1));
		assertTrue(l != null);
		assertTrue(n1.equals(l.getAPartirDeNeurone()));
		assertTrue(n2.equals(l.getJusquANeurone()));
		assertNotNull(l.getImportance());
	}

	@Test
	public void testLienNeuroneNeuroneDouble() {
		Lien l = new Lien(n1, n2, 8);
		assertTrue(l != null);
		assertTrue(n1.equals(l.getAPartirDeNeurone()));
		assertTrue(n2.equals(l.getJusquANeurone()));
		assertTrue(l.getImportance().getValImportance() == 8);
	}

	@Test
	public void testGetImportance() {
		assertNull(l1.getImportance());
		assertNotNull(l4);
		assertTrue(l4.getImportance().getValImportance() == 30);
	}

	@Test
	public void testSetImportance() {
		l1.setImportance(i1);
		assertEquals(l1.getImportance(), i1);
	}

	@Test
	public void testGetEntree() {
		n1.setSortie(7);
		assertTrue(l1.getEntree() == 7);
		n1.setSortie(70);
		assertTrue(l1.getEntree() == 70);
		n1.setSortie(9);
		assertTrue(l1.getEntree() == 9);
		n1.setSortie(0);
		assertTrue(l1.getEntree() == 0);
	}

	@Test
	public void testGetEntreeSelonImportance() {
		n1.setSortie(1);
		assertTrue(l4.getEntreeSelonImportance()==30);
		n1.setSortie(2);
		assertTrue(l4.getEntreeSelonImportance()==60);
	}

	@Test
	public void testGetAPartirDeNeurone() {
		assertEquals(l1.getAPartirDeNeurone(), n1);
	}

	@Test
	public void testGetJusquANeurone() {
		assertEquals(l1.getJusquANeurone(), n2);
	}

	@Test
	public void testClone() {
		fail("Not yet implemented");
	}

	@Test
	public void testEqualsObject() {
		Lien l=new Lien(n1, n2, 30);
		assertTrue(l4.equals(l));
	}

	@Test
	public void testToString() {
		assertEquals("Lien", "actual");
	}

}
