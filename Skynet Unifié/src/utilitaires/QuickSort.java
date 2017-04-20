package utilitaires;

import java.util.List;

import exception.InterfaceNotImplementedException;

/**
 * Classe utilitaire qui sert à classer un vecteur instance de <code>List</code>
 * selon l'algorithme de tri quicksort (tri rapide).
 * <P>
 * Par contre, pour trier le vecteur, tous les objets contenus dans ce dernier
 * <span
 * style="text-decoration:underline;"><b>doivent</b></span>
 * implémenter l'interface <code>Sortable</code>.
 * 
 * @see Sortable
 */
public class QuickSort {

	private static List liste;
	private static int nbr;

	/**
	 * Classe l'instance de <code>List</code> selon la l'algorithme de tri
	 * quicksort (tri rapide).
	 * 
	 * @param listeASorter
	 *            <code>List</code>, la liste à classer.
	 * @return <code>List</code>, la liste classée.
	 */
	public static List sort(List listeASorter) {
		try {
			if (listeASorter == null || listeASorter.size() == 0) {
				throw new IllegalArgumentException("");
			} else {
				if (validerSortable(listeASorter)) {
					liste = listeASorter;
					nbr = listeASorter.size();
					quicksort(0, nbr - 1);
				} else {
					throw new InterfaceNotImplementedException(
							"Tous les éléments du vecteur doivent implémenter l'interface Sortable");
				}
			}
		} catch (InterfaceNotImplementedException e) {
			e.printStackTrace();
		}
		return liste;
	}

	private static void quicksort(int bas, int haut) {
		int i = bas, j = haut;
		Object pivot = liste.get(bas + (haut - bas) / 2);
		while (i <= j) {
			while (((Sortable) liste.get(i)).getValeurLorsSort() < ((Sortable) pivot).getValeurLorsSort()) {
				i++;
			}
			while (((Sortable) liste.get(j)).getValeurLorsSort() > ((Sortable) pivot).getValeurLorsSort()) {
				j--;
			}

			if (i <= j) {
				echanger(i, j);
				i++;
				j--;
			}
		}
		if (bas < j)
			quicksort(bas, j);
		if (i < haut)
			quicksort(i, haut);
	}

	private static void echanger(int i, int j) {
		Object temp = liste.get(i);
		liste.set(i, liste.get(j));
		liste.set(j, temp);
	}

	private static boolean validerSortable(List listeASort) {
		boolean so = true;

		for (Object object : listeASort) {
			if (!Sortable.class.isAssignableFrom(object.getClass())) {
				so = false;
				break;
			}
		}

		return so;
	}

}
