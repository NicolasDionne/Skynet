package modele.game;

//No Ctrl-F ty

/**
 *
 * @author B�nange Breton
 *
 *	Cette classe contient les diff�rentes difficult�s du jeu et tous les param�tres de jeu que la difficult� affecte.
 */
public enum Difficulty {
	VERY_EASY("Très facile", 1, false, true, 0, 0, 0, 0, true, false, 0.5f, 0),
	EASY("Facile", 1, false, true, 0, 0, 0, 0, true, false, 0.75f, 0),
	MEDIUM("Moyen", 1, false, true, 0.5f, 0.5f, 0, 0, true, true, 1, 0.005f),
	HARD("Difficile", 0.7f, true, false, 0.7f, 0.5f, 0, 0, false, false, 1.2f, 0.01f),
	VERY_HARD("Très difficile", 0.7f, true, false, 0.75f, 0.6f, 0, 0, false, false, 1.5f, 0.05f),
	/*LIKE_REALLY_REALLY_HARD("Bonne chance", 0.6f, true, false, 1, 1, 1, 1, false, false, 1.5f, 0.1f)*/;

	private String name = "";

	private boolean startAcceleration, 				//Si un ennemi a une acc�l�ration
	 				constantVelocity,				//Si tous les ennemis ont la m�me vitesse constante
	 				cycleLanes,						//Si les ennemis apparaissent selon un cycle de rang�es
	 				cycleBackAndForth;				//Si on alterne mont�e/descente dans les rang�es

	private float startVelocityBias,				//La probabilit� qu'un ennemi ait une v�locit� au d�part
					selfRotationBias,				//La probabilit� qu'un ennemi tourne sur lui m�me
					selfRotationStartVelocityBias,	//La probabilit� que cette rotation ait une v�locit� angulaire de d�part
					originRotationBias,				//La probabilit� qu'un ennemi tourne autour d'un origine
					originRotationStarVelocityBias,	//La probabilit� que cette rotation ait une v�locit� angulaire de d�part
					speedScaleFactor,				//Le facteur par lequel toutes les vitesses sont multipli�es
					timeBetweenEnemiesIncrement;	//L'incr�mentation du facteur qui diminue le temps entre les ennemis

	private Difficulty(String name, float startVelocityBias, boolean startAcceleration, boolean constantVelocity, float selfRotationBias,
			float selfRotationStartVelocityBias, float originRotationBias, float originRotationStarVelocityBias,
			boolean cycleLanes, boolean cycleBackAndForth, float speedScaleFactor, float timeBetweenEnemiesIncrement) {

		this.name = name;
		this.startVelocityBias = startVelocityBias;
		this.startAcceleration = startAcceleration;
		this.selfRotationBias = selfRotationBias;
		this.selfRotationStartVelocityBias = selfRotationStartVelocityBias;
		this.originRotationBias = originRotationBias;
		this.originRotationStarVelocityBias = originRotationStarVelocityBias;
		this.cycleLanes = cycleLanes;
		this.cycleBackAndForth = cycleBackAndForth;
		this.speedScaleFactor = speedScaleFactor;
		this.constantVelocity = constantVelocity;
		this.timeBetweenEnemiesIncrement = timeBetweenEnemiesIncrement;

	}

	public String toString() {
		return name;
	}

	public float getStartVelocityBias() {
		return startVelocityBias;
	}

	public boolean isStartAcceleration() {
		return startAcceleration;
	}

	public float getSelfRotationBias() {
		return selfRotationBias;
	}

	public float getSelfRotationStartVelocityBias() {
		return selfRotationStartVelocityBias;
	}

	public float getOriginRotationBias() {
		return originRotationBias;
	}

	public float getOriginRotationStarVelocityBias() {
		return originRotationStarVelocityBias;
	}

	public boolean doesCycleLanes() {
		return cycleLanes;
	}

	public boolean doesCyclesBackAndForth(){
		return cycleBackAndForth;
	}

	public float getSpeedScaleFactor() {
		return speedScaleFactor;
	}

	public boolean isConstantVelocity() {
		return constantVelocity;
	}

	public float getTimeBetweenEnemiesIncrement() {
		return timeBetweenEnemiesIncrement;
	}

}
