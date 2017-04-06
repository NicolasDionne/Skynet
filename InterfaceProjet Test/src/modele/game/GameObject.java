package modele.game;


import modele.elements.HitBox;
import modele.exceptions.ConstructorException;

public abstract class GameObject {

    public static final short MAX_DIM = 40;
    public static final short MIN_DIM = 10;

    private short dimension;
    private HitBox hitBox;

    public GameObject(HitBox hb) throws ConstructorException {
        if (hb != null)
            hitBox = hb;
        else
            throw new ConstructorException("HitBox = null");
    }

    public GameObject(short dim) throws ConstructorException {
        dimension = filtrerDim(dim);
        hitBox = new HitBox(dimension, dimension);
    }

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