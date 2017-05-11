package ai.coeur.apprentissage.arret;

import ai.coeur.apprentissage.ApprentissageSupervise;

public class ArretMaxErreur implements ConditionArret {
	private final ApprentissageSupervise regleApprentissage;

	public ArretMaxErreur(ApprentissageSupervise regleApprentissage) {
		this.regleApprentissage = regleApprentissage;
	}

	@Override
	public boolean estAtteint() {
		boolean reponse = false;
		if (regleApprentissage.getTotalErreurReseau() < regleApprentissage.getErreurMax()) {
			reponse = true;
		}
		return reponse;
	}

}
