package modele.game;


import modele.elements.HitBox;
import modele.elements.MotionPoint;
import modele.exceptions.ConstructorException;

public class Enemy extends GameObject {

    public static final short ENEMY_DIM = 40;

    public Enemy() throws ConstructorException {
        super(ENEMY_DIM);
    }

    public Enemy(HitBox hb) throws ConstructorException {
        super(hb);
    }

    public boolean checkObjectBeyondEdges() {

        boolean isBeyondEdge = false;

        HitBox hb = getHitBox();
        MotionPoint c = hb.getCenterPoint();
        short halfDiagLength = (short) Math.ceil(Math.hypot(hb.getWidth() / 2.0, hb.getHeight() / 2.0));

        boolean edge1 = c.getX() > maxw + halfDiagLength && c.velocityX() > 0;
        boolean edge2 = c.getY() > maxh + halfDiagLength && c.velocityY() > 0;
        boolean edge3 = c.getX() < -halfDiagLength && c.velocityX() < 0;
        boolean edge4 = c.getY() < -halfDiagLength && c.velocityY() < 0;

        if (edge1 || edge2 || edge3 || edge4) {
            isBeyondEdge = true;
        }

        return isBeyondEdge;
    }
}
