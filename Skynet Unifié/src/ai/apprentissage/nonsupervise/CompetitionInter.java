package ai.apprentissage.nonsupervise;

import java.io.Serializable;
import java.util.ArrayList;

import ai.coeur.Reseau;
import ai.coeur.apprentissage.ApprentissageNonSupervise;

public abstract class CompetitionInter extends ApprentissageNonSupervise implements Serializable {

	private static final long serialVersionUID = -5743381037817513332L;

	/**
	 * La liste de <code>Reseau</code> qui sont en compétition.
	 */
	protected ArrayList<Reseau> listeReseauxEnCompetitions;

	protected ArrayList<Reseau> listeMoinsBonsReseaux;
	protected ArrayList<Reseau> listeMeilleursReseaux;
	public double importanceMin = -100;
	public double importanceMax = 100;

	/**
	 * @param listeReseauxEnCompetitions
	 */
	public CompetitionInter(ArrayList<Reseau> listeReseauxEnCompetitions) {
		super();
		this.listeReseauxEnCompetitions = listeReseauxEnCompetitions;
		this.listeMeilleursReseaux = new ArrayList<>();
		this.listeMoinsBonsReseaux = new ArrayList<>();
	}

	@Override
	public void faireUneIterationApprentissage() {
		this.iterationCourante++;
		avantEpoch();
		mettreAJourImportancesReseau();
		apresEpoch();
	}

	public ArrayList<Reseau> getListeReseauxEnCompetitions() {
		return listeReseauxEnCompetitions;
	}

	public ArrayList<Reseau> getListeMeilleursReseaux() {
		return listeMeilleursReseaux;
	}

	public ArrayList<Reseau> getListeMoinsBonsReseaux() {
		return listeMoinsBonsReseaux;
	}

	protected abstract void classerReseaux();

	protected abstract void trouverMeilleurReseau();

}
