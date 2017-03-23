package modele.elements;


import modele.exceptions.ConstructorException;
import modele.math.Vector2D;
import utilitaires.MathUtilitaires;

public class MotionPoint extends Point {

    public static final short MAX_VELOCITY = 50;
    public static final short MIN_VELOCITY = -50;
    public static final short MAX_ACCELERATION = 10;
    public static final short MIN_ACCELERATION = -10;

    private Vector2D velocityVector;
    private Vector2D accelerationVector;
    private RotationParameters rotationParameters;

    public MotionPoint(float x, float y, Vector2D velocityVectorP, Vector2D accelerationVectorP, RotationParameters rotationParametersP) throws ConstructorException {
        super(x, y);

        accelerationVector = filterAccelerationVector(accelerationVectorP);
        rotationParameters = filterRotationParameters(rotationParametersP);
        velocityVector = filterVelocityVector(velocityVectorP);


        float velocity = velocityVectorP.norm();
        float acceleration = accelerationVectorP.norm();

        if (!validerAcceleration(acceleration))
            throw new ConstructorException("L'accélération est soit trop petite, soit trop grande. Elle doit se situer entre " + MIN_ACCELERATION + " et " + MAX_ACCELERATION + ".");
        if (!validerVelocite(velocity))
            throw new ConstructorException("La vélocité est soit trop petite, soit trop grande. Elle doit se situer entre 0 et " + MAX_VELOCITY + ".");
    }

    public MotionPoint(float x, float y) throws ConstructorException {
        this(x, y, new Vector2D(), new Vector2D(), new RotationParameters());
    }

    private void adjustVelocity() {
        float newVX = velocityVector.xParam() + accelerationVector.xParam();
        float newVY = velocityVector.yParam() + accelerationVector.yParam();

        float newVel = (float) Math.hypot(newVX, newVY);

        if (validerVelocite(newVel)) {
            velocityVector.setxParam(newVX);
            velocityVector.setyParam(newVY);
        }
    }

    public void move() {
        adjustVelocity();

        this.setX(this.getX() + velocityX());
        this.setY(this.getY() + velocityY());

        // System.out.println(velocityVector);
    }

    public void rotate(Point origin) {
        rotationParameters.updateAngle();
        //System.out.println(rotationParameters.getAngle());
        Point rotatedCenter = MathUtilitaires.rotatePoint(this, origin, rotationParameters.getAngularVelocity());

        this.setX(rotatedCenter.getX());
        this.setY(rotatedCenter.getY());
    }

    public float getVelocity() {
        return velocityVector.getMagnitude();
    }

    public void setVelocity(float velocityP) {
        if (validerVelocite(velocityP))
            velocityVector.setMagnitude(velocityP);
    }

    public String toString() {
        return super.toString() + "\n   " + velocityVector.toString() +
                "\n " + accelerationVector.toString() + "\n " + rotationParameters.toString();
    }

    public double direction() {
        return velocityVector.angle();
    }

    public void setDirection(double angleP) {
        velocityVector.setAngle(MathUtilitaires.reduceAngle(angleP));
    }

    public float velocityX() {
        return velocityVector.xParam();
    }

    public float velocityY() {
        return velocityVector.yParam();
    }

    public float acceleration() {
        return accelerationVector.norm();
    }

    public void setAcceleration(float accelerationP) {
        if (validerAcceleration(accelerationP))
            accelerationVector.setMagnitude(accelerationP);
    }

    public double accelerationAngle() {
        return accelerationVector.angle();
    }

    public void setAccelerationAngle(double angle) {
        accelerationVector.setAngle(angle);
    }

    public float accelerationX() {
        return accelerationVector.xParam();
    }

    public float accelerationY() {
        return accelerationVector.yParam();
    }

    public RotationParameters getRotationParameters() {
        return rotationParameters;
    }

    public void setRotationParameters(RotationParameters rotationParametersP) {
        if (validerVelocite(rotationParametersP.getAngularVelocity()) && validerAcceleration(rotationParametersP.getAngularAcceleration()))
            this.rotationParameters = rotationParametersP;
    }

    private boolean validerVelocite(float velocity) {
        return velocity <= MAX_VELOCITY && velocity >= MIN_VELOCITY;
    }

    private boolean validerAcceleration(float acceleration) {
        return acceleration <= MAX_ACCELERATION && acceleration >= MIN_ACCELERATION;
    }

    private Vector2D filterVelocityVector(Vector2D velocityVectorP) {
        return velocityVectorP == null ? new Vector2D(0, 0) : velocityVectorP;
    }

    private Vector2D filterAccelerationVector(Vector2D accelerationVectorP) {
        return accelerationVectorP == null ? new Vector2D(0, 0) : accelerationVectorP;
    }

    private RotationParameters filterRotationParameters(RotationParameters rotationParametersP) throws ConstructorException {
        return rotationParametersP == null ? new RotationParameters() : rotationParametersP;
    }

}
