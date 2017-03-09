package modele.elements;


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import utilitaires.MathUtilitaires;

/**
 * Classe qui permet d'ajouter des paramètres de rotation à un objet; c'est à dire, un angle de rotation, une vélocité angulaire, et une accélération angulaire.
 */

public class RotationParameters {

    public static final short MAX_ANGULAR_VEL = 6;
    public static final short MIN_ANGULAR_VEL = -6;
    public static final short MAX_ANGULAR_ACCELERATION = 2;
    public static final short MIN_ANGULAR_ACCELERATION = -2;

    private float angularVelocity;
    private float angularAcceleration;
    private DoubleProperty angle;

    public RotationParameters(float velocityP, float accelerationP, double angleP) {

        setAngularVelocity(velocityP);
        setAngularAcceleration(accelerationP);
        angle = new SimpleDoubleProperty();
        setAngle(angleP);

    }

    public RotationParameters() {
        this(0, 0, 0);
    }

    private void updateAngularVelocity() {
        setAngularVelocity(angularVelocity + angularAcceleration);
    }

    public void updateAngle() {
        updateAngularVelocity();
        angle.set(MathUtilitaires.reduceAngle(angle.get() + angularVelocity));
    }

    public float getAngularVelocity() {
        return angularVelocity;
    }

    public float getAngularAcceleration() {
        return angularAcceleration;
    }

    public void setAngularVelocity(float angularVelocity) {
        this.angularVelocity = filtrerVelociteAngulaire(angularVelocity);
    }

    public void setAngularAcceleration(float angularAcceleration) {
        this.angularAcceleration = filtrerAccelerationAngulaire(angularAcceleration);
    }

    public double getAngle() {
        return angle.get();
    }

    public DoubleProperty angleProperty() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle.set(MathUtilitaires.reduceAngle(angle));
    }

    private float filtrerAccelerationAngulaire(float angularAcceleration) {
        float newAngAcc = angularAcceleration;

        if (angularAcceleration > MAX_ANGULAR_ACCELERATION)
            newAngAcc = MAX_ANGULAR_ACCELERATION;
        if (angularAcceleration < MIN_ANGULAR_ACCELERATION)
            newAngAcc = MIN_ANGULAR_ACCELERATION;

        return newAngAcc;
    }

    private float filtrerVelociteAngulaire(float angularVelocity) {
        float newAngVel = angularVelocity;

        if (angularVelocity > MAX_ANGULAR_VEL)
            newAngVel = MAX_ANGULAR_VEL;
        if (angularVelocity < MIN_ANGULAR_VEL)
            newAngVel = MIN_ANGULAR_VEL;


        return newAngVel;

    }
}
