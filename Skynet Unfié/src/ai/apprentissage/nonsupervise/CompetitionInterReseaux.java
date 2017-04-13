package ai.apprentissage.nonsupervise;

import java.util.ArrayList;

import ai.coeur.Lien;
import ai.coeur.Reseau;
import ai.coeur.apprentissage.ApprentissageNonSupervise;
import utilitaires.QuickSort;

public class CompetitionInterReseaux extends ApprentissageNonSupervise {

	public ArrayList<Reseau<CompetitionInterReseaux>> listeReseauxEnCompetitions;

	public ArrayList<Reseau<CompetitionInterReseaux>> listeMeilleursReseaux;

	public double margeErreurAcceptable = 5;

	public CompetitionInterReseaux(ArrayList<Reseau<CompetitionInterReseaux>> listeReseauxEnCompetitions) {
		this.listeReseauxEnCompetitions = listeReseauxEnCompetitions;
		for (Reseau<CompetitionInterReseaux> reseau : this.listeReseauxEnCompetitions) {
			reseau.setRegleApprentissage(this);
		}
		listeMeilleursReseaux = new ArrayList<>();
	}

	@Override
	protected void mettreAJourImportancesReseau() {
		classerReseaux();
		trouverMeilleurs();
	}

	public void classerReseaux() {
		listeReseauxEnCompetitions = QuickSort.sort(listeReseauxEnCompetitions);
	}

	public void trouverMeilleurs() {
		for (int i = 0; i < listeReseauxEnCompetitions.size() / 2; i++) {
			listeMeilleursReseaux.add(listeReseauxEnCompetitions.get(i));
		}
	}

	public void trouverSimilarites(Reseau<CompetitionInterReseaux> reseau1, Reseau<CompetitionInterReseaux> reseau2) {
		ArrayList<Lien> listeLiensReseau1 = reseau1.getListeLiens();
	}

}
