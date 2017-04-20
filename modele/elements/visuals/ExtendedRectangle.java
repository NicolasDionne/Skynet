package modele.elements.visuals;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import modele.elements.hitbox.HitBox;
import modele.game.game_objects.GameObject;

public class ExtendedRectangle extends Rectangle {

    public static final Color DEFAULT_COLOR = Color.ROSYBROWN;

    private HitBox hb;
    GameObject gOb;

    public ExtendedRectangle(HitBox hb) {
        super();
        if (hb != null) {

            this.hb = hb;

            xProperty().bind(hb.getCenterPoint().xProperty().subtract(hb.getWidth() / 2.0));
            yProperty().bind(hb.getCenterPoint().yProperty().subtract(hb.getHeight() / 2.0));
            rotateProperty().bind(hb.getSelfRotationParameters().angleProperty().multiply(360 / 2.0 / Math.PI));

            setWidth(hb.getWidth());
            setHeight(hb.getHeight());
            setFill(DEFAULT_COLOR);
        }
    }


    public ExtendedRectangle(GameObject gameObject) {
        this(gameObject.getHitBox());
        setFill(gameObject.getColor());
        this.gOb = gameObject;
    }

    public HitBox getHitBox() {
        return hb;
    }

    public GameObject getGameObject() {
        return gOb;
    }
}
