package ai.coeur.apprentissage;

import java.util.Iterator;

import ai.coeur.donnee.EnsembleDonnees;
import ai.coeur.donnee.LigneEnsembleDonnees;

public abstract class ApprentissageNonSupervise extends ApprentissageIteratif {
	private static final long serialVersionUID = 6713522365035755553L;

	// TODO Javadoc
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

	protected void patternApprentissage(LigneEnsembleDonnees elementEntrainement) {
		double[] entrees = elementEntrainement.getEntrees();
		this.reseau.setValEntree(entrees);
		this.reseau.calculer();
		this.mettreAJourImportancesReseau();
	}

	abstract protected void mettreAJourImportancesReseau();

}
