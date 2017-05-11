package modele.game.game_objects;

import java.util.ArrayList;
import java.util.List;

import controleur.Controleur;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import modele.elements.hitbox.HitBox;
import modele.elements.hitbox.MotionPoint;
import utilitaires.Parametres;
import modele.elements.ai_related.VisionGrid;

/**
 *
 * @author Bénange Breton
 *
 *         Classe qui instancie un joueur
 */
public class Player extends GameObject {

	public static final short PLAYER_DIM = 20;
	public static final short VELOCITY_PLAYER = 4;

	private VisionGrid vGrid;
	private IntegerProperty score;
	protected List<Integer> listeIndexEntrees;

	public Player(GameObjectType pType, HitBox hb, Parametres parametres) {
		super(hb);

		MotionPoint origin = new MotionPoint(0, 0);
		getHitBox().setOrigin(origin);
		getHitBox().setCanHitOthers(true);
		setObjectType(pType);

		vGrid = new VisionGrid(hb, (short) parametres.getValNbLignes(), (short) parametres.getValNbColonnes());
		score = new SimpleIntegerProperty();
		listeIndexEntrees = new ArrayList<>();

	}

	public IntegerProperty scoreProperty() {
		return score;
	}

	public int getScore() {
		return score.get();
	}

	public VisionGrid getvGrid() {
		return vGrid;
	}

	/**
	 * Change la direction du déplacement selon un paramètres
	 *
	 * @param i
	 *            int qui est soit 1 (monter), soit -1 (descendre) ou autre
	 *            (arrêter)
	 */
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

	public List<Integer> getListeIndexEntrees() {
		return listeIndexEntrees;
	}

	public void setListeIndexEntrees(List<Integer> listeEntreesNumeriques) {
		this.listeIndexEntrees = listeEntreesNumeriques;
	}

	public void appliquerIndex() {
	}
}
