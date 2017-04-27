package modele.reseau;

import java.util.ArrayList;

import ai.coeur.Neurone;
import ai.coeur.Niveau;
import ai.coeur.Reseau;
import ai.coeur.entree.SommeImportance;
import ai.coeur.transfers.EtapeSortie;

public class GenerateurReseau {
	protected ArrayList<Reseau> listeReseaux;

	protected void genererEntrees(Reseau reseau, int nbrEntrees) {
		ArrayList<Neurone> neuronesEntrees = new ArrayList<>();

		for (int i = 0; i < nbrEntrees; i++) {
			neuronesEntrees.add(new Neurone());
		}

		reseau.setNeuronesEntree(neuronesEntrees);
	}

	protected void genererSorties(Reseau reseau, int nbrSorties) {
		ArrayList<Neurone> neuronesSorties = new ArrayList<>();

		for (int i = 0; i < nbrSorties; i++) {
			neuronesSorties.add(new Neurone(new SommeImportance(), new EtapeSortie()));
		}

		reseau.setNeuronesSorties(neuronesSorties);
	}

	protected void genererNiveaux(Reseau reseau, int nbrNiveaux, int nbrNeuronesParNiveau) {
		for (int i = 0; i < nbrNiveaux; i++) {
			Niveau niveau = new Niveau(nbrNeuronesParNiveau);
			reseau.ajouterNiveau(niveau);
		}
	}

	public ArrayList<Reseau> getReseauxCIR() {
		return listeReseaux;
	}

}
