package ai.coeur.apprentissage;

import java.util.ArrayList;
import java.util.Iterator;

import ai.coeur.Importance;
import ai.coeur.Lien;
import ai.coeur.Neurone;
import ai.coeur.Niveau;
import ai.coeur.apprentissage.arret.ArretMaxErreur;
import ai.coeur.apprentissage.erreur.ErreurQuadratiqueMoyenne;
import ai.coeur.apprentissage.erreur.FonctionErreur;
import ai.coeur.donnee.EnsembleDonnees;
import ai.coeur.donnee.LigneEnsembleDonnees;

public abstract class ApprentissageSupervise extends ApprentissageIteratif {
	// TODO Javadoc
	private double erreurEpochPrecedente;
	private double erreurMax = 0.01;
	private double erreurIterationChangementMin = Double.POSITIVE_INFINITY;
	private int limiteIterationsChangementErreur = Integer.MAX_VALUE;
	private int compteurIterationChangementErreur;
	private boolean modeBatch = false;
	private FonctionErreur fonctionErreur;

	public ApprentissageSupervise() {
		super();
		this.fonctionErreur = new ErreurQuadratiqueMoyenne();
		this.conditionsArret.add(new ArretMaxErreur(this));
	}

	public void apprendre(EnsembleDonnees ensembleEntrainement, double erreurMax) {
		this.erreurMax = erreurMax;
		this.apprendre(ensembleEntrainement);
	}

	public void apprendre(EnsembleDonnees ensembleEntrainement, double erreurMax, int iterationsMax) {
		this.erreurMax = erreurMax;
		this.setIterationsMax(iterationsMax);
		this.apprendre(ensembleEntrainement);
	}

	@Override
	protected void lorsDemarer() {
		super.lorsDemarer();
		this.erreurIterationChangementMin = 0;
		this.erreurEpochPrecedente = 0;
	}

	@Override
	protected void avantEpoch() {
		this.erreurEpochPrecedente = fonctionErreur.getErreurTotale();
		this.fonctionErreur.reinitialiser();
	}

	@Override
	protected void apresEpoch() {
		double absChangementErreur = Math.abs(erreurEpochPrecedente - fonctionErreur.getErreurTotale());
		if (absChangementErreur <= this.erreurIterationChangementMin) {
			this.compteurIterationChangementErreur++;
		} else {
			this.compteurIterationChangementErreur = 0;
		}

		if (this.modeBatch == true) {
			faireMiseAJourImportancesBatch();
		}
	}

	@Override
	public void faireEpochApprentissage(EnsembleDonnees ensembleEntrainement) {
		Iterator<LigneEnsembleDonnees> iterator = ensembleEntrainement.iterator();
		while (iterator.hasNext() && !estArrete()) {
			LigneEnsembleDonnees ligneEnsembleDonnees = iterator.next();

			this.patternApprentissage(ligneEnsembleDonnees);
		}
	}

	protected void patternApprentissage(LigneEnsembleDonnees elementEntrainement) {
		double[] entrees = elementEntrainement.getEntrees();
		this.reseau.setEntree(entrees);
		this.reseau.calculer();
		double[] sorties = this.reseau.getSortie();
		double[] sortiesDesirees = elementEntrainement.getSortiesDesirees();
		double[] erreurPattern = fonctionErreur.calculerPatternErreur(sorties, sortiesDesirees);
		this.calculerChangementImportance(erreurPattern);

		if (!modeBatch) {
			appliquerChangementsImportances();
		}

	}

	protected void faireMiseAJourImportancesBatch() {
		ArrayList<Niveau> niveaux = this.reseau.getListeNiveaux();

		for (int i = this.reseau.getNombreNiveaux() - 1; i > 0; i--) {
			for (Neurone neurone : niveaux.get(i).getListeNeuronesNiveau()) {
				for (Lien lien : neurone.getLiensEntree()) {
					Importance importance = lien.getImportance();
					importance.valImportance += importance.changementImportance;
					importance.changementImportance = 0;
				}
			}
		}
	}

	public boolean isEnModeBatch() {
		return modeBatch;
	}

	public void setModeBatch(boolean modeBatch) {
		this.modeBatch = modeBatch;
	}

	public double getErreurMax() {
		return erreurMax;
	}

	public void setErreurMax(double erreurMax) {
		this.erreurMax = erreurMax;
	}

	public double getErreurEpochPrecedente() {
		return erreurEpochPrecedente;
	}

	public double getErreurIterationChangementMin() {
		return erreurIterationChangementMin;
	}

	public void setErreurIterationChangementMin(double erreurChangementMin) {
		this.erreurIterationChangementMin = erreurChangementMin;
	}

	public int getLimiteIterationsChangementErreur() {
		return limiteIterationsChangementErreur;
	}

	public void setLimiteIterationsChangementErreur(int limiteIterationsChangementErreur) {
		this.limiteIterationsChangementErreur = limiteIterationsChangementErreur;
	}

	public int getCompteurIterationChangementErreur() {
		return compteurIterationChangementErreur;
	}

	public FonctionErreur getFonctionErreur() {
		return this.fonctionErreur;
	}

	public void setFonctionErreur(FonctionErreur fonctionErreur) {
		this.fonctionErreur = fonctionErreur;
	}

	public double getTotalErreurReseau() {
		return fonctionErreur.getErreurTotale();
	}

	private void appliquerChangementsImportances() {
		ArrayList<Niveau> niveaux = reseau.getListeNiveaux();
		for (int i = reseau.getNombreNiveaux() - 1; i > 0; i--) {
			for (Neurone neurone : niveaux.get(i).getListeNeuronesNiveau()) {
				for (Lien lien : neurone.getLiensEntree()) {
					Importance importance = lien.getImportance();
					importance.valImportance += importance.changementImportance;
					importance.changementImportance = 0;
				}
			}
		}
	}

	abstract protected void calculerChangementImportance(double[] erreurSortie);

}
