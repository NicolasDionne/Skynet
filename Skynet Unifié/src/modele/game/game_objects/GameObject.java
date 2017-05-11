package modele.game.game_objects;

import javafx.scene.paint.Color;
import modele.elements.hitbox.HitBox;

/**
 *
 * @author Bénange Breton
 *
 *         Classe abstraite qui génère des objets avec lequel le jeu peut
 *         interagir
 */
public abstract class GameObject {

	public static final int maxw = 1500;
	public static final int maxh = 600;

	public static final short MAX_DIM = 1000;
	public static final short MIN_DIM = 10;

	private HitBox hitBox;
	protected GameObjectType gameObType = GameObjectType.VOID;

	/**
	 * Constructeur d'un GameObject. Lui donne une HitBox qui lui permettra de
	 * se déplacer.
	 *
	 * @param hb
	 *            la HitBox qu'on lui associe
	 *
	 */
	public GameObject(HitBox hb) {
		if (hb != null) {
			hitBox = hb;
			hitBox.setWidth(filterWidth(hb.getWidth()));
			hitBox.setHeight(filterHeigth(hb.getHeight()));
		}
	}

	/**
	 * Permet à l'objet héritant de vérifier efficacement s'il passe au delà des
	 * limites du jeu
	 *
	 * @return true si l'objet est plus loin que les limites
	 */
	public abstract boolean checkObjectBeyondEdges();

	protected void setObjectType(GameObjectType gameObType) {
		this.gameObType = gameObType;
	}

	public GameObjectType getObjectType() {
		return gameObType;
	}

	public Color getColor() {
		return gameObType.getColor();
	}

	public HitBox getHitBox() {
		return hitBox;
	}

	private short filterWidth(short width) {
		short newWidth = width;

		if (newWidth > MAX_DIM)
			newWidth = MAX_DIM;
		if (newWidth < MIN_DIM)
			newWidth = MIN_DIM;

		return newWidth;
	}

	private short filterHeigth(short height) {
		short newHeigth = height;

		if (newHeigth > MAX_DIM)
			newHeigth = MAX_DIM;
		if (newHeigth < MIN_DIM)
			newHeigth = MIN_DIM;

		return newHeigth;
	}
}
