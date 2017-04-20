package ai.apprentissage.nonsupervise;

import java.util.ArrayList;
import java.util.LinkedList;

import ai.apprentissage.nonsupervise.competitionInterReseau.Similarite;
import ai.coeur.Lien;
import ai.coeur.Niveau;
import ai.coeur.Reseau;
import ai.coeur.apprentissage.ApprentissageNonSupervise;
import modele.exceptions.ConstructorException;
import utilitaires.QuickSort;

public class CompetitionInterReseaux extends ApprentissageNonSupervise {

	private ArrayList<Reseau<CompetitionInterReseaux>> listeReseauxEnCompetitions;

	private ArrayList<Reseau<CompetitionInterReseaux>> listeMeilleursReseaux;

	private ArrayList<Reseau<CompetitionInterReseaux>> listeMoinsBonsReseaux;

	private LinkedList<Similarite> listeSimilaritesMeilleursReseaux;

	public double margeErreurAcceptable = 5;
	public double importanceMin = -20;
	public double importanceMax = 20;

	public CompetitionInterReseaux(ArrayList<Reseau<CompetitionInterReseaux>> listeReseauxEnCompetitions) {
		try {

			if (isReseauxIdentiques(listeReseauxEnCompetitions)) {

				this.listeReseauxEnCompetitions = listeReseauxEnCompetitions;
				for (Reseau<CompetitionInterReseaux> reseau : this.listeReseauxEnCompetitions) {
					reseau.setRegleApprentissage(this);
				}
				listeMeilleursReseaux = new ArrayList<>();
				listeMoinsBonsReseaux = new ArrayList<>();
				listeSimilaritesMeilleursReseaux = new LinkedList<>();

			} else {
				throw new ConstructorException("les réseaux en compétitions doivent être identiques");
			}

		} catch (ConstructorException e) {

			e.printStackTrace();
		}
	}

	@Override
	public void faireUneIterationApprentissage() {
		avantEpoch();
		mettreAJourImportancesReseau();
		apresEpoch();
	}

	@Override
	protected void mettreAJourImportancesReseau() {
		trouverMeilleurs();
		for (int i = 0; i < listeMeilleursReseaux.size() - 1; i++) {
			for (int j = i + 1; j < listeMeilleursReseaux.size(); j++) {
				trouverSimilarites(listeMeilleursReseaux.get(i), listeMeilleursReseaux.get(j));
			}
		}
		validerSimilarites();
	}

	@Override
	protected void avantEpoch() {
		listeMeilleursReseaux.clear();
		listeSimilaritesMeilleursReseaux.clear();
		classerReseaux();
	}

	@Override
	protected void apresEpoch() {
		randomizerImportanceReseaux();
		appliquerSimilarites();
	}

	private void classerReseaux() {
		listeReseauxEnCompetitions = (ArrayList<Reseau<CompetitionInterReseaux>>) QuickSort
				.sort(listeReseauxEnCompetitions);
	}

	private void trouverMeilleurs() {
		for (int i = 0; i < listeReseauxEnCompetitions.size(); i++) {
			if (i < listeReseauxEnCompetitions.size() / 2) {
				listeMeilleursReseaux.add(listeReseauxEnCompetitions.get(i));
			} else {
				listeMoinsBonsReseaux.add(listeReseauxEnCompetitions.get(i));
			}
		}
	}

	private void trouverSimilarites(Reseau<CompetitionInterReseaux> reseau1, Reseau<CompetitionInterReseaux> reseau2) {
		ArrayList<Lien> listeLiensReseau1 = reseau1.getListeLiens();
		ArrayList<Lien> listeLiensReseau2 = reseau2.getListeLiens();
		mettreAJourNomsLiens(listeLiensReseau1);
		mettreAJourNomsLiens(listeLiensReseau2);

		for (int i = 0; i < listeLiensReseau1.size(); i++) {
			Lien lienReseau1 = listeLiensReseau1.get(i);
			Lien lienReseau2 = listeLiensReseau2.get(i);

			if (Math.abs(lienReseau1.getImportance().valImportance
					- lienReseau2.getImportance().valImportance) <= margeErreurAcceptable) {
				double valAAppliquer = getMoyenne(lienReseau1.getImportance().valImportance,
						lienReseau2.getImportance().valImportance);
				Similarite similarite = new Similarite(lienReseau1.getNom(), valAAppliquer);
				listeSimilaritesMeilleursReseaux.add(similarite);
			}
		}

	}

	private void mettreAJourNomsLiens(ArrayList<Lien> listeLiensReseau) {
		for (Lien lien : listeLiensReseau) {
			lien.setNom();
		}
	}

	private double getMoyenne(double d1, double d2) {
		return (d1 + d2) / 2;
	}

	private void randomizerImportanceReseaux() {
		for (Reseau<CompetitionInterReseaux> reseau : listeMoinsBonsReseaux) {
			reseau.randomizerImportances(importanceMin, importanceMax);
		}
	}

	private void appliquerSimilarites() {
		for (Reseau<CompetitionInterReseaux> reseau : listeMoinsBonsReseaux) {
			ArrayList<Lien> listeLiensReseau = reseau.getListeLiens();

			for (Similarite similarite : listeSimilaritesMeilleursReseaux) {
				for (Lien lien : listeLiensReseau) {
					if (lien.getNom().equals(similarite.getNomLien())) {
						lien.getImportance().setValImportance(similarite.getValAAppliquer());
						break;
					}
				}
			}

		}
	}

	private void validerSimilarites() {
		while (isPlusieursSimilariteAvecMemeNom()) {

			boolean trouveSimilaritesIdentiques = false;
			for (int i = 0; i < listeSimilaritesMeilleursReseaux.size() - 1; i++) {
				LinkedList<Similarite> listeSimilaritesARetirer = new LinkedList<>();
				Similarite similarite1 = listeSimilaritesMeilleursReseaux.get(i);

				for (int j = i + 1; j < listeSimilaritesMeilleursReseaux.size(); j++) {
					Similarite similarite2 = listeSimilaritesMeilleursReseaux.get(j);

					if (similarite1.getNomLien().equals(similarite2.getNomLien())) {

						trouveSimilaritesIdentiques = true;
						if (!listeSimilaritesARetirer.contains(similarite1)) {
							listeSimilaritesARetirer.add(similarite1);
						}
						listeSimilaritesARetirer.add(similarite2);

					}

				}

				if (trouveSimilaritesIdentiques) {
					ajusterListeSimilarite(listeSimilaritesARetirer);
					break;
				}

			}

		}
	}

	private void ajusterListeSimilarite(LinkedList<Similarite> listeSimilaritesARetirer) {
		listeSimilaritesMeilleursReseaux.removeAll(listeSimilaritesARetirer);
		double valAAppliquer = 0;
		for (Similarite similarite : listeSimilaritesARetirer) {
			valAAppliquer += similarite.getValAAppliquer();
		}
		valAAppliquer = valAAppliquer / listeSimilaritesARetirer.size();
		Similarite similariteAAjouter = new Similarite(listeSimilaritesARetirer.get(0).getNomLien(), valAAppliquer);
		listeSimilaritesMeilleursReseaux.add(similariteAAjouter);
	}

	private boolean isPlusieursSimilariteAvecMemeNom() {
		boolean so = false;

		for (int i = 0; i < listeSimilaritesMeilleursReseaux.size() - 1; i++) {
			Similarite similarite1 = listeSimilaritesMeilleursReseaux.get(i);

			for (int j = i + 1; j < listeSimilaritesMeilleursReseaux.size(); j++) {
				Similarite similarite2 = listeSimilaritesMeilleursReseaux.get(j);

				if (similarite1.getNomLien().equals(similarite2.getNomLien())) {
					so = true;
					break;
				}

			}

			if (so) {
				break;
			}
		}

		return so;
	}

	private boolean isReseauxIdentiques(ArrayList<Reseau<CompetitionInterReseaux>> listeReseauxEnCompetitions) {
		boolean so = true;

		for (int i = 0; i < listeReseauxEnCompetitions.size() - 1; i++) {

			for (int j = i + 1; j < listeReseauxEnCompetitions.size() - 1; j++) {
				if (isReseauxIdentiquesEntre(listeReseauxEnCompetitions.get(i), listeReseauxEnCompetitions.get(j))) {
					so = false;
					break;
				}
			}

			if (!so) {
				break;
			}
		}

		return so;
	}

	private boolean isReseauxIdentiquesEntre(Reseau<CompetitionInterReseaux> reseau1,
			Reseau<CompetitionInterReseaux> reseau2) {

		return (isReseauxIdentiquesSelonMesuresEntre(reseau1, reseau2)
				&& isReseauxIdentiquesSelonNombreNeuronesParNiveauEntre(reseau1, reseau2)
				&& isReseauxIdentiquesSelonLiensEntre(reseau1, reseau2));

	}

	private boolean isReseauxIdentiquesSelonMesuresEntre(Reseau<CompetitionInterReseaux> reseau1,
			Reseau<CompetitionInterReseaux> reseau2) {

		return (reseau1.getNombreEntrees() != reseau2.getNombreEntrees()
				|| reseau1.getNombreSorties() != reseau2.getNombreSorties()
				|| reseau1.getNombreNiveaux() != reseau2.getNombreNiveaux());

	}

	private boolean isReseauxIdentiquesSelonNombreNeuronesParNiveauEntre(Reseau<CompetitionInterReseaux> reseau1,
			Reseau<CompetitionInterReseaux> reseau2) {

		boolean so = true;

		for (Niveau niveauReseau1 : reseau1.getListeNiveaux()) {

			for (Niveau niveauReseau2 : reseau2.getListeNiveaux()) {
				if (niveauReseau1.getNombreDeNeurones() != niveauReseau2.getNombreDeNeurones()) {
					so = false;
					break;
				}
			}

			if (!so) {
				break;
			}
		}

		return so;

	}

	private boolean isReseauxIdentiquesSelonLiensEntre(Reseau<CompetitionInterReseaux> reseau1,
			Reseau<CompetitionInterReseaux> reseau2) {

		boolean so = true;

		if (reseau1.getListeLiens().size() != reseau2.getListeLiens().size()) {
			so = false;
		} else {
			for (int i = 0; i < reseau1.getListeLiens().size(); i++) {
				if (!reseau1.getListeLiens().get(i).getNom().equals(reseau2.getListeLiens().get(i).getNom())) {
					so = false;
					break;
				}
			}
		}

		return so;

	}

	public ArrayList<Reseau<CompetitionInterReseaux>> getListeReseauxEnCompetitions() {
		return listeReseauxEnCompetitions;
	}

	public ArrayList<Reseau<CompetitionInterReseaux>> getListeMeilleursReseaux() {
		return listeMeilleursReseaux;
	}

	public LinkedList<Similarite> getListeSimilaritesMeilleursReseaux() {
		return listeSimilaritesMeilleursReseaux;
	}

	public ArrayList<Reseau<CompetitionInterReseaux>> getListeMoinsBonsReseaux() {
		return listeMoinsBonsReseaux;
	}

}
