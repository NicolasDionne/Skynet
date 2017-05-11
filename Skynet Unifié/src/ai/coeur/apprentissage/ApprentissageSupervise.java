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

/**
 * Classe de base pour toutes les règles d'apprentissage supervisées.
 * <code>ApprentissageSupervise</code> hérite
 * d'<code>ApprentissageIteratif</code>.
 * 
 * @see ApprentissageIteratif
 */
public abstract class ApprentissageSupervise extends ApprentissageIteratif {

	private static final long serialVersionUID = -1418951059208713829L;

	/**
	 * Erreur total du réseau lors de l'époch précédente.
	 */
	private double erreurEpochPrecedente;

	/**
	 * L'erreur maximale qu'un réseau peut avoir (condition pour arréter
	 * l'apprentissage).
	 */
	private double erreurMax = 0.01;

	/**
	 * Condition d'arret: l'entrainement cesse si l'erreur totale du réseau est
	 * plus petite que {@link #limiteIterationsChangementErreur}.
	 * 
	 */
	private double erreurIterationChangementMin = Double.POSITIVE_INFINITY;

	/**
	 * Le nombre maximal d'itérations où {@link #erreurEpochPrecedente} est plus
	 * petite que {@link #erreurIterationChangementMin}.
	 */
	private int limiteIterationsChangementErreur = Integer.MAX_VALUE;

	/**
	 * Le nombre d'itérations où {@link #erreurEpochPrecedente} est plus petite
	 * que {@link #erreurIterationChangementMin}.
	 */
	private int compteurIterationChangementErreur;

	/**
	 * Détermine si la mise à jour de l'importance des liens est faite en mode
	 * batch.
	 */
	private boolean modeBatch = false;
	private FonctionErreur fonctionErreur;

	/**
	 * Crée une nouvelle instance de la règle d'apprentissage supervisé.
	 */
	public ApprentissageSupervise() {
		super();
		this.fonctionErreur = new ErreurQuadratiqueMoyenne();
		this.conditionsArret.add(new ArretMaxErreur(this));
	}

	/**
	 * Entraine le réseau pour la liste voulue selon la liste pour
	 * l'apprentissage et l'erreur maximale possible. Paramétrise
	 * {@link #erreurMax} puis lance {@link #apprendre(EnsembleDonnees)}, avec
	 * <code>ensembleEntrainement</code> comme paramètre.
	 * 
	 * @param ensembleEntrainement
	 *            <code>EnsembleDonnees</code>, la liste des situations pour
	 *            l'apprentissage.
	 * @param erreurMax
	 *            <code>double</code>, la valeur maximale d'erreur comme
	 *            condition d'arrêt.
	 */
	public void apprendre(EnsembleDonnees ensembleEntrainement, double erreurMax) {
		this.erreurMax = erreurMax;
		this.apprendre(ensembleEntrainement);
	}

	/**
	 * Fait la même chose que {@link #apprendre(EnsembleDonnees, double)}, mais
	 * en mettant un nombre maximal d'itérations.
	 * 
	 * @param ensembleEntrainement
	 *            <code>EnsembleDonnees</code>, la liste des situations pour
	 *            l'apprentissage.
	 * @param erreurMax
	 *            <code>double</code>, la valeur maximale d'erreur comme
	 *            condition d'arrêt.
	 * @param iterationsMax
	 *            <code>int<code>, le nombre maximal d'itérations possibles.
	 */
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

	/**
	 * Entraine le réseau selon la situation donnée.
	 * 
	 * @param elementEntrainement
	 *            <code>LigneEnsembleDonnees</code>, la situation spécifique
	 *            contenant une situation d'entrées possible et la sortie
	 *            attendue pour cette situation d'entrées.
	 */
	protected void patternApprentissage(LigneEnsembleDonnees elementEntrainement) {
		double[] entrees = elementEntrainement.getEntrees();
		this.reseau.setValEntree(entrees);
		this.reseau.calculer();
		double[] sorties = this.reseau.getSortie();
		double[] sortiesDesirees = elementEntrainement.getSortiesDesirees();
		double[] erreurPattern = fonctionErreur.calculerPatternErreur(sorties, sortiesDesirees);
		this.calculerChangementImportance(erreurPattern);

		if (!modeBatch) {
			appliquerChangementsImportances();
		}

	}

	/**
	 * Met à jour les <code>Importance</code> du réseau en mode batch.
	 * <P>
	 * Cela utilise le changement d'importance enregistré dans
	 * {@link Importance#changementImportance}. C'est exécuté après chaque epoch
	 * d'apprentissage, mais seulement si l'apprentissage est fait en mode
	 * batch.
	 * 
	 * @see #faireEpochApprentissage(EnsembleDonnees)
	 */
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

	/**
	 * Calcule le changement d'importace à appliquer au <code>Reseau</code>
	 * selon l'erreur de sortie de chaque <code>LigneEnsembleDonnees</code>.
	 * 
	 * @param erreurSortie
	 *            <code>double[]</code> le tableau contenant l'erreur de chaque
	 *            ligne.
	 */
	abstract protected void calculerChangementImportance(double[] erreurSortie);

}
