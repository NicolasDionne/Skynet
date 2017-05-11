package ai.apprentissage.nonsupervise.competitionInter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

import ai.apprentissage.nonsupervise.CompetitionInter;
import ai.apprentissage.nonsupervise.competitionInter.competitionInterReseau.Similarite;
import ai.coeur.Lien;
import ai.coeur.Niveau;
import ai.coeur.Reseau;
import modele.exceptions.ConstructorException;

public class CompetitionInterReseaux extends CompetitionInter implements Serializable {

	private static final long serialVersionUID = -315153809902585523L;

	private transient LinkedList<Similarite> listeSimilaritesMeilleursReseauxGenerationPrecedente;
	private transient LinkedList<Similarite> listeSimilaritesMeilleursReseaux;

	private int dernierMeilleurScore;

	public double margeErreurAcceptable = 2;

	public CompetitionInterReseaux(ArrayList<Reseau> listeReseauxEnCompetitions) {
		super(listeReseauxEnCompetitions);
		try {
			if (isReseauxIdentiques(listeReseauxEnCompetitions)) {

				this.listeReseauxEnCompetitions = listeReseauxEnCompetitions;
				for (Reseau<CompetitionInterReseaux> reseau : this.listeReseauxEnCompetitions) {
					reseau.setRegleApprentissage(this);
				}

				listeSimilaritesMeilleursReseaux = new LinkedList<>();
				listeSimilaritesMeilleursReseauxGenerationPrecedente = new LinkedList<>();

			} else {
				throw new ConstructorException("les réseaux en compétitions doivent être identiques");
			}

		} catch (ConstructorException e) {

			e.printStackTrace();
		}
	}

	@Override
	protected void mettreAJourImportancesReseau() {
		trouverMeilleurReseau();
		for (int i = 0; i < listeMeilleursReseaux.size() - 1; i++) {
			for (int j = i + 1; j < listeMeilleursReseaux.size(); j++) {
				trouverSimilarites(listeMeilleursReseaux.get(i), listeMeilleursReseaux.get(j));
			}
		}
		validerSimilarites();
		ajusterListeSimilaritesGenerationPrecedente();
	}

	@Override
	protected void avantEpoch() {
		listeMeilleursReseaux.clear();
		listeSimilaritesMeilleursReseauxGenerationPrecedente = (LinkedList<Similarite>) listeSimilaritesMeilleursReseaux
				.clone();
		listeSimilaritesMeilleursReseaux.clear();
		classerReseaux();
	}

	@Override
	protected void apresEpoch() {
		randomizerImportanceReseaux();
		if (isResultatPireQuAvant()) {
			appliquerSimilaritesLorsquePire();
		} else {
			appliquerSimilarites();
		}
		dernierMeilleurScore = listeMeilleursReseaux.get(0).getScore();
		System.out.println("tailleSimilarites: " + listeSimilaritesMeilleursReseaux.size());
		System.out.println("learnigDone");

	}

	protected void trouverMeilleurReseau() {
		for (int i = 0; i < listeReseauxEnCompetitions.size(); i++) {
			if (i < listeReseauxEnCompetitions.size() / 4) {
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

	protected void mettreAJourNomsLiens(ArrayList<Lien> listeLiensReseau) {
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

			for (Similarite similarite : listeSimilaritesMeilleursReseauxGenerationPrecedente) {
				for (Lien lien : listeLiensReseau) {
					if (lien.getNom().equals(similarite.getNomLien())) {
						lien.getImportance().setValImportance(similarite.getValAAppliquer());
						break;
					}

				}
			}

		}
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

	private void ajusterListeSimilaritesGenerationPrecedente() {
		if (!listeSimilaritesMeilleursReseauxGenerationPrecedente.isEmpty()) {
			ArrayList<Similarite> anciennesSimilaritesAAjuster = new ArrayList<>();
			ArrayList<Similarite> similaritesCourantesAAjuster = new ArrayList<>();
			ArrayList<Similarite> anciennesSimilaritesAEnlever = new ArrayList<>();
			ArrayList<Similarite> similaritesCourantesAEnlever = new ArrayList<>();

			for (Similarite ancienneSimilarite : listeSimilaritesMeilleursReseauxGenerationPrecedente) {
				for (Similarite similariteCourante : listeSimilaritesMeilleursReseaux) {
					if (ancienneSimilarite.getNomLien().equals(similariteCourante)) {
						if (Math.abs(ancienneSimilarite.getValAAppliquer()
								- similariteCourante.getValAAppliquer()) <= margeErreurAcceptable) {
							anciennesSimilaritesAAjuster.add(ancienneSimilarite);
							similaritesCourantesAAjuster.add(similariteCourante);
						} else {
							anciennesSimilaritesAEnlever.add(ancienneSimilarite);
							similaritesCourantesAEnlever.add(similariteCourante);
						}
					}
				}
			}
			listeSimilaritesMeilleursReseaux.removeAll(similaritesCourantesAEnlever);
			listeSimilaritesMeilleursReseauxGenerationPrecedente.removeAll(anciennesSimilaritesAEnlever);
			ajusterListeSimilariteCouranteAvecPrecedente(anciennesSimilaritesAAjuster, similaritesCourantesAAjuster);
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

	private void ajusterListeSimilariteCouranteAvecPrecedente(ArrayList<Similarite> ancienneSimilarite,
			ArrayList<Similarite> similariteCourante) {
		if (ancienneSimilarite.size() != similariteCourante.size()) {
			throw new IllegalArgumentException("Ne sont pas identiques");
		} else {
			boolean so = true;
			for (int i = 0; i < ancienneSimilarite.size(); i++) {
				if (!ancienneSimilarite.get(i).getNomLien().equals(similariteCourante.get(i).getNomLien())) {
					so = false;
					break;
				}
			}
			if (!so) {
				throw new IllegalArgumentException("Ne sont pas identiques");
			} else {
				listeSimilaritesMeilleursReseauxGenerationPrecedente.removeAll(ancienneSimilarite);
				listeSimilaritesMeilleursReseaux.removeAll(similariteCourante);
				for (int i = 0; i < ancienneSimilarite.size(); i++) {
					double valAAppliquer = (ancienneSimilarite.get(i).getValAAppliquer()
							+ similariteCourante.get(i).getValAAppliquer()) / 2;
					Similarite similarite = new Similarite(similariteCourante.get(i).getNomLien(), valAAppliquer);
					listeSimilaritesMeilleursReseaux.add(similarite);
				}
			}
		}
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

	private boolean isReseauxIdentiques(ArrayList<Reseau> listeReseauxEnCompetitions) {
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

	public LinkedList<Similarite> getListeSimilaritesMeilleursReseaux() {
		return listeSimilaritesMeilleursReseaux;
	}

	public void setListeSimilaritesMeilleursReseaux(LinkedList<Similarite> listeSimilaritesMeilleursReseaux) {
		this.listeSimilaritesMeilleursReseaux = listeSimilaritesMeilleursReseaux;
	}

	public LinkedList<Similarite> getListeSimilaritesMeilleursReseauxGenerationPrecedente() {
		return listeSimilaritesMeilleursReseauxGenerationPrecedente;
	}

	public void setListeSimilaritesMeilleursReseauxGenerationPrecedente(
			LinkedList<Similarite> listeSimilaritesMeilleursReseauxGenerationPrecedente) {
		this.listeSimilaritesMeilleursReseauxGenerationPrecedente = listeSimilaritesMeilleursReseauxGenerationPrecedente;
	}

	public boolean isResultatPireQuAvant() {
		return getListeMeilleursReseaux().get(0).getScore() < dernierMeilleurScore;
	}

	private void appliquerSimilaritesLorsquePire() {
		long i = 0;
		for (Reseau<CompetitionInterReseaux> reseau : listeMoinsBonsReseaux) {
			ArrayList<Lien> listeLiensReseau = reseau.getListeLiens();

			for (Similarite similarite : listeSimilaritesMeilleursReseauxGenerationPrecedente) {
				for (Lien lien : listeLiensReseau) {
					if (i % 999999 == 0) {
						if (lien.getNom().equals(similarite.getNomLien())) {
							lien.getImportance().setValImportance(similarite.getValAAppliquer());
							break;
						}
					}
					i++;
				}
			}

		}
		long j = 0;
		for (Reseau<CompetitionInterReseaux> reseau : listeMoinsBonsReseaux) {
			ArrayList<Lien> listeLiensReseau = reseau.getListeLiens();

			for (Similarite similarite : listeSimilaritesMeilleursReseaux) {
				for (Lien lien : listeLiensReseau) {
					if (j % 999999 == 0) {
						if (lien.getNom().equals(similarite.getNomLien())) {
							lien.getImportance().setValImportance(similarite.getValAAppliquer());
							break;
						}
					}
					j++;
				}
			}

		}
	}

}
