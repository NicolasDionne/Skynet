package ai.coeur;

import java.util.ArrayList;

public class Niveau {

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
			throw new IllegalArgumentException("La neurone ne peut égaler null");
		}

		neurone.setNiveauParent(this);
		listeNeuronesNiveau.add(neurone);

	}

	public void ajouterNeurone(int position, Neurone neurone) {
		if (neurone == null) {
			throw new IllegalArgumentException("La neurone ne peut égaler null");
		}

		listeNeuronesNiveau.add(position, neurone);

		neurone.setNiveauParent(this);

	}

	public void setNeurone(int position, Neurone neurone) {
		if (neurone == null) {
			throw new IllegalArgumentException("La neurone ne peut égaler null");
		}

		listeNeuronesNiveau.set(position, neurone);

		neurone.setNiveauParent(this);

	}

	public void retirerNeurone(Neurone neurone) {
		listeNeuronesNiveau.remove(positionDe(neurone));
	}

	public void retirerNeuroneA(int position) {
		listeNeuronesNiveau.remove(position);
	}

	public void retirerToutesNeurones() {
		listeNeuronesNiveau.clear();
	}

	public Neurone getNeuroneA(int position) {
		return listeNeuronesNiveau.get(position);
	}

	public int positionDe(Neurone neurone) {
		return listeNeuronesNiveau.indexOf(neurone);
	}

	public int getNombreDeNeurones() {
		return listeNeuronesNiveau.size();
	}

	/**
	 * lance la méthode calculer() de toutes les neurones du réseau
	 */
	public void calculer() {
		for (Neurone neurone : listeNeuronesNiveau) {
			neurone.calculer();
		}
	}

	public void reinitialiser() {
		for (Neurone neurone : listeNeuronesNiveau) {
			neurone.reinitialiser();
		}
	}

	public void initialiserImportanceLiensEntree(double valImportance) {
		for (Neurone neurone : listeNeuronesNiveau) {
			neurone.initialiserImportanceLiensEntree(valImportance);
		}
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public boolean isVide() {
		return listeNeuronesNiveau.isEmpty();
	}

}
