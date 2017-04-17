package ai.coeur.transfers;

/**
 * Classe abstraite pour toutes les fonctions de transfers
 */
abstract public class FonctionTransfers {
	// TODO Javadoc
	protected double sortie;

	abstract public double getSortie(double totalEntree);

	public double getDerivee(double totalEntree) {
		return 1;
	}

}
