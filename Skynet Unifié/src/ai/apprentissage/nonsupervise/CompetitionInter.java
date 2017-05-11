package ai.apprentissage.nonsupervise;

import java.io.Serializable;
import java.util.ArrayList;

import ai.coeur.Reseau;
import ai.coeur.apprentissage.ApprentissageNonSupervise;
import utilitaires.QuickSort;
import utilitaires.Sortable;

/**
 * Classe de base pour les règles d'apprentissage fonctionnant entre plusieurs
 * réseaux construits de la même manière (le même nombre d'entrées, de niveaux,
 * de neurones dans chaque niveau (selon l'ordre des niveaux) et de sorties).
 * <P>
 * Les règles d'apprentissage héritant de cette dernière modifient les liens
 * selon ceux du ou des réseaux ayant le mieux performé.
 * <P>
 * Cette classe hérite de <code>ApprentissageNonSupervise<code>.
 * 
 * @see ApprentissageNonSupervise
 */
public abstract class CompetitionInter extends ApprentissageNonSupervise implements Serializable {

	private static final long serialVersionUID = -5743381037817513332L;

	/**
	 * La liste de <code>Reseau</code> qui sont en compétition.
	 */
	protected ArrayList<Reseau> listeReseauxEnCompetitions;

	/**
	 * La liste de <code>Reseau</code> ayant le moins bien performé.
	 */
	protected ArrayList<Reseau> listeMoinsBonsReseaux;

	/**
	 * La liste du ou des <code>Reseau</code> ayant le mieux performé.
	 */
	protected ArrayList<Reseau> listeMeilleursReseaux;

	/**
	 * L'importance minimale possible qu'un lien peut recevoir.
	 */
	public double importanceMin = -100;

	/**
	 * L'importance maximale possible qu'un lien peut recevoir.
	 */
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

	/**
	 * Classe les réseaux selon leur score.
	 * 
	 * @see QuickSort
	 * @see QuickSort#sort(List)
	 * @see Sortable
	 */
	protected void classerReseaux() {
		listeReseauxEnCompetitions = (ArrayList<Reseau>) QuickSort.sort(listeReseauxEnCompetitions);
	}

	/**
	 * Trouve le ou les meilleurs réseaux.
	 */
	protected abstract void trouverMeilleurReseau();

}
