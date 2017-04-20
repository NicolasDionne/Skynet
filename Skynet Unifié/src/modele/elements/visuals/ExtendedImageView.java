package modele.elements.visuals;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import modele.elements.hitbox.HitBox;
import modele.game.game_objects.GameObject;
import modele.game.game_objects.Player;

public class ExtendedImageView extends ImageView {

    HitBox hb;
    GameObject gOb;

    public ExtendedImageView(HitBox hb, String url) {
        super();
        if (hb != null) {
            try {

                this.hb = hb;

                xProperty().bind(hb.getCenterPoint().xProperty().subtract(hb.getWidth() / 2.0));
                yProperty().bind(hb.getCenterPoint().yProperty().subtract(hb.getHeight() / 2.0));
                rotateProperty().bind(hb.getSelfRotationParameters().angleProperty().multiply(360 / 2.0 / Math.PI));

                setImage(new Image(url));

            } catch (IllegalArgumentException iae) {
                setImage(null);
            }
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
