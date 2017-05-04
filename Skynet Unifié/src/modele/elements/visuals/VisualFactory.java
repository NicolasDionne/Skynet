package modele.elements.visuals;


import javafx.scene.paint.Color;
import modele.elements.hitbox.HitBox;
import modele.game.game_objects.GameObject;

/*
*
* @author Bénange Breton
*
* Classe de transition entre le modèle et le contrôleur
*/

public class VisualFactory {

    ExtendedRectangle getInstance(HitBox hb) {
        ExtendedRectangle r = new ExtendedRectangle(hb);
        return r;
    }

    ExtendedImageView getInstance(HitBox hb, String url) {
        ExtendedImageView iv = new ExtendedImageView(hb, url);
        return iv;
    }

    ExtendedRectangle getInstance(GameObject gameObject) {
        ExtendedRectangle r = new ExtendedRectangle(gameObject);
        return r;
    }

    ExtendedImageView getInstance(GameObject gameObject, String url) {
        ExtendedImageView iv = new ExtendedImageView(gameObject, url);
        return iv;
    }

}
