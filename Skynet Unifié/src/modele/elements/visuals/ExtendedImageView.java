package modele.elements.visuals;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import modele.elements.hitbox.HitBox;
import modele.game.game_objects.GameObject;
import modele.game.game_objects.Player;

/**
*
* @author Bénange Breton
*
* Classe qui permet de lier une ImageView avec un GameObject, pour des manipulations faciles.
*/
public class ExtendedImageView extends ImageView {

	HitBox hb;
	GameObject gOb;

	public ExtendedImageView(HitBox hb, String styleName) {
		super();
		if (hb != null) {

			this.hb = hb;

			xProperty().bind(hb.getCenterPoint().xProperty().subtract(hb.getWidth() / 2.0));
			yProperty().bind(hb.getCenterPoint().yProperty().subtract(hb.getHeight() / 2.0));
			rotateProperty().bind(hb.getSelfRotationParameters().angleProperty().multiply(360 / 2.0 / Math.PI));

			getStyleClass().set(0, styleName);
		}
	}

	public ExtendedImageView(GameObject gameObject, String url) {
		this(gameObject.getHitBox(), url);
		this.gOb = gameObject;
	}

	public HitBox getHitBox() {
		return hb;
	}

	public GameObject getGameObject() {
		return gOb;
	}

}
