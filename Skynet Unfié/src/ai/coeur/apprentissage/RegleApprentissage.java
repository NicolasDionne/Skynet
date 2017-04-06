package ai.coeur.apprentissage;

import ai.coeur.Reseau;
import ai.coeur.donnee.EnsembleDonnees;

public abstract class RegleApprentissage {

	protected Reseau<?> reseau;

	private EnsembleDonnees ensembleEntrainement;

	private volatile boolean arreterApprentissage = false;

	public RegleApprentissage() {

	}

	public EnsembleDonnees getEnsembleEntrainement() {
		return ensembleEntrainement;
	}

	public void setEnsembleEntrainement(EnsembleDonnees ensembleEntrainement) {
		this.ensembleEntrainement = ensembleEntrainement;
	}

	public Reseau<?> getReseau() {
		return reseau;
	}

	public void setReseau(Reseau<?> reseau) {
		this.reseau = reseau;
	}

	protected void lorsDemarer() {
		this.arreterApprentissage = false;
	}

	protected void lorsArreter() {

	}

	public synchronized void arreterApprentissage() {
		this.arreterApprentissage = true;
	}

	public synchronized boolean estArrete() {
		return this.arreterApprentissage;
	}

	abstract public void apprendre(EnsembleDonnees ensembleEntrainement);
}
