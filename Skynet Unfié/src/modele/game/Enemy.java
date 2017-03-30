package modele.game;


import modele.elements.HitBox;
import modele.exceptions.ConstructorException;

public class Enemy extends GameObject {

    public static final short ENEMY_DIM = 40;

    public Enemy() throws ConstructorException {
        super(ENEMY_DIM);
    }

    public Enemy(HitBox hb) throws ConstructorException {
        super(hb);
    }
}
