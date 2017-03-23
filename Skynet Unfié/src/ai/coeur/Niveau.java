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

	// TODO constructeur avec (int nbrNeurones, ProprieteNeurones
	// proprieteNeurones)

	public Reseau getReseauParent() {
		return reseauParent;
	}

	public void setReseauParent(Reseau reseauParent) {
		this.reseauParent = reseauParent;
	}

	public ArrayList<Neurone> getListeNeuronesNiveau() {
		return listeNeuronesNiveau;
	}

	public void ajouterNeurone(Neurone neurone) {
		if (neurone != null) {
			listeNeuronesNiveau.add(new Neurone());
			neurone.setNiveauParent(this);
		} else {
			throw new IllegalArgumentException("La neurone ne peut être nulle");
		}
		//TODO faire event
	}

}
