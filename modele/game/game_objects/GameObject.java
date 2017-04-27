package modele.game.game_objects;


import javafx.scene.paint.Color;
import modele.elements.hitbox.HitBox;

public abstract class GameObject {

    public static final int maxw = 1500;
    public static final int maxh = 600;

    public static final short MAX_DIM = 40;
    public static final short MIN_DIM = 10;

    private HitBox hitBox;
    protected GameObjectType gameObType = GameObjectType.VOID;

    public GameObject(HitBox hb) {
        if (hb != null) {
            hitBox = hb;
            hitBox.setWidth(filterWidth(hb.getWidth()));
            hitBox.setHeight(filterHeigth(hb.getHeight()));
        }
    }

    protected void setObjectType(GameObjectType gameObType) {
        this.gameObType = gameObType;
    }

    public GameObjectType getObjectType() {
        return gameObType;
    }

    public Color getColor() {
        return gameObType.getColor();
    }

    public abstract boolean checkObjectBeyondEdges();

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
