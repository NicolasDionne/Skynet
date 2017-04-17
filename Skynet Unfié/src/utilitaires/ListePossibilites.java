package utilitaires;

import java.util.ArrayList;

public class ListePossibilites {

	private ArrayList<ArrayList<Double>> listePossibilites = new ArrayList<>();

	public void genererListeArrangementsAvecRepetition(double[] nombresPossibles, int tailleRestante,
			ArrayList<Double> precedent) {
		for (int i = 0; i < nombresPossibles.length; i++) {
			precedent.add(nombresPossibles[i]);
			if (tailleRestante == 1) {
				ArrayList<Double> clone = new ArrayList<>();
				for (double d : precedent) {
					clone.add(d);
				}
				listePossibilites.add(clone);
			} else {
				genererListeArrangementsAvecRepetition(nombresPossibles, tailleRestante - 1, precedent);
			}
			precedent.remove(precedent.size() - 1);
		}
	}

	public ArrayList<ArrayList<Double>> getListePossibilites() {
		return listePossibilites;
	}

	public void reset() {
		listePossibilites.clear();
	}

}
