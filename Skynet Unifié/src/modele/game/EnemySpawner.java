package modele.game;

import controleur.Controleur;
import modele.elements.hitbox.HitBox;
import modele.elements.hitbox.MotionPoint;
import modele.elements.hitbox.RotationParameters;
import modele.game.game_objects.Enemy;
import modele.math.Vector2D;
import utilitaires.MathUtilitaires;

/**
 *
 * @author Bénange Breton
 *
 *         Classe qui permet d'instancier des ennemis qui possèdent les
 *         paramètres voulus par la difficulté.
 */
public class EnemySpawner implements Bias {

	private static final int CONSTANT_VELOCITY = 5;

	static final public int MIN_HEIGHT = Controleur.PLAFOND;
	static final public int MAX_HEIGHT = Controleur.PLANCHER;

	private Difficulty difficulty;

	private int nbLanes;
	private int laneIndex;
	private int[] laneHeights;
	boolean descending = true;

	public EnemySpawner(int nbLanes, Difficulty diff) {
		this.nbLanes = filterNbLanes(nbLanes);
		this.difficulty = diff;

		laneHeights = new int[nbLanes];
		for (int i = 0; i < this.nbLanes; i++) {
			laneHeights[i] = ((MAX_HEIGHT - MIN_HEIGHT) / this.nbLanes) * (i + 1) + MIN_HEIGHT;
		}
	}

	/**
	 * Selon un paramètre de taille, donne une HitBox avec des paramètres de
	 * mouvement/rotation déterminés selon la difficulté
	 *
	 * @param size
	 *            la taille de la HitBox
	 * @return une HitBox avec des paramètres pré-générés
	 *
	 */
	public HitBox spawn(short size) {

		HitBox hb = new HitBox(size, size,
				new MotionPoint(Controleur.EDGE + size, startHeight(), new Vector2D(startVelocity(), 0),
						new Vector2D(startAcceleration(), 0), startOriginRotationParameters()),
				startSelfRotationParameters());

		hb.setOrigin(startOrigin(hb));

		return hb;
	}

	/**
	 * Calcule la coordonnée Y de départ
	 *
	 * @return un float qui sera la coordonnée Y de la HitBox
	 */
	private float startHeight() {
		int startHeight;

		if (difficulty.doesCycleLanes()) {
			startHeight = laneHeights[laneIndex];
			changeLane(difficulty.doesCyclesBackAndForth());
		} else
			startHeight = (int) MathUtilitaires.getRandomInRange(MIN_HEIGHT, MAX_HEIGHT);

		return startHeight;
	}

	/**
	 * Méthode qui permet de changer de rangée si les hauteurs de spawn sont
	 * fixes.
	 *
	 * @param cycleBackAndForth
	 *            si on alterne montée/descente ou non
	 */
	private void changeLane(boolean cycleBackAndForth) {
		if (cycleBackAndForth) {

			if (descending) {
				laneIndex++;
				if (laneIndex == nbLanes - 1)
					descending = false;
			} else {
				laneIndex--;
				if (laneIndex == 0)
					descending = true;
			}
		} else
			laneIndex = (laneIndex + 1) % nbLanes;
	}

	/**
	 * Calcule l'accélération de départ
	 *
	 * @return un float qui est l'accélération de départ
	 */
	private float startAcceleration() {
		return difficulty.isStartAcceleration()
				? (-1) * randRange(MotionPoint.MIN_ACCELERATION, MotionPoint.MAX_ACCELERATION) : 0;
	}

	/**
	 * Calcule la vélocité de départ
	 *
	 * @return un float qui est la vélocité de départ
	 */
	private float startVelocity() {
		float vel = CONSTANT_VELOCITY;

		if (!difficulty.isConstantVelocity() && testBias(difficulty.getStartVelocityBias())) {
			vel = randRange(MotionPoint.MIN_VELOCITY, MotionPoint.MAX_VELOCITY);
		}
		return (-1) * vel * difficulty.getSpeedScaleFactor();
	}

	/**
	 * Donne à la HitBox un origine de rotation prédéfini
	 *
	 * @param hb
	 *            la HitBox par rapport à laquelle on définit les coordonnées de
	 *            l'origine
	 * @return un point d'origine
	 */
	private MotionPoint startOrigin(HitBox hb) {

		MotionPoint o = new MotionPoint((float) hb.getCenterPoint().getX(), Controleur.MID_HEIGHT);
		o.setAccelerationX(hb.getCenterPoint().accelerationX());
		o.setVelocityX(hb.getCenterPoint().velocityX());

		return o;
	}

	/**
	 * Crée les paramètres de rotation de la HitBox autour de soi même.
	 *
	 * @return les paramètres de rotation
	 */
	private RotationParameters startSelfRotationParameters() {

		RotationParameters rParams = new RotationParameters();

		if (testBias(difficulty.getSelfRotationBias())) {

			float acc = randRange(RotationParameters.MIN_ANGULAR_ACCELERATION,
					RotationParameters.MAX_ANGULAR_ACCELERATION);

			if (testBias(difficulty.getSelfRotationStartVelocityBias())) {
				float vel = randRange(RotationParameters.MIN_ANGULAR_VEL, RotationParameters.MAX_ANGULAR_VEL);

				rParams.setAngularVelocity(vel);
			}
			rParams.setAngularAcceleration(acc);
		}

		return rParams;
	}

	/**
	 * Crée les paramètres de rotation de la HitBox autour d'un origine
	 * prédéfini
	 *
	 * @return les paramètres de rotation
	 */
	private RotationParameters startOriginRotationParameters() {

		RotationParameters rParams = new RotationParameters();
		float acc = 0;
		float vel = 0;

		if (testBias(difficulty.getOriginRotationBias())) {

			acc = randRange(RotationParameters.MIN_ANGULAR_ACCELERATION, RotationParameters.MAX_ANGULAR_ACCELERATION);

			if (testBias(difficulty.getOriginRotationStarVelocityBias())) {
				vel = randRange(RotationParameters.MIN_ANGULAR_VEL, RotationParameters.MAX_ANGULAR_VEL);

				rParams.setAngularVelocity(vel);
			}
			rParams.setAngularAcceleration(acc);
		}

		return rParams;
	}

	private float randRange(float min, float max) {
		return MathUtilitaires.getRandomInRange(min, max);
	}

	private int filterNbLanes(int nbLanes) {
		return nbLanes > 0 ? nbLanes : 1;
	}
}
