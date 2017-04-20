package ai.coeur.apprentissage.arret;

import ai.coeur.apprentissage.ApprentissageIteratif;

public class ArretMaxIterations implements ConditionArret {
	// TODO Javadoc
	private ApprentissageIteratif regleApprentissage;

	public ArretMaxIterations(ApprentissageIteratif regleApprentissage) {
		this.regleApprentissage = regleApprentissage;
	}

	@Override
	public boolean estAtteint() {
		return (this.regleApprentissage.getIterationCourante() >= this.regleApprentissage.getIterationsMax());
	}

}
