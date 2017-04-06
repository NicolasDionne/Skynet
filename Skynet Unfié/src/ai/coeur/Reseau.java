package ai.coeur;

import java.util.ArrayList;
import java.util.Random;

import ai.coeur.apprentissage.ApprentissageIteratif;
import ai.coeur.apprentissage.RegleApprentissage;
import ai.coeur.donnee.EnsembleDonnees;
import ai.utilitaire.random.RandomiserALimites;
import ai.utilitaire.random.RandomizerImportance;

public class Reseau<R extends RegleApprentissage> {

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
			genererLiens();
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
			genererLiens();
		}
	}

	public void retirerNiveau(Niveau niveau) {
		if (!listeNiveaux.remove(niveau)) {
			throw new RuntimeException("Le niveau n'était pas dans le réseau");
		}
		genererLiens();
	}

	public void retirerNiveauA(int index) {
		listeNiveaux.remove(index);
		genererLiens();
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
			genererLiens();
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

	public void apprendre(EnsembleDonnees ensembleDonnees, R regleApprentissage) {
		setRegleApprentissage(regleApprentissage);
		regleApprentissage.apprendre(ensembleDonnees);
	}

	public void arreterApperentissage() {
		if (regleApprentissage instanceof ApprentissageIteratif) {
			((ApprentissageIteratif) regleApprentissage).pauser();
		}
	}

	public void resumerApprentissage() {
		if (regleApprentissage instanceof ApprentissageIteratif) {
			((ApprentissageIteratif) regleApprentissage).resumer();
		}
	}

	public void randomizerImportances() {
		randomizerImportances(new RandomizerImportance());
	}

	public void randomizerImportances(double min, double max) {
		randomizerImportances(new RandomiserALimites(min, max));
	}

	public void randomizerImportances(Random randomizer) {
		randomizerImportances(new RandomizerImportance(randomizer));
	}

	public void randomizerImportances(RandomizerImportance randomizerImportance) {
		randomizerImportance.randomize(this);
	}

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
		genererLiens();
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
		genererLiens();
	}

	public R getRegleApprentissage() {
		return regleApprentissage;
	}

	public void setRegleApprentissage(R regleApprentissage) {
		this.regleApprentissage = regleApprentissage;
	}

	public void genererLiens() {
		retirerTousLiens();
		if (listeNiveaux.isEmpty()) {

			for (int i = 0; i < neuronesEntree.size(); i++) {
				Neurone neuroneEntree = neuronesEntree.get(i);
				for (int j = 0; j < neuronesSorties.size(); j++) {
					Neurone neuroneSortie = neuronesSorties.get(j);
					neuroneSortie.ajouterLienEntree(neuroneEntree);
				}
			}

		} else {

			for (int i = 0; i < listeNiveaux.get(0).getNombreDeNeurones(); i++) {
				Neurone neurone = listeNiveaux.get(0).getNeuroneA(i);
				for (Neurone neuroneEntree : neuronesEntree) {
					neurone.ajouterLienEntree(neuroneEntree);
				}
			}

			for (int i = 0; i < listeNiveaux.get(listeNiveaux.size() - 1).getNombreDeNeurones(); i++) {
				Neurone neuroneEntree = listeNiveaux.get(listeNiveaux.size() - 1).getNeuroneA(i);
				for (Neurone neurone : neuronesSorties) {
					neurone.ajouterLienEntree(neuroneEntree);
				}
			}

			if (listeNiveaux.size() > 1) {
				for (int i = 1; i < listeNiveaux.size() - 1; i++) {
					for (Neurone neurone : listeNiveaux.get(i).getListeNeuronesNiveau()) {
						for (Neurone neuroneEntree : listeNiveaux.get(i - 1).getListeNeuronesNiveau()) {
							neurone.ajouterLienEntree(neuroneEntree);
						}
					}
				}
			}
		}
	}

	public void retirerTousLiens() {
		for (Niveau niveau : listeNiveaux) {
			for (Neurone neurone : niveau.getListeNeuronesNiveau()) {
				neurone.retirerTousLiens();
			}
		}

		for (Neurone neurone : neuronesEntree) {
			neurone.retirerTousLiens();
		}

		for (Neurone neurone : neuronesSorties) {
			neurone.retirerTousLiens();
		}
	}

}
