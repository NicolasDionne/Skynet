package modele.game;


import modele.elements.HitBox;
import modele.elements.MotionPoint;
import modele.exceptions.ConstructorException;

public class Player extends GameObject {

    public static final short PLAYER_DIM = 20;
    private static final float ACCELERATION_INCREMENT = 0.3f;

    private double pi = Math.PI;

    public Player() throws ConstructorException {
        super(PLAYER_DIM);
        getHitBox().setHitsOthers(true);
    }

    public Player(HitBox hb) throws ConstructorException {
        super(hb);
        getHitBox().setHitsOthers(true);
    }

    private void accelerationChangeX(float increment) {
        MotionPoint c = getHitBox().getCenterPoint();

        c.setAccelerationX(c.accelerationX() + increment);
    }

    private void accelerationChangeY(float increment) {
        MotionPoint c = getHitBox().getCenterPoint();

        c.setAccelerationY(c.accelerationY() + increment);
    }

    private void brakeX() {
        MotionPoint c = getHitBox().getCenterPoint();
        float vel = c.velocityX();

            c.setVelocityX(vel - ( vel / 10.0f));
            c.setAccelerationX(0);
    }

    private void brakeY() {
        MotionPoint c = getHitBox().getCenterPoint();
        float vel = c.velocityY();

            c.setVelocityY(vel - ( vel / 10.0f));
            c.setAccelerationY(0);

    }

    public void changeDirection(String dir) {
        if (dir == "up") {
            accelerationChangeY(-ACCELERATION_INCREMENT);
            brakeX();
        }
        if (dir == "down") {
            accelerationChangeY(ACCELERATION_INCREMENT);
            brakeX();
        }
        if (dir == "left") {
            accelerationChangeX(-ACCELERATION_INCREMENT);
            brakeY();
        }
        if (dir == "right") {
            accelerationChangeX(ACCELERATION_INCREMENT);
            brakeY();
        }
        checkObjectBeyondEdges();
    }

    public boolean checkObjectBeyondEdges() {

        HitBox hb = getHitBox();
        MotionPoint c = hb.getCenterPoint();
        short halfLength = Player.PLAYER_DIM / 2;

        boolean edge1 = c.getX() + halfLength + c.velocityX() > maxw;
        boolean edge2 = c.getY() + halfLength + c.velocityY() > maxh;
        boolean edge3 = c.getX() - halfLength + c.velocityX() < 0;
        boolean edge4 = c.getY() - halfLength + c.velocityY() < 0;

        if (edge1 || edge2 || edge3 || edge4) {
            c.setAcceleration(0);
            c.setVelocity(0);
        }
        return false;
    }
}
