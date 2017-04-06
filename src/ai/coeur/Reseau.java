package ai.coeur;

import java.util.ArrayList;

import ai.coeur.apprentissage.RegleApprentissage;
import ai.coeur.donnee.EnsembleDonnees;

public class Reseau<R extends RegleApprentissage> {

	// TODO la classe

	private ArrayList<Niveau> listeNiveaux;
	private ArrayList<Neurone> neuronesEntree;
	private ArrayList<Neurone> neuronesSorties;
	private double[] tableauSorties;

	private R regleApprentissage;

	public Reseau() {
		this.listeNiveaux = new ArrayList<>();
		this.neuronesEntree = new ArrayList<>();
		this.neuronesSorties = new ArrayList<>();
	}

	public void ajouterNiveau(Niveau niveau) {
		if (niveau == null) {
			throw new IllegalArgumentException("ne peut égaler null");
		} else {
			niveau.setReseauParent(this);
			listeNiveaux.add(niveau);
		}
	}

	public void ajouterNiveau(int index, Niveau niveau) {
		if (niveau == null) {
			throw new IllegalArgumentException("ne peut égaler null");
		} else if (0 < index) {
			throw new IllegalArgumentException("ne peut être plus petit que 0");
		} else {
			niveau.setReseauParent(this);
			listeNiveaux.add(index, niveau);
		}
	}

	public void retirerNiveau(Niveau niveau) {
		if (!listeNiveaux.remove(niveau)) {
			throw new RuntimeException("Le niveau n'était pas dans le réseau");
		}
	}

	public void retirerNiveauA(int index) {
		listeNiveaux.remove(index);
	}

	public ArrayList<Niveau> getListeNiveaux() {
		return this.listeNiveaux;
	}

	public Niveau getNiveauA(int index) {
		return this.listeNiveaux.get(index);
	}

	public int indexDe(Niveau niveau) {
		return this.listeNiveaux.indexOf(niveau);
	}

	public int getNombreNiveaux() {
		return this.listeNiveaux.size();
	}

	public void setEntree(double... entrees) {
		if (entrees.length != neuronesEntree.size()) {
			throw new IllegalArgumentException("la taille n'est pas conforme");
		} else {
			for (int i = 0; i < this.neuronesEntree.size(); i++) {
				neuronesEntree.get(i).setTotalEntrees(entrees[i]);
			}
		}
	}

	public double[] getSortie() {
		this.tableauSorties = new double[this.neuronesSorties.size()];

		for (int i = 0; i < this.neuronesSorties.size(); i++) {
			this.tableauSorties[i] = neuronesSorties.get(i).getSortie();
		}

		return this.tableauSorties;
	}

	public void calculer() {
		for (int i = 0; i < listeNiveaux.size(); i++) {
			listeNiveaux.get(i).calculer();
		}
	}

	public void reinitialiser() {
		for (Niveau niveau : listeNiveaux) {
			niveau.reinitialiser();
		}
	}

	public void apprendre(EnsembleDonnees ensembleDonnees) {
		if (ensembleDonnees == null) {
			throw new IllegalArgumentException("ne peut égaler null");
		} else {
			regleApprentissage.apprendre(ensembleDonnees);
		}
	}
	// TODO finir la classe

	public ArrayList<Neurone> getNeuronesEntree() {
		return neuronesEntree;
	}

	public int getNombreEntrees() {
		return this.getNeuronesEntree().size();
	}

	public void setNeuronesEntree(ArrayList<Neurone> neuronesEntree) {
		for (Neurone neurone : neuronesEntree) {
			this.neuronesEntree.add(neurone);
		}
	}

	public ArrayList<Neurone> getNeuronesSorties() {
		return neuronesSorties;
	}

	public int getNombreSorties() {
		return getNeuronesSorties().size();
	}

	public void setNeuronesSorties(ArrayList<Neurone> neuronesSorties) {
		for (Neurone neurone : neuronesSorties) {
			this.neuronesSorties.add(neurone);
		}
		this.tableauSorties = new double[this.neuronesSorties.size()];
	}

}