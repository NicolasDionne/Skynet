package utilitaires;

import java.util.ArrayList;

import ai.apprentissage.nonsupervise.CompetitionInterReseaux;
import ai.coeur.Reseau;

public class QuickSort {
	private static ArrayList<Reseau<CompetitionInterReseaux>> liste;
	private static int nbr;

	public static ArrayList<Reseau<CompetitionInterReseaux>> sort(
			ArrayList<Reseau<CompetitionInterReseaux>> listeReseauxEnCompetitions) {
		// check for empty or null array
		if (listeReseauxEnCompetitions == null || listeReseauxEnCompetitions.size() == 0) {

		}
		liste = listeReseauxEnCompetitions;
		nbr = listeReseauxEnCompetitions.size();
		quicksort(0, nbr - 1);
		return liste;
	}

	private static void quicksort(int low, int high) {
		int i = low, j = high;
		Reseau<?> pivot = liste.get(low + (high - low) / 2);
		while (i <= j) {
			while (Integer.MAX_VALUE - liste.get(i).getScore() < Integer.MAX_VALUE - pivot.getScore()) {
				i++;
			}
			while (Integer.MAX_VALUE - liste.get(j).getScore() > Integer.MAX_VALUE - pivot.getScore()) {
				j--;
			}

			if (i <= j) {
				exchange(i, j);
				i++;
				j--;
			}
		}
		if (low < j)
			quicksort(low, j);
		if (i < high)
			quicksort(i, high);
	}

	private static void exchange(int i, int j) {
		Reseau<CompetitionInterReseaux> temp = liste.get(i);
		liste.set(i, liste.get(j));
		liste.set(j, temp);
	}
}
