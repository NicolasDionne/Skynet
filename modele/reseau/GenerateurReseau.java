package modele.reseau;

import java.util.ArrayList;

import ai.apprentissage.nonsupervise.CompetitionInterReseaux;
import ai.coeur.Neurone;
import ai.coeur.Niveau;
import ai.coeur.Reseau;

public class GenerateurReseau {
	// TODO Javadoc
	private ArrayList<Reseau<CompetitionInterReseaux>> listeReseau;

	public void genererReseauCIR(int nbrReseaux, int nbrEntrees, int nbrSorties, int nbrNiveaux,
			int nbrNeuronesParNiveau, double importanceMin, double importanceMax) {
		listeReseau = new ArrayList<>();
		for (int i = 0; i < nbrReseaux; i++) {

			Reseau<CompetitionInterReseaux> reseau = new Reseau<>();
			genererEntrees(reseau, nbrEntrees);
			genererSorties(reseau, nbrSorties);
			genererNiveaux(reseau, nbrNiveaux, nbrNeuronesParNiveau);
			reseau.genererLiens();
			reseau.randomizerImportances(importanceMin, importanceMax);
			listeReseau.add(reseau);
		}
	}

	public void genererEntrees(Reseau<CompetitionInterReseaux> reseau, int nbrEntrees) {
		ArrayList<Neurone> neuronesEntrees = new ArrayList<>();

		for (int i = 0; i < nbrEntrees; i++) {
			neuronesEntrees.add(new Neurone());
		}

		reseau.setNeuronesEntree(neuronesEntrees);
	}

	public void genererSorties(Reseau<CompetitionInterReseaux> reseau, int nbrSorties) {
		ArrayList<Neurone> neuronesSorties = new ArrayList<>();

		for (int i = 0; i < nbrSorties; i++) {
			neuronesSorties.add(new Neurone());
		}

		reseau.setNeuronesSorties(neuronesSorties);
	}

	public void genererNiveaux(Reseau<CompetitionInterReseaux> reseau, int nbrNiveaux, int nbrNeuronesParNiveau) {
		for (int i = 0; i < nbrNiveaux; i++) {
			Niveau niveau = new Niveau(nbrNeuronesParNiveau);
			reseau.ajouterNiveau(niveau);
		}
	}

	public ArrayList<Reseau<CompetitionInterReseaux>> getReseauxCIR() {
		return listeReseau;
	}

}
