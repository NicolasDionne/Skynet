package ai.coeur.entree;

import java.util.ArrayList;

import ai.coeur.Lien;

public class SommeImportance extends FonctionEntree {

	@Override
	public double getSortie(ArrayList<Lien> liensEntree) {
		double sortie = 0;
		for (Lien lien : liensEntree) {
			sortie += lien.getEntreeSelonImportance();
		}

		return sortie;
	}

	public static double[] getSortie(double[] entrees, double[] importances) {
		double[] sortie = new double[entrees.length];

		for (int i = 0; i < entrees.length; i++) {
			sortie[i] += entrees[i] * importances[i];
		}

		return sortie;
	}
}
