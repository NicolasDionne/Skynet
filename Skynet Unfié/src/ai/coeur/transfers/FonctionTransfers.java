package ai.coeur.transfers;

abstract public class FonctionTransfers {

	protected double sortie;

	abstract public double getSortie(double totalEntree);

	public double getDerivee(double totalEntree) {
		return 1;
	}

}
