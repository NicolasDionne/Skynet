package ai.apprentissage.nonsupervise.competitionInter;

import java.io.Serializable;
import java.util.ArrayList;

import ai.apprentissage.nonsupervise.CompetitionInter;
import ai.apprentissage.nonsupervise.competitionInter.competitionInterLiens.GetValModification;
import ai.coeur.Lien;
import ai.coeur.Reseau;
import utilitaires.QuickSort;

public class CompetitionInterLiens extends CompetitionInter implements Serializable {

	private static final long serialVersionUID = -7304761752225772866L;

	private transient double valModification;
	private GetValModification getValModification = new GetValModification();

	/**
	 * @param listeReseauxEnCompetitions
	 */
	public CompetitionInterLiens(ArrayList<Reseau> listeReseauxEnCompetitions, double valModification) {
		super(listeReseauxEnCompetitions);
		this.listeMeilleursReseaux.add(listeReseauxEnCompetitions.get(0));
		this.valModification = valModification;
	}

	@Override
	protected void mettreAJourImportancesReseau() {
		this.valModification = 5;
		appliquerImportanceMeilleurReseau();
		appliquerModificationImportance();
	}

	@Override
	protected void avantEpoch() {
		classerReseaux();
		trouverMeilleurReseau();
		listerMoinsBonsReseaux();
	}

	@Override
	protected void apresEpoch() {

	}

	protected void classerReseaux() {
		listeReseauxEnCompetitions = (ArrayList<Reseau>) QuickSort.sort(listeReseauxEnCompetitions);
	}

	protected void trouverMeilleurReseau() {

		listeMeilleursReseaux.set(0, this.listeReseauxEnCompetitions.get(0));
		listerMoinsBonsReseaux();
	}

	private void listerMoinsBonsReseaux() {
		for (int i = 1; i < listeReseauxEnCompetitions.size(); i++) {
			listeMoinsBonsReseaux.add(listeReseauxEnCompetitions.get(i));
		}
	}

	protected void appliquerImportanceMeilleurReseau() {
		Reseau meilleurReseau = listeMeilleursReseaux.get(0);
		for (Reseau reseau : listeMoinsBonsReseaux) {
			for (Lien lienMeilleurReseau : (ArrayList<Lien>) (meilleurReseau.getListeLiens())) {
				for (Lien lien : (ArrayList<Lien>) reseau.getListeLiens()) {
					if (lienMeilleurReseau.getNom().equals(lien.getNom())) {
						lien.setImportance(lienMeilleurReseau.getImportance());
					} else {
						break;
					}
				}
			}
		}
	}

	protected void appliquerModificationImportance() {
		for (Reseau<CompetitionInterLiens> reseau : listeMoinsBonsReseaux) {
			for (Lien lien : reseau.getListeLiens()) {
				lien.getImportance().setImportanceAleatoire(lien.getImportance().getValImportance() - valModification,
						lien.getImportance().getValImportance() + valModification);
				if (lien.getImportance().getValImportance() > importanceMax) {
					lien.getImportance().setValImportance(
							importanceMax - (lien.getImportance().getValImportance() - importanceMax));
				} else if (lien.getImportance().getValImportance() < importanceMin) {
					lien.getImportance().setValImportance(
							importanceMin - (lien.getImportance().getValImportance() - importanceMin));
				}
			}
		}
	}

	public double getValModification() {
		return valModification;
	}

	public void setValModification(double valModification) {
		this.valModification = valModification;
	}

	public ArrayList<Reseau> getListeReseauxEnCompetitions() {
		return listeReseauxEnCompetitions;
	}

}
