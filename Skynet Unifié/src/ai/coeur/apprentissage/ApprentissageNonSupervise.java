package ai.coeur.apprentissage;

import java.util.Iterator;

import ai.coeur.donnee.EnsembleDonnees;
import ai.coeur.donnee.LigneEnsembleDonnees;

/**
 * Classe de base pour les règles d'entrainment non-supervisées.
 * <P>
 * Une règle d'entrainement est considérée comme étant non-supervisée lorsque
 * toutes les {@link LigneEnsembleDonnees} de l'{@link EnsembleDonnees} que la
 * règle utilise n'ont pas de sortie prévue.
 */
public abstract class ApprentissageNonSupervise extends ApprentissageIteratif {

	private static final long serialVersionUID = 6713522365035755553L;

	public ApprentissageNonSupervise() {
		super();
	}

	@Override
	public void faireEpochApprentissage(EnsembleDonnees ensembleEntrainement) {
		Iterator<LigneEnsembleDonnees> iterator = ensembleEntrainement.iterator();
		while (iterator.hasNext() && !estArrete()) {
			LigneEnsembleDonnees ligneEnsembleEntrainement = (LigneEnsembleDonnees) iterator.next();
			patternApprentissage(ligneEnsembleEntrainement);
		}
	}

	/**
	 * Entraine le réseau avec la situation spécifique de l'élément
	 * d'entrainement.
	 * 
	 * @param elementEntrainement
	 *            <code>LigneEnsembleDonnees</code>, l'élément d'entrainement.
	 */
	protected void patternApprentissage(LigneEnsembleDonnees elementEntrainement) {
		double[] entrees = elementEntrainement.getEntrees();
		this.reseau.setValEntree(entrees);
		this.reseau.calculer();
		this.mettreAJourImportancesReseau();
	}

	/**
	 * Cette méthode est utilisée pour mettre à jour l'importance des liens du
	 * réseau.
	 */
	abstract protected void mettreAJourImportancesReseau();

}
