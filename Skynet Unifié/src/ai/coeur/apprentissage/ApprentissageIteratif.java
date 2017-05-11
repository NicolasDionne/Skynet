package ai.coeur.apprentissage;

import java.util.ArrayList;

import ai.coeur.apprentissage.arret.ArretMaxIterations;
import ai.coeur.apprentissage.arret.ConditionArret;
import ai.coeur.donnee.EnsembleDonnees;

/**
 * Classe de base pour toutes les règles d'apprentissage itératives.
 * <P>
 * Cette classe hérite de <code>RegleApprentissage</code>.
 * 
 * @see RegleApprentissage
 */
public abstract class ApprentissageIteratif extends RegleApprentissage {

	private static final long serialVersionUID = 4942413204553309627L;

	/**
	 * Le paramètre du taux d'apprentissage.
	 */
	protected double tauxApprentissage = 0.1;

	/**
	 * Le numéro de l'itération en cours. C'est numéro de la génération du
	 * réseau de neurones.
	 */
	protected int iterationCourante = 1;

	/**
	 * Le nombre maximal d'itérations possibles, et donc celui de la dernière
	 * génération à laquelle l'apprentissage s'arrête si
	 * {@link iterationsLimitees} égale <code>true</code>.
	 */
	private int iterationsMax = Integer.MAX_VALUE;

	/**
	 * Détermine si l'apprentissage s'arrête après une certaine itération. Si
	 * <code>true</code>, l'apprentissage s'arrête lorsque
	 * {@link #iterationCourante} atteint la valeur juste après celle de
	 * {@link #iterationsMax}, sinon, l'apprentissage ne s'arrête pas.
	 */
	private boolean iterationsLimitees = false;

	/**
	 * La liste des conditions d'arrêt pour la règle d'apprentissage.
	 */
	protected ArrayList<ConditionArret> conditionsArret;

	/**
	 * Si l'apprentissage est arrêté.
	 */
	private volatile boolean apprentissageArrete = false;

	/**
	 * Constructeur de cette règle d'apprentissage.
	 */
	public ApprentissageIteratif() {
		super();
		this.conditionsArret = new ArrayList<>();
	}

	/**
	 * Retourne le taux d'apprentissage.
	 * 
	 * @return <code>double</code>, le taux d'apprentissage de la règle.
	 */
	public double getTauxApprentissage() {
		return tauxApprentissage;
	}

	/**
	 * Définit le taux d'apprentissage de la règle.
	 * 
	 * @param tauxApprentissage
	 *            <code>double</code>, le nouveau taux d'apprentissage de la
	 *            règle.
	 */
	public void setTauxApprentissage(double tauxApprentissage) {
		this.tauxApprentissage = tauxApprentissage;
	}

	/**
	 * Retourne le nombre maximal d'itération de la règle d'apprentissage.
	 * 
	 * @return <code>double</code>, le nombre maximal d'itérations de la règle.
	 */
	public int getIterationsMax() {
		return iterationsMax;
	}

	/**
	 * Définit le nombre maximal d'itérations de la règle.
	 * 
	 * @param iterationsMax
	 *            <code>double<code>, le nombre maximal d'itérations de la
	 *            règle.
	 */
	public void setIterationsMax(int iterationsMax) {
		if (0 < iterationsMax) {
			this.iterationsMax = iterationsMax;
			this.iterationsLimitees = true;
		}
	}

	/**
	 * Retourne si le nombre d'itérations de la règle est limité. Si
	 * <code>true</code>, l'apprentissage cessera lorsque
	 * {@link #iterationCourante} attendra une valeur supérieure à celle de
	 * {@link #iterationsMax}, sinon <code>false</code>.
	 * <P>
	 * Si c'est <code>false</code>, l'apprentissage ne va jamais s'arrêter s'il
	 * n'y a pas au moins une autre règle dans {@link #conditionsArret}.
	 * 
	 * @return <code>boolean</code>, <code>true</code> si le nombre d'itérations
	 *         est limité, sinon <code>false</code>.
	 */
	public boolean isIterationsLimitees() {
		return iterationsLimitees;
	}

	/**
	 * Retourne le numéro de l'itération, et donc de la génération courante.
	 * 
	 * @return <code>int</code>, le numéro de l'itération courante.
	 */
	public int getIterationCourante() {
		return iterationCourante;
	}

	/**
	 * Retourne si l'apprentissage est arrêté.
	 * 
	 * @return <code>boolean</code>, <code>true</code> si l'apprentissage est
	 *         arrêté, sinon <code>false</code>.
	 */
	public boolean isApprentissageArrete() {
		return apprentissageArrete;
	}

	/**
	 * Met l'apprentissage en pause.
	 */
	public void pauser() {
		this.apprentissageArrete = true;
	}

	/**
	 * Continue l'apprentissage là où il s'était arrêté.
	 */
	public void resumer() {
		this.apprentissageArrete = false;
		synchronized (this) {
			this.notify();
		}
	}

	@Override
	/**
	 * Utilisée lorsque l'apprentissage démare avant la première epoch.
	 */
	protected void lorsDemarer() {
		super.lorsDemarer();

		if (this.isIterationsLimitees()) {
			this.conditionsArret.add(new ArretMaxIterations(this));
		}
		this.iterationCourante = 0;
	}

	protected void avantEpoch() {
	}

	protected void apresEpoch() {
	}

	@Override
	final public void apprendre(EnsembleDonnees ensembleEntrainement) {
		setEnsembleEntrainement(ensembleEntrainement);
		lorsDemarer();

		while (!isApprentissageArrete()) {
			avantEpoch();
			faireEpochApprentissage(ensembleEntrainement);
			this.iterationCourante++;
			apresEpoch();

			if (aAtteindConditionArret()) {
				arreterApprentissage();
			} else if (!iterationsLimitees && (iterationCourante == Integer.MAX_VALUE)) {
				this.iterationCourante = 1;
			}

			if (this.apprentissageArrete) {
				synchronized (this) {
					while (this.apprentissageArrete) {
						try {
							this.wait();
						} catch (Exception e) {
							System.out.println("y'a eu une erreur lors du wait, mais on s'en fout");
						}
					}
				}
			}
		}
		lorsArreter();
	}

	/**
	 * Vérifie si l'apprentissage a rempli une de ses conditions d'arrêt. Lance
	 * la méthode {@link ConditionArret#estAtteint()} de toutes les conditions.
	 * 
	 * @return <code>boolean</code>, <code>true</code> si au moins une condition
	 *         d'arret est remplie.
	 */
	protected boolean aAtteindConditionArret() {
		boolean reponse = false;

		for (ConditionArret conditionArret : conditionsArret) {
			if (conditionArret.estAtteint()) {
				reponse = true;
				break;
			}
		}

		return reponse;

	}

	/**
	 * Lance l'apprentissage du <code>Reseau</code> en lui assignant
	 * préalablement un nombre maximum d'itérations.
	 * 
	 * @param ensembleEntrainement
	 *            <code>EnsembleDonnees</code>, la liste pour l'apprentissage à
	 *            utiliser.
	 * @param iterationsMax
	 *            <code>int</code>, le nombre maximal d'itérations.
	 */
	public void apprendre(EnsembleDonnees ensembleEntrainement, int iterationsMax) {
		this.setIterationsMax(iterationsMax);
		this.apprendre(ensembleEntrainement);
	}

	/**
	 * Fait une itération d'apprentissage selon la liste pour l'apprentissage.
	 * Cette méthode exécute, dans l'ordre indiqué {@link #avantEpoch()},
	 * {@link #faireEpochApprentissage(EnsembleDonnees)} puis
	 * {@link #apresEpoch()}.
	 * 
	 * @param ensembleEntrainement
	 *            <code>EnsembleDonnees</code>, la liste à utiliser pour
	 *            l'apprentissage.
	 */
	public void faireUneIterationApprentissage(EnsembleDonnees ensembleEntrainement) {
		avantEpoch();
		faireEpochApprentissage(ensembleEntrainement);
		apresEpoch();
	}

	/**
	 * Fait une itération d'apprentissage pour une règle ne nécessitant pas
	 * d'<code>EnsembleDonnee</code>.
	 */
	public void faireUneIterationApprentissage() {
	}

	/**
	 * Overrider cette méthode pour faire une epoch d'apprentissage. Une epoch
	 * d'apprentissage passe dans tout le l'<code>ensembleEntrainement</code>.
	 * 
	 * @param ensembleEntrainement
	 *            <code>EnsembleDonnees</code>, la liste pour l'apprentissage
	 *            voulu.
	 */
	abstract public void faireEpochApprentissage(EnsembleDonnees ensembleEntrainement);

}
