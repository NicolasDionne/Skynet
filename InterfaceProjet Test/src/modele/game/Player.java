package modele.game;


import modele.elements.HitBox;
import modele.exceptions.ConstructorException;

public class Player extends GameObject {

    public static final short PLAYER_DIM = 20;

    Player() throws ConstructorException {
        super(PLAYER_DIM);
        getHitBox().setHitsOthers(true);
    }

    Player(HitBox hb) throws ConstructorException {
        super(hb);
        getHitBox().setHitsOthers(true);
    }

}
