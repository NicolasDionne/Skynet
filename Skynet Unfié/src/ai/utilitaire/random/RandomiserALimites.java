package ai.utilitaire.random;

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
