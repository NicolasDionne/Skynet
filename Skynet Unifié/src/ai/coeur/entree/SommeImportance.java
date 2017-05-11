package ai.coeur.entree;

import java.util.ArrayList;

import ai.coeur.Lien;

/**
 * Calcule la valeur d'entré de la <code>Neurone</code> en fonction de la valeur
 * de l'importance des liens d'entré de la <code>Neurone</code>.
 * <P>
 * Cette classe hérite de la classe <code>FonctionEntree</code>.
 * 
 * @see FonctionEntree
 */
public class SommeImportance extends FonctionEntree {

	private static final long serialVersionUID = -6742742486463591274L;

	/**
	 * Permet d'avoir la valeur d'entré de la <code>Neurone</code> comme étant
	 * la somme des valeur de sorties des neurones multipliés par les valeurs
	 * d'importance du lien.
	 * <P>
	 * La méthode fonctionne comme suit:<br>
	 * {@code for (Lien lien : liensEntree)}<code> &#123</code><br>
	 * {@code sortie += lien.getEntreeSelonImportance();}<br>
	 * <code>&#125</code>
	 * 
	 * @see FonctionEntree#getSortie(ArrayList)
	 */
	@Override
	public double getSortie(ArrayList<Lien> liensEntree) {
		double sortie = 0;
		for (Lien lien : liensEntree) {
			sortie += lien.getEntreeSelonImportance();
		}

		return sortie;
	}

	/**
	 * fait la même chose que {@link Lien#getEntreeSelonImportance()}.
	 * 
	 * @param entrees
	 *            <code>double[]</code>, le tableau des valeurs d'entré.
	 * @param importances
	 *            <code>double[]</code>, le tableau des valeurs d'importance.
	 * @return <code>double[]<code>, le tableau des valeurs d'entré selon
	 *         l'importance.
	 */
	public static double[] getSortie(double[] entrees, double[] importances) {
		double[] sortie = new double[entrees.length];

		for (int i = 0; i < entrees.length; i++) {
			sortie[i] += entrees[i] * importances[i];
		}

		return sortie;
	}
}
