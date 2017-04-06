package modele.reseau;

import java.util.ArrayList;

import ai.apprentissage.supervise.RetropropagationDuGradient;
import ai.coeur.Neurone;
import ai.coeur.Niveau;
import ai.coeur.Reseau;

public class GenerateurReseau {

	ArrayList<Neurone> neuronesEntrees;

	private Reseau<?> reseau;

	public void genererReseauRDG() {
		reseau = new Reseau<RetropropagationDuGradient>();
		reseau.setNeuronesEntree(neuronesEntrees);
	}

	public void genererEntrees() {
		neuronesEntrees = new ArrayList<>();
		for (int i = 0; i < 40; i++) {
			neuronesEntrees.add(new Neurone());
		}
	}

	public void genererSortie() {
		ArrayList<Neurone> sorties = new ArrayList<>();
		sorties.add(new Neurone());
		reseau.setNeuronesSorties(sorties);
	}

	public void genererNiveaux() {
		for (int i = 0; i < 5; i++) {
			Niveau niveau = new Niveau(20);
			reseau.ajouterNiveau(niveau);
		}
	}

}
