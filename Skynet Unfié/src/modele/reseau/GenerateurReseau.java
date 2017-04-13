package modele.reseau;

import java.util.ArrayList;

import ai.coeur.Neurone;
import ai.coeur.Niveau;
import ai.coeur.Reseau;
import ai.coeur.apprentissage.RegleApprentissage;

public class GenerateurReseau {

	private ArrayList<Reseau<RegleApprentissage>> listeReseau;

	public void genererReseauCIR(int nbrReseaux, int nbrEntrees, int nbrSorties, int nbrNiveaux,
			int nbrNeuronesParNiveau) {
		listeReseau = new ArrayList<>();
		for (int i = 0; i < nbrReseaux; i++) {

			Reseau<RegleApprentissage> reseau = new Reseau<>();
			genererEntrees(reseau, nbrEntrees);
			genererSorties(reseau, nbrSorties);
			genererNiveaux(reseau, nbrNiveaux, nbrNeuronesParNiveau);
			reseau.genererLiens();
			reseau.randomizerImportances(-20, 20);
			listeReseau.add(reseau);
		}
	}

	public void genererEntrees(Reseau<RegleApprentissage> reseau, int nbrEntrees) {
		ArrayList<Neurone> neuronesEntrees = new ArrayList<>();

		for (int i = 0; i < nbrEntrees; i++) {
			neuronesEntrees.add(new Neurone());
		}

		reseau.setNeuronesEntree(neuronesEntrees);
	}

	public void genererSorties(Reseau<RegleApprentissage> reseau, int nbrSorties) {
		ArrayList<Neurone> neuronesSorties = new ArrayList<>();

		for (int i = 0; i < nbrSorties; i++) {
			neuronesSorties.add(new Neurone());
		}

		reseau.setNeuronesSorties(neuronesSorties);
	}

	public void genererNiveaux(Reseau<RegleApprentissage> reseau, int nbrNiveaux, int nbrNeuronesParNiveau) {
		for (int i = 0; i < nbrNiveaux; i++) {
			Niveau niveau = new Niveau(nbrNeuronesParNiveau);
			reseau.ajouterNiveau(niveau);
		}
	}

	public ArrayList<Reseau<RegleApprentissage>> getReseaux() {
		return listeReseau;
	}

}
