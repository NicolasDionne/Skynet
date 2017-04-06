package ai.coeur.apprentissage;

import java.util.ArrayList;

import ai.coeur.apprentissage.arret.ArretMaxIterations;
import ai.coeur.apprentissage.arret.ConditionArret;
import ai.coeur.donnee.EnsembleDonnees;

public abstract class ApprentissageIteratif extends RegleApprentissage {
	// TODO la classe
	protected double tauxApprentissage = 0.1;

	protected int iterationCourante = 0;

	private int iterationsMax = Integer.MAX_VALUE;

	private boolean iterationsLimitees = false;

	protected ArrayList<ConditionArret> conditionsArret;

	private volatile boolean apprentissageArrete = false;

	public ApprentissageIteratif() {
		super();
		this.conditionsArret = new ArrayList<>();
	}

	public double getTauxApprentissage() {
		return tauxApprentissage;
	}

	public void setTauxApprentissage(double tauxApprentissage) {
		this.tauxApprentissage = tauxApprentissage;
	}

	public int getIterationsMax() {
		return iterationsMax;
	}

	public void setIterationsMax(int iterationsMax) {
		if (0 < iterationsMax) {
			this.iterationsMax = iterationsMax;
			this.iterationsLimitees = true;
		}
	}

	public boolean isIterationsLimitees() {
		return iterationsLimitees;
	}

	public int getIterationCourante() {
		return iterationCourante;
	}

	public boolean isApprentissageArrete() {
		return apprentissageArrete;
	}

	public void pauser() {
		this.apprentissageArrete = true;
	}

	public void resumer() {
		this.apprentissageArrete = false;
		synchronized (this) {
			this.notify();
		}
	}

	@Override
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
			
			if(this.apprentissageArrete){
				
			}
		}
	}

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

	public void apprendre(EnsembleDonnees ensembleEntrainement, int iterationsMax) {
		this.setIterationsMax(iterationsMax);
		this.apprendre(ensembleEntrainement);
	}

	public void faireUneIterationApprentissage(EnsembleDonnees ensembleEntrainement) {
		avantEpoch();
		faireEpochApprentissage(ensembleEntrainement);
		apresEpoch();
	}

	abstract public void faireEpochApprentissage(EnsembleDonnees ensembleEntrainement);

}
