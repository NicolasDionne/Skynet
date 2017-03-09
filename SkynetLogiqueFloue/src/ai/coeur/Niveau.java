package ai.coeur;

import java.util.ArrayList;

public class Niveau {
	// TODO la classe
	private Reseau reseauParent;

	protected ArrayList<Neurone> listeNeuronesNiveau;

	private String nom;

	public Niveau() {
		listeNeuronesNiveau = new ArrayList<>();
	}

	public Niveau(int nbrNeurones) {
		listeNeuronesNiveau = new ArrayList<>(nbrNeurones);
	}

	public Reseau getReseauParent() {
		return reseauParent;
	}

	public void setReseauParent(Reseau reseauParent) {
		this.reseauParent = reseauParent;
	}

	public void ajouterNeurone() {
		listeNeuronesNiveau.add(new Neurone());
	}

}
