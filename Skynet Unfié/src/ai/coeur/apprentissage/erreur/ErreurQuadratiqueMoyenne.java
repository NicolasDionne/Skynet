package ai.coeur.apprentissage.erreur;

public class ErreurQuadratiqueMoyenne implements FonctionErreur {

	private double erreurTotale;

	private double comptePattern;

	public ErreurQuadratiqueMoyenne() {
		reinitialiser();
	}

	@Override
	public void reinitialiser() {
		erreurTotale = 0;
		comptePattern = 0;
	}

	@Override
	public double getErreurTotale() {
		return erreurTotale / (2 * comptePattern);
	}

	@Override
	public double[] calculerPatternErreur(double[] sortiesPredites, double[] objectifSorties) {
		double[] erreurPattern = new double[objectifSorties.length];

		for (int i = 0; i < sortiesPredites.length; i++) {
			erreurPattern[i] = objectifSorties[i] - sortiesPredites[i];
			erreurTotale += erreurPattern[i] * erreurPattern[i];
		}

		comptePattern++;

		return erreurPattern;
	}

}
