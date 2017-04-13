package modele.game;

import controleur.Controleur;
import modele.elements.HitBox;
import modele.elements.MotionPoint;
import modele.elements.RotationParameters;
import modele.game.game_objects.Bias;
import modele.game.game_objects.Enemy;
import modele.game.game_objects.Spawn;
import modele.math.Vector2D;
import utilitaires.MathUtilitaires;

public class HitBoxGenerator implements Spawn<HitBox>, Bias {

	public static final float SELF_ROTATION_BIAS = 0.3f;
	public static final float SELF_ROTATION_START_VELOCITY_BIAS = 0.3f;

	public static final float ORIGIN_ROTATION_BIAS = 0f;
	public static final float ORIGIN_ROTATION_START_VELOCITY_BIAS = 0.3f;

	public static final float START_VELOCITY_BIAS = 0.5f;

	@Override
	public HitBox spawn(short size) {

		HitBox hb = new HitBox(size, size,
				new MotionPoint(Controleur.EDGE + size, newHeight(), new Vector2D(newVelocity(), 0),
						new Vector2D(newAcceleration(), 0), generateOriginRotationParams()),
				generateSelfRotationParams());

		hb.setOrigin(generateOrigin(hb));

		return hb;
	}

	private float newHeight() {
		return randRange(Controleur.PLAFOND + Enemy.ENEMY_DIM / 2, Controleur.PLANCHER - Enemy.ENEMY_DIM / 2);
	}

	private float newAcceleration() {
		return (-1) * randRange(0, MotionPoint.MAX_ACCELERATION);
	}

	private float newVelocity() {
		float vel = 0;

		if (testBias(START_VELOCITY_BIAS)) {
			vel = (-1) * randRange(0, MotionPoint.MAX_VELOCITY);
		}
		return vel;
	}

	private MotionPoint generateOrigin(HitBox hb) {
		// float space = (float) (Math.abs((hb.getCenterPoint().getY() -
		// Controleur.MID_HEIGHT)));

		MotionPoint o = new MotionPoint((float) hb.getCenterPoint().getX() - 50, Controleur.MID_HEIGHT);
		o.setAccelerationX(hb.getCenterPoint().accelerationX());
		o.setVelocityX(hb.getCenterPoint().velocityX());

		return o;
	}

	private RotationParameters generateSelfRotationParams() {

		RotationParameters rParams = new RotationParameters();
		float acc = 0;
		float vel = 0;

		if (testBias(SELF_ROTATION_BIAS)) {

			acc = randRange(RotationParameters.MIN_ANGULAR_ACCELERATION, RotationParameters.MAX_ANGULAR_ACCELERATION);

			if (testBias(SELF_ROTATION_START_VELOCITY_BIAS)) {
				vel = randRange(RotationParameters.MIN_ANGULAR_VEL, RotationParameters.MAX_ANGULAR_VEL);

				rParams.setAngularVelocity(vel);
			}
			rParams.setAngularAcceleration(acc);
		}

		return rParams;
	}

	private RotationParameters generateOriginRotationParams() {

		RotationParameters rParams = new RotationParameters();
		float acc = 0;
		float vel = 0;

		if (testBias(ORIGIN_ROTATION_BIAS)) {

			acc = randRange(RotationParameters.MIN_ANGULAR_ACCELERATION, RotationParameters.MAX_ANGULAR_ACCELERATION);

			if (testBias(ORIGIN_ROTATION_START_VELOCITY_BIAS)) {
				vel = randRange(RotationParameters.MIN_ANGULAR_VEL, RotationParameters.MAX_ANGULAR_VEL);

				rParams.setAngularVelocity(vel);
			}
			rParams.setAngularAcceleration(acc);
		}

		// return new RotationParameters();
		return rParams;
	}

	private float randRange(float min, float max) {
		float rand=MathUtilitaires.getRandomInRange(min, max);
		return rand;
	}
}
