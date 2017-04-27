package ai.coeur.transfers;

import java.io.Serializable;

/**
 * Classe abstraite pour toutes les fonctions de transfers
 */
abstract public class FonctionTransfers implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1568710356491223774L;
	// TODO Javadoc
	protected double sortie;

	abstract public double getSortie(double totalEntree);

	public double getDerivee(double totalEntree) {
		return 1;
	}

}
