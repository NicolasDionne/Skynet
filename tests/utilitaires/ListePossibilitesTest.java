package tests.utilitaires;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import utilitaires.ListePossibilites;

public class ListePossibilitesTest {
	ListePossibilites l;
	double[] nombresPossibles = { 0, 1 };

	@Before
	public void setUp() throws Exception {
		l = new ListePossibilites();
	}

	@Test
	public void testGenererListeArrangementsAvecRepetition() {

		l.genererListeArrangementsAvecRepetition(nombresPossibles, 4, new ArrayList<>());
		assertTrue(l.getListePossibilites().size() == 16);

		l.reset();
		l.genererListeArrangementsAvecRepetition(nombresPossibles, 6, new ArrayList<>());
		assertTrue(l.getListePossibilites().size() == 64);

		l.reset();
		l.genererListeArrangementsAvecRepetition(nombresPossibles, 8, new ArrayList<>());
		assertTrue(l.getListePossibilites().size() == 256);
	}

}
