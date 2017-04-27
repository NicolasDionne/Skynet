package modele.game.game_objects;

import java.util.ArrayList;

import ai.apprentissage.nonsupervise.CompetitionInterReseaux;
import ai.coeur.Neurone;
import ai.coeur.Reseau;
import controleur.Controleur;
import modele.elements.hitbox.HitBox;
import modele.elements.hitbox.MotionPoint;
import modele.elements.ai_related.VisionGrid;

public class Player extends GameObject {

	public static final short PLAYER_DIM = 20;
	public static final short VELOCITY_PLAYER = 4;

	private VisionGrid vGrid;

	public Player(GameObjectType pType, HitBox hb) {
		super(hb);

		MotionPoint origin = new MotionPoint(0, 0);
		getHitBox().setOrigin(origin);
		getHitBox().setCanHitOthers(true);
		setObjectType(pType);

		vGrid = new VisionGrid(hb);
	}

	public VisionGrid getvGrid() {
		return vGrid;
	}

	public void changeDirection(int i) {

		if (i >= 1)
			getHitBox().getCenterPoint().setVelocityY(-VELOCITY_PLAYER);
		else if (i <= -1)
			getHitBox().getCenterPoint().setVelocityY(VELOCITY_PLAYER);
		else
			getHitBox().getCenterPoint().setVelocityY(0);

		checkObjectBeyondEdges();
	}

	public boolean checkObjectBeyondEdges() {

		HitBox hb = getHitBox();
		MotionPoint c = hb.getCenterPoint();
		short halfLength = Player.PLAYER_DIM / 2;

		boolean edgeBot = c.getY() + halfLength + c.velocityY() > Controleur.PLANCHER;
		boolean edgeTop = c.getY() - halfLength + c.velocityY() < Controleur.PLAFOND;

		if (edgeBot || edgeTop) {
			c.setAcceleration(0);
			c.setVelocity(0);
		}
		return false;
	}

}
