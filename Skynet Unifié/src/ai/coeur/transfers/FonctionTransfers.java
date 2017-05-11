package ai.coeur.transfers;

import java.io.Serializable;

import ai.coeur.Neurone;
import ai.coeur.entree.FonctionEntree;

/**
 * Classe abstraite pour toutes les fonctions de transfers.
 * <P>
 * Détermine la valeur de sortie de la <code>Neurone</code> selon la valeur
 * déterminée par la <code>FonctionEntree</code> de la neurone.
 * 
 * @see Neurone
 * @see FonctionEntree
 */
abstract public class FonctionTransfers implements Serializable {
	private static final long serialVersionUID = 1568710356491223774L;
	protected double sortie;

	public double getDerivee(double totalEntree) {
		return 1;
	}

	/**
	 * Détermine la valeur de sortie de la <code>Neurone</code>.
	 * 
	 * @param totalEntree
	 *            <code>double</code>, la valeur calculée selon la
	 *            <code>FonctionEntree</code> de la neurone.
	 * @return <code>double</code>, la valeur de sortie de la neurone.
	 */
	abstract public double getSortie(double totalEntree);

}
