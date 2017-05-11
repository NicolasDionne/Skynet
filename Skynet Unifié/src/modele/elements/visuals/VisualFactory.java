package modele.elements.visuals;

import modele.elements.hitbox.HitBox;
import modele.game.game_objects.GameObject;

/**
 *
 * @author Bénange Breton
 *
 *         Crée des objets de transition entre le modèle et le contrôleur
 */

public class VisualFactory {

	public ExtendedRectangle getInstance(HitBox hb) {
		ExtendedRectangle r = new ExtendedRectangle(hb);
		return r;
	}

	public ExtendedImageView getInstance(HitBox hb, String url) {
		ExtendedImageView iv = new ExtendedImageView(hb, url);
		return iv;
	}

	public ExtendedRectangle getInstance(GameObject gameObject) {
		ExtendedRectangle r = new ExtendedRectangle(gameObject);
		return r;
	}

	public ExtendedImageView getInstance(GameObject gameObject, String url) {
		ExtendedImageView iv = new ExtendedImageView(gameObject, url);
		return iv;
	}

}
