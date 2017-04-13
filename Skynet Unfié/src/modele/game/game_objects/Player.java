package modele.game.game_objects;

import controleur.Controleur;
import modele.elements.HitBox;
import modele.elements.MotionPoint;
import modele.elements.VisionGrid;

public class Player extends GameObject {

    public static final short PLAYER_DIM = 20;
    public static final short VELOCITY_PLAYER = 4;
    private VisionGrid vGrid;
    private PlayerType pType;

    public Player(PlayerType pType, HitBox hb) {
        super(hb);
        MotionPoint origin = new MotionPoint(0, 0);
        getHitBox().setOrigin(origin);
        getHitBox().setCanHitOthers(true);

       // if (pType == PlayerType.AI)
            vGrid = new VisionGrid(getHitBox());
        this.pType = pType;
    }


    public PlayerType getPlayerType() {
        return pType;
    }

    public VisionGrid getvGrid() {
        return vGrid;
    }

    public void changeDirection(int i) {
        if (i >= 1)
            getHitBox().getCenterPoint().setVelocityY(-VELOCITY_PLAYER);
        else if (i <= -1)
            getHitBox().getCenterPoint().setVelocityY(VELOCITY_PLAYER);
        else
            getHitBox().getCenterPoint().setVelocityY(0);

        checkObjectBeyondEdges();
    }

    public boolean checkObjectBeyondEdges() {

        HitBox hb = getHitBox();
        MotionPoint c = hb.getCenterPoint();
        short halfLength = Player.PLAYER_DIM / 2;

        boolean edgeBot = c.getY() + halfLength + c.velocityY() > Controleur.PLANCHER;
        boolean edgeTop = c.getY() - halfLength + c.velocityY() < Controleur.PLAFOND;

        if (edgeBot || edgeTop) {
            c.setAcceleration(0);
            c.setVelocity(0);
        }
        return false;
    }

}
