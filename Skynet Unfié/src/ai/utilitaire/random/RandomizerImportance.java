package ai.utilitaire.random;

import java.util.Random;

import ai.coeur.Lien;
import ai.coeur.Neurone;
import ai.coeur.Niveau;
import ai.coeur.Reseau;

public class RandomizerImportance {

	protected Random randomizer;

	public RandomizerImportance() {
		this.randomizer = new Random();
	}

	public RandomizerImportance(Random randomizer) {
		this.randomizer = randomizer;
	}

	public Random getRandomizer() {
		return randomizer;
	}

	public void setRandomizer(Random randomizer) {
		this.randomizer = randomizer;
	}

	public void randomize(Reseau<?> reseau) {
		for (Niveau niveau : reseau.getListeNiveaux()) {
			this.randomize(niveau);
		}
	}

	public void randomize(Niveau niveau) {
		for (Neurone neurone : niveau.getListeNeuronesNiveau()) {
			randomize(neurone);
		}
	}

	public void randomize(Neurone neurone) {
		int nombreDeConnnectionsEntrees = neurone.getLiensEntree().size();
		double val = 1 / Math.sqrt(nombreDeConnnectionsEntrees);
		double coefficient = (val == 0) ? 1 : val;

		for (Lien lien : neurone.getLiensEntree()) {
			lien.getImportance().setValImportance(prochaineImportanceRandom());
		}
	}

	protected double prochaineImportanceRandom() {
		return randomizer.nextDouble();
	}
}
