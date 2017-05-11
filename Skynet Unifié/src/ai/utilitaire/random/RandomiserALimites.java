package ai.utilitaire.random;

/**
 * Permet d'avoir une importance aléatoire entre deux bornes.
 * 
 * @see RandomizerImportance
 */
public class RandomiserALimites extends RandomizerImportance {
	protected double min;

	protected double max;

	public RandomiserALimites(double min, double max) {
		this.min = min;
		this.max = max;
	}

	@Override
	protected double prochaineImportanceRandom() {
		return min + randomizer.nextDouble() * (max - min);
	}
}
