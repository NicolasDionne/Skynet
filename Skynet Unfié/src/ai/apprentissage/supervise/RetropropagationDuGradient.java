package ai.apprentissage.supervise;

import java.util.ArrayList;

import ai.coeur.Lien;
import ai.coeur.Neurone;
import ai.coeur.Niveau;
import ai.coeur.transfers.FonctionTransfers;

public class RetropropagationDuGradient extends LMS {

	public RetropropagationDuGradient() {
		super();
	}

	@Override
	protected void calculerChangementImportance(double[] erreurSortie) {
		this.calculerErreurEtMettreAJourNeuronesSortie(erreurSortie);
	}

	protected void calculerErreurEtMettreAJourNeuronesSortie(double[] erreurSortie) {
		ArrayList<Neurone> neuronesSortie = this.reseau.getNeuronesSorties();
		for (int i = 0; i < neuronesSortie.size(); i++) {
			Neurone neurone = neuronesSortie.get(i);

			if (erreurSortie[i] == 0) {
				neurone.setErreur(0);
				continue;
			}

			FonctionTransfers fonctionTransfers = neurone.getFonctionTransfers();
			double entreeNeurone = neurone.getTotalEntrees();
			double delta = erreurSortie[i] * fonctionTransfers.getDerivee(entreeNeurone);
			neurone.setErreur(delta);

			this.mettreAJourImportancesNeurone(neurone);
		}
	}

	protected void calculerErreurEtMettreAJourNeuronesCachees() {
		ArrayList<Niveau> niveaux = reseau.getListeNiveaux();
		for (int index = niveaux.size() - 2; index > 0; index--) {
			for (Neurone neurone : niveaux.get(index).getListeNeuronesNiveau()) {
				double erreurNeurone = this.calculerErreurNeuroneCachee(neurone);
				neurone.setErreur(erreurNeurone);
				this.mettreAJourImportancesNeurone(neurone);
			}
		}
	}

	protected double calculerErreurNeuroneCachee(Neurone neurone) {
		double sommeDelta = 0;

		for (Lien lien : neurone.getLiensSortie()) {
			double delta = lien.getJusquANeurone().getErreur() * lien.getImportance().valImportance;
			sommeDelta += delta;
		}

		FonctionTransfers fonctionTransfers = neurone.getFonctionTransfers();
		double totalEntrees = neurone.getTotalEntrees();
		double f1 = fonctionTransfers.getDerivee(totalEntrees);
		double erreurNeurone = f1 * sommeDelta;
		return erreurNeurone;

	}

}
