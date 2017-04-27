package ai.apprentissage.supervise;

import java.util.ArrayList;

import ai.coeur.Importance;
import ai.coeur.Lien;
import ai.coeur.Neurone;
import ai.coeur.apprentissage.ApprentissageSupervise;

public class LMS extends ApprentissageSupervise {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5421303050095899405L;

	// TODO Javadoc
	public LMS() {
	}

	@Override
	protected void calculerChangementImportance(double[] erreurSortie) {
		ArrayList<Neurone> neuronesSortie = reseau.getNeuronesSorties();
		for (int i = 0; i < neuronesSortie.size(); i++) {
			Neurone neurone = neuronesSortie.get(i);
			neurone.setErreur(erreurSortie[i]);
			this.mettreAJourImportancesNeurone(neurone);
		}
	}

	public void mettreAJourImportancesNeurone(Neurone neurone) {
		double erreurNeuron = neurone.getErreur();

		for (Lien lien : neurone.getLiensEntree()) {
			double entree = lien.getEntree();

			double changementImportance = this.tauxApprentissage * erreurNeuron * entree;

			Importance importance = lien.getImportance();

			if (!this.isEnModeBatch()) {
				importance.changementImportance = changementImportance;
			} else {
				importance.changementImportance += changementImportance;
			}
		}
	}

}
