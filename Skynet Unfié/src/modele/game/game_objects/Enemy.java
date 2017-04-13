package modele.game.game_objects;

import modele.elements.HitBox;
import modele.elements.MotionPoint;

public class Enemy extends GameObject {

	public static final short ENEMY_DIM = 32;

	public Enemy() {
		super(ENEMY_DIM);
	}

	public Enemy(HitBox hb) {
		super(hb);
	}

	public boolean checkObjectBeyondEdges() {

		HitBox hb = getHitBox();
		MotionPoint c = hb.getCenterPoint();
		short halfDiagLength = (short) Math.ceil(Math.hypot(hb.getWidth() / 2.0, hb.getHeight() / 2.0));

		boolean isBeyondEdge = c.getX() < -halfDiagLength && c.velocityX() < 0;

		return isBeyondEdge;
	}
}
