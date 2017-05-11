package modele.elements.hitbox;

import modele.math.Vector2D;
import utilitaires.AddressString;
import utilitaires.MathUtilitaires;

import java.awt.geom.Line2D;

public class HitBox implements AddressString {

	// TODO Tester rigoureusement la combinaison de mouvement linéaire et
	// rotationnel.

	public static final short MAX_WIDTH = 1000;
	public static final short MAX_HEIGHT = 400;
	public static final short MIN_WIDTH = 2;
	public static final short MIN_HEIGHT = 2;

	private short width;
	private short height;
	private RotationParameters selfRotationParameters;
	private MotionPoint centerPoint;
	private MotionPoint origin;
	private boolean canHitOthers = false;
	private boolean isInCollision;

	public boolean isInCollision() {
		return isInCollision;
	}

	/**
	 * Constructeur principal, crée une HitBox avec les paramètres voulous
	 *
	 * @param widthP
	 *            la largeur de la HitBox
	 * @param heightP
	 *            la hauteur de la HitBox
	 * @param centerPointP
	 *            le point central
	 * @param selfRotationParametersP
	 *            les paramètres de rotation autour de soi, si on en veut
	 */
	public HitBox(short widthP, short heightP, MotionPoint centerPointP, RotationParameters selfRotationParametersP) {

		setHeight(heightP);
		setWidth(widthP);
		this.selfRotationParameters = selfRotationParametersP;

		this.centerPoint = centerPointP == null
				? new MotionPoint(0, 0, new Vector2D(0, 0), new Vector2D(0, 0), new RotationParameters())
				: centerPointP;

	}

	public HitBox(short width, short height) {
		this(width, height, new MotionPoint(0, 0, new Vector2D(0, 0), new Vector2D(0, 0), new RotationParameters()),
				new RotationParameters());
	}

	public HitBox(short width, short height, float x, float y) {
		this(width, height, new MotionPoint(x, y, new Vector2D(0, 0), new Vector2D(0, 0), new RotationParameters()),
				new RotationParameters());
	}

	/**
	 * Méthode qui permet de représenter les quatres lignes qui forment la
	 * HitBox
	 *
	 * @return un array de 4 objets Line2D : les 4 lignes qui délimitent le
	 *         périmètre de la HitBox
	 */
	private Line2D[] getLinesOfPerimeter() {

		double angleOfRotation = selfRotationParameters.getAngle();
		float x = width / 2;
		float y = height / 2;

		Point topRight = MathUtilitaires.rotatePoint(new Point(x, y), new Point(), angleOfRotation);
		Point topLeft = MathUtilitaires.rotatePoint(new Point(-x, y), new Point(), angleOfRotation);
		Point botLeft = MathUtilitaires.rotatePoint(new Point(-x, -y), new Point(), angleOfRotation);
		Point botRight = MathUtilitaires.rotatePoint(new Point(x, -y), new Point(), angleOfRotation);

		Line2D l1 = new Line2D.Double(centerPoint.getX() + topRight.getX(), centerPoint.getY() + topRight.getY(),
				centerPoint.getX() + botRight.getX(), centerPoint.getY() + botRight.getY());
		Line2D l2 = new Line2D.Double(centerPoint.getX() + botRight.getX(), centerPoint.getY() + botRight.getY(),
				centerPoint.getX() + botLeft.getX(), centerPoint.getY() + botLeft.getY());
		Line2D l3 = new Line2D.Double(centerPoint.getX() + botLeft.getX(), centerPoint.getY() + botLeft.getY(),
				centerPoint.getX() + topLeft.getX(), centerPoint.getY() + topLeft.getY());
		Line2D l4 = new Line2D.Double(centerPoint.getX() + topLeft.getX(), centerPoint.getY() + topLeft.getY(),
				centerPoint.getX() + topRight.getX(), centerPoint.getY() + topRight.getY());

		Line2D[] lines = { l1, l2, l3, l4 };

		return lines;
	}

	/**
	 * Compare cette HitBox avec une autre et regarde si il y a une collision
	 * entre les deux
	 *
	 * @param box
	 *            la HitBox avec laquelle on vérifie la collision
	 * @return true si il y a collision
	 */
	public boolean checkCollision(HitBox box) {
		boolean intersects = false;

		// Si les deux Box ne peuvent "toucher" les autres, on ignore les
		// collisions entièrement.
		if (this.hitsOthers() || box.hitsOthers()) {

			Line2D[] thislines = this.getLinesOfPerimeter();
			Line2D[] boxlines = box.getLinesOfPerimeter();
			int l = thislines.length;
			int k = boxlines.length;

			for (Line2D thisline : thislines) {
				for (Line2D boxline : boxlines) {
					if (thisline.intersectsLine(boxline)) {
						intersects = true;
						break;
					}
				}
			}
		}
		isInCollision = intersects;
		return intersects;
	}

	/**
	 * Fait tourner la HitBox sur elle même
	 */
	public void rotateSelf() {
		selfRotationParameters.updateAngle();
	}

	/**
	 * Déplace le point d'origine de rotation
	 */
	public void moveOrigin() {
		if (origin != null)
			origin.move();
	}

	public short getWidth() {
		return filterWidth(width);
	}

	public void setWidth(short width) {
		this.width = filterWidth(width);
	}

	public short getHeight() {
		return height;
	}

	public void setHeight(short height) {
		this.height = filterHeight(height);
	}

	/**
	 * Booléen qui détermine si la HitBox peut "toucher" d'autres HitBox; si elle ne peut pas, les collisions retournent directement false
	 * @return true si elle peut entrer en collision
	 */
	public boolean hitsOthers() {
		return canHitOthers;
	}

	public void setCanHitOthers(boolean canHitOthers) {
		this.canHitOthers = canHitOthers;
	}

	public MotionPoint getCenterPoint() {
		return centerPoint;
	}

	public MotionPoint getOrigin() {
		return origin;
	}

	public void setOrigin(MotionPoint originP) {
		this.origin = originP;
	}

	public RotationParameters getSelfRotationParameters() {
		return selfRotationParameters;
	}

	public void setSelfRotationParameters(RotationParameters selfRotationParameters) {
		this.selfRotationParameters = selfRotationParameters;
	}

	private boolean validerDimensions(int w, int h) {
		return w <= MAX_WIDTH && w >= MIN_WIDTH && h <= MAX_HEIGHT && h >= MIN_HEIGHT;
	}

	private short filterHeight(short height) {
		short heightBuffer = height;

		if (height < MIN_HEIGHT)
			heightBuffer = MIN_HEIGHT;
		if (height > MAX_HEIGHT)
			heightBuffer = MAX_HEIGHT;

		return heightBuffer;
	}

	private short filterWidth(short width) {
		short widthBuffer = width;

		if (width < MIN_WIDTH)
			widthBuffer = MIN_WIDTH;
		if (width > MAX_WIDTH)
			widthBuffer = MAX_WIDTH;

		return widthBuffer;
	}

	/**
	 * Retourne l'addresse de la HitBox, puis ses dimensions
	 */
	public String toString() {
		return address() + "    Largeur : " + width + "   Hauteur : " + height;
	}

}
