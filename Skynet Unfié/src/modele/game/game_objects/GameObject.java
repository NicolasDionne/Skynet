package modele.game.game_objects;


import modele.elements.HitBox;
import modele.elements.MotionPoint;

public abstract class GameObject {

    public static final int maxw = 1500;
    public static final int maxh = 600;

    public static final short MAX_DIM = 40;
    public static final short MIN_DIM = 10;

    private short dimension;
    private HitBox hitBox;

    public GameObject(HitBox hb)  {
        if (hb != null) {
            hitBox = hb;
        }
    }

    public GameObject(short dim) {
        dimension = filtrerDim(dim);
        hitBox = new HitBox(dimension, dimension);
        MotionPoint origin = new MotionPoint(0, 0);
        hitBox.setOrigin(origin);
    }

    public abstract boolean checkObjectBeyondEdges();

    public HitBox getHitBox() {
        return hitBox;
    }

    public void setHitBox(HitBox hitBox) {
        this.hitBox = hitBox;
    }

    public short getDimension() {
        return dimension;
    }

    public void setDimension(short dimension) {
        this.dimension = dimension;
    }

    private short filtrerDim(short dim) {
        short newDim = dim;

        if (dim > MAX_DIM)
            newDim = MAX_DIM;
        if (dim < MIN_DIM)
            newDim = MIN_DIM;
        return newDim;

    }
}
