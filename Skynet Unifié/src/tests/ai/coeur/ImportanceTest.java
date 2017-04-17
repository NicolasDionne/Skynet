/**
 * 
 */
package tests.ai.coeur;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import ai.coeur.Importance;

/**
 * @author 1537391
 *
 */
@SuppressWarnings("all")
public class ImportanceTest {

	Importance i1, i2, i3;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		i1 = new Importance(9001);
		i2 = new Importance(-9001);
		i3 = new Importance(58);
	}

	/**
	 * Test method for {@link ai.coeur.Importance#Importance()}.
	 */
	@Test
	public void testImportance() {
		Importance imp;
		for (int i = 0; i < 40; i++) {
			imp = new Importance();
			assertTrue(-0.5 <= imp.getValImportance());
			assertTrue(0.5 >= imp.getValImportance());
		}

	}

	/**
	 * Test method for {@link ai.coeur.Importance#Importance(double)}.
	 */
	@Test
	public void testImportanceDouble() {
		Importance imp1 = new Importance(4), imp2 = new Importance(40), imp3 = new Importance(-400),
				imp4 = new Importance(9001);
		assertTrue(imp1.valImportance == 4);
		assertTrue(imp2.valImportance == 40);
		assertTrue(imp3.valImportance == -400);
		assertTrue(imp4.valImportance == 9001);
	}

	/**
	 * Test method for {@link ai.coeur.Importance#augmenterVal(double)}.
	 */
	@Test
	public void testAugmenterVal() {
		i1.augmenterVal(8);
		assertTrue(i1.valImportance == (9001 + 8));
	}

	/**
	 * Test method for {@link ai.coeur.Importance#diminuerVal(double)}.
	 */
	@Test
	public void testDiminuerVal() {
		i2.diminuerVal(10);
		assertTrue(i2.valImportance == (-9011));
	}

	/**
	 * Test method for {@link ai.coeur.Importance#setValImportance(double)}.
	 */
	@Test
	public void testSetValImportance() {
		i1.setValImportance(5);
		assertTrue(i1.valImportance == 5);
	}

	/**
	 * Test method for {@link ai.coeur.Importance#getValImportance()}.
	 */
	@Test
	public void testGetValImportance() {
		assertTrue(i1.getValImportance() == 9001);
	}

	/**
	 * Test method for {@link ai.coeur.Importance#setImportanceAleatoire()}.
	 */
	@Test
	public void testSetImportanceAleatoire() {
		boolean test1 = true, test2 = true, test3 = true, test4 = true, test5 = true;

		i1.setImportanceAleatoire();
		test1 = 9001 != i1.getValImportance();
		i1.setImportanceAleatoire();
		test2 = 9001 != i1.getValImportance();
		i1.setImportanceAleatoire();
		test3 = 9001 != i1.getValImportance();
		i1.setImportanceAleatoire();
		test4 = 9001 != i1.getValImportance();
		i1.setImportanceAleatoire();
		test5 = 9001 != i1.getValImportance();

		assertTrue(test1 || test2 || test3 || test4 || test5);

	}

	/**
	 * Test method for
	 * {@link ai.coeur.Importance#setImportanceAleatoire(double, double)}.
	 */
	@Test
	public void testSetImportanceAleatoireDoubleDouble() {
		i1.setImportanceAleatoire(5, 6);
		assertTrue(5 <= i1.getValImportance() && i1.getValImportance() <= 6);
	}

	/**
	 * Test method for
	 * {@link ai.coeur.Importance#setImportanceAleatoire(java.util.Random)}.
	 */
	@Test
	public void testSetImportanceAleatoireRandom() {
		Random r1 = new Random(), r2 = new Random(), r3 = new Random(), r4 = new Random(), r5 = new Random();
		boolean test1 = true, test2 = true, test3 = true, test4 = true, test5 = true;

		i1.setImportanceAleatoire(r1);
		test1 = 9001 != i1.getValImportance();
		i1.setImportanceAleatoire(r2);
		test2 = 9001 != i1.getValImportance();
		i1.setImportanceAleatoire(r3);
		test3 = 9001 != i1.getValImportance();
		i1.setImportanceAleatoire(r4);
		test4 = 9001 != i1.getValImportance();
		i1.setImportanceAleatoire(r5);
		test5 = 9001 != i1.getValImportance();
		assertTrue(test1 || test2 || test3 || test4 || test5);
	}

	/**
	 * Test method for {@link ai.coeur.Importance#getDonneeEntrainement()}.
	 */
	@Test
	public void testGetDonneeEntrainement() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link ai.coeur.Importance#setDonneeEntrainement(java.lang.Object)}.
	 */
	@Test
	public void testSetDonneeEntrainement() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link ai.coeur.Importance#clone()}.
	 */
	@Test
	public void testClone() {
		try {
			Importance clone = (Importance) i1.clone();
			assertTrue(i1.getValImportance() == clone.getValImportance());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for {@link ai.coeur.Importance#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		Importance iTest1 = new Importance(9001), iTest2 = new Importance(9), iTest3 = new Importance(9001),
				iTest4 = new Importance(9001);

		iTest3.changementImportance = 0.55;

		assertTrue(i1.equals(i1));
		assertFalse(i1.equals(null));
		assertTrue(i1.equals(iTest1));
		assertFalse(i1.equals(iTest2));
		assertFalse(i1.equals(iTest3));

	}

	/**
	 * Test method for {@link ai.coeur.Importance#toString()}.
	 */
	@Test
	public void testToString() {
		assertEquals(i1.toString(), "9001.0");
	}

}
