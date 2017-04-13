package modele.elements;


import modele.math.Vector2D;
import utilitaires.MathUtilitaires;

public class MotionPoint extends Point {

    public static final short MAX_VELOCITY = 15;
    public static final short MIN_VELOCITY = -15;
    public static final short MAX_ACCELERATION = 2;
    public static final short MIN_ACCELERATION = -2;

    private Vector2D velocityVector;
    private Vector2D accelerationVector;
    private RotationParameters rotationParameters;

    public MotionPoint(float x, float y, Vector2D velocityVectorP, Vector2D accelerationVectorP, RotationParameters rotationParametersP) {
        super(x, y);

        accelerationVector = filterAccelerationVector(accelerationVectorP);
        rotationParameters = filterRotationParameters(rotationParametersP);
        velocityVector = filterVelocityVector(velocityVectorP);

        float velocity = velocityVectorP.norm();
        float acceleration = accelerationVectorP.norm();

    }

    /**
     * Constructeur avec coordonnées en paramètres
     *
     * @param x la coordonnée x
     * @param y la coordonnée y
     */
    public MotionPoint(float x, float y) {
        this(x, y, new Vector2D(), new Vector2D(), new RotationParameters());
    }

    /**
     * Déplace linéairement le point après avoir ajusté sa vélocité.
     */
    public void move() {
        adjustVelocity();

        this.setX(this.getX() + velocityX());
        this.setY(this.getY() + velocityY());

    }

    /**
     * Fait la rotation de ce point autour d'un point d'origine. L'angle de rotation est donné par les paramètres de rotation.
     *
     * @param origin l'origine de rotation.
     */
    public void rotate(Point origin) {
        rotationParameters.updateAngle();

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

    /**
     * Méthode toString(), retourne tous les paramètres du point.
     *
     * @return l'adresse mémoire, le vecteur de vélocité, le vecteur d'accélération et les paramètres de rotation.
     */
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

    public void setAccelerationX(float accelerationX) {
        float xBuffer = accelerationVector.xParam();
        accelerationVector.setxParam(accelerationX);

        if (!validerAcceleration(accelerationVector.getMagnitude())) {
            accelerationVector.setxParam(xBuffer);
        }
    }

    public void setAccelerationY(float accelerationY) {
        float yBuffer = accelerationVector.yParam();
        accelerationVector.setyParam(accelerationY);

        if (!validerAcceleration(accelerationVector.getMagnitude())) {
            accelerationVector.setyParam(yBuffer);
        }
    }

    public void setVelocityX(float velocityX) {
        float xBuffer = velocityVector.xParam();
        velocityVector.setxParam(velocityX);

        if (!validerVelocite(velocityVector.getMagnitude())) {
            velocityVector.setxParam(xBuffer);
        }
    }

    public void setVelocityY(float velocityY) {
        float yBuffer = velocityVector.yParam();
        velocityVector.setyParam(velocityY);

        if (!validerVelocite(velocityVector.getMagnitude())) {
            velocityVector.setyParam(yBuffer);
        }
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

    private RotationParameters filterRotationParameters(RotationParameters rotationParametersP) {
        return rotationParametersP == null ? new RotationParameters() : rotationParametersP;
    }

    /**
     * Ajuste la vélocité x et y selon l'accélération x et y.
     */
    private void adjustVelocity() {
        float newVX = velocityVector.xParam() + accelerationVector.xParam();
        float newVY = velocityVector.yParam() + accelerationVector.yParam();

        float newVel = (float) Math.hypot(newVX, newVY);

        if (validerVelocite(newVel)) {
            velocityVector.setxParam(newVX);
            velocityVector.setyParam(newVY);
        }
    }
}
