package modele.reseau;

import java.util.ArrayList;

import ai.apprentissage.nonsupervise.competitionInter.CompetitionInterReseaux;
import ai.coeur.Reseau;

public class GenerateurReseauCIR extends GenerateurReseau {
	public void genererReseauCIR(int nbrReseaux, int nbrEntrees, int nbrSorties, int nbrNiveaux,
			int nbrNeuronesParNiveau, double importanceMin, double importanceMax) {
		listeReseaux = new ArrayList<>();
		for (int i = 0; i < nbrReseaux; i++) {

			Reseau<CompetitionInterReseaux> reseau = new Reseau<>();
			genererEntrees(reseau, nbrEntrees);
			genererSorties(reseau, nbrSorties);
			genererNiveaux(reseau, nbrNiveaux, nbrNeuronesParNiveau);
			reseau.genererLiens();
			reseau.randomizerImportances(importanceMin, importanceMax);
			listeReseaux.add(reseau);
		}
	}

}
