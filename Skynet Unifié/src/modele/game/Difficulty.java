package modele.game;

/**
 *
 * @author Bénange Breton
 *
 *	Cette classe contient les différentes difficultés du jeu et tous les paramètres de jeu que la difficulté affecte.
 */
public enum Difficulty {
	VERY_EASY("Très facile", 1, false, true, 0, 0, 0, 0, true, 0.5f, 0),
	EASY("Facile", 1, false, true, 0, 0, 0, 0, true, 0.75f, 0),
	MEDIUM("Moyen", 1, false, false, 0.5f, 0.5f, 0, 0, true, 1, 0.005f),
	HARD("Difficile", 0.7f, true, false, 0.7f, 0.5f, 0, 0, false, 1.2f, 0.01f),
	VERY_HARD("Très difficile", 0.7f, true, false, 0.75f, 0.6f, 0, 0, false, 1.5f, 0.1f),
	LIKE_REALLY_REALLY_HARD("Bonne chance", 1f, true, false, 1, 1, 1, 1, false, 1.5f, 1f);

	private String name = "";
	private boolean startAcceleration,
	 				constantVelocity,
	 				cycleLanes;
	private float startVelocityBias,
					selfRotationBias,
					selfRotationStartVelocityBias,
					originRotationBias,
					originRotationStarVelocityBias,
					speedScaleFactor,
					timeBetweenEnemiesIncrement;

	private Difficulty(String name, float startVelocityBias, boolean startAcceleration, boolean constantVelocity, float selfRotationBias,
			float selfRotationStartVelocityBias, float originRotationBias, float originRotationStarVelocityBias,
			boolean cycleLanes, float speedScaleFactor, float timeBetweenEnemiesIncrement) {
		this.name = name;
		this.startVelocityBias = startVelocityBias;
		this.startAcceleration = startAcceleration;
		this.selfRotationBias = selfRotationBias;
		this.selfRotationStartVelocityBias = selfRotationStartVelocityBias;
		this.originRotationBias = originRotationBias;
		this.originRotationStarVelocityBias = originRotationStarVelocityBias;
		this.cycleLanes = cycleLanes;
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

	public boolean isCycleLanes() {
		return cycleLanes;
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
