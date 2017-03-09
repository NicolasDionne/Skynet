/**
 * 
 */
package tests.ai.coeur;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ai.coeur.Importance;

/**
 * @author 1537391
 *
 */
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
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link ai.coeur.Importance#getValImportance()}.
	 */
	@Test
	public void testGetValImportance() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link ai.coeur.Importance#setImportanceAleatoire()}.
	 */
	@Test
	public void testSetImportanceAleatoire() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link ai.coeur.Importance#setImportanceAleatoire(double, double)}.
	 */
	@Test
	public void testSetImportanceAleatoireDoubleDouble() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link ai.coeur.Importance#setImportanceAleatoire(java.util.Random)}.
	 */
	@Test
	public void testSetImportanceAleatoireRandom() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link ai.coeur.Importance#getTrainingData()}.
	 */
	@Test
	public void testGetTrainingData() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link ai.coeur.Importance#setTrainingData(java.lang.Object)}.
	 */
	@Test
	public void testSetTrainingData() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link ai.coeur.Importance#clone()}.
	 */
	@Test
	public void testClone() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link ai.coeur.Importance#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link ai.coeur.Importance#toString()}.
	 */
	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

}
