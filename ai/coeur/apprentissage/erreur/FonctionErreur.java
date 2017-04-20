package ai.coeur.apprentissage.erreur;

public interface FonctionErreur {
	// TODO Javadoc
	public double getErreurTotale();

	public void reinitialiser();

	public double[] calculerPatternErreur(double[] sortiesPredites, double[] objectifSorties);

}
