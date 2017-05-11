package ai.coeur.apprentissage.erreur;

public interface FonctionErreur {
	public double getErreurTotale();

	public void reinitialiser();

	public double[] calculerPatternErreur(double[] sortiesPredites, double[] objectifSorties);

}
