package modele.math;

import utilitaires.AddressString;
import utilitaires.MathUtilitaires;

/**
 *
 * @author Bénange Breton
 *
 *         Classe Vecteur bloqué à 2 dimensions
 *
 */
public class Vector2D extends Vector implements AddressString {

	private double angle;
	private float magnitude;

	public Vector2D() {
		super(2);
		this.lockDimensions();
	}

	public Vector2D(float xParam, float yParam) {
		this();

		this.set(0, xParam);
		this.set(1, yParam);

		setPolarFromCoordinates();
	}

	public Vector2D(float magnitude, double angle) {
		this();

		setAngle(angle);
		setMagnitude(magnitude);

		setCoordinatesFromPolar();
	}

	public float getMagnitude() {
		return magnitude;
	}

	public void setMagnitude(float magnitude) {
		this.magnitude = magnitude;
		setCoordinatesFromPolar();
	}

	public double angle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = MathUtilitaires.reduceAngle(angle);
		setCoordinatesFromPolar();
	}

	public float xParam() {
		return this.get(0);
	}

	public void setxParam(float xParam) {
		this.set(0, xParam);
		setPolarFromCoordinates();
	}

	public float yParam() {
		return this.get(1);
	}

	public void setyParam(float yParam) {
		this.set(1, yParam);
		setPolarFromCoordinates();
	}

	private void setCoordinatesFromPolar() {
		this.set(0, (float) MathUtilitaires.round(magnitude * Math.cos(angle)));
		this.set(1, (float) MathUtilitaires.round(magnitude * Math.sin(angle)));
	}

	private void setPolarFromCoordinates() {
		angle = Math.atan2(this.get(1), this.get(0));
		magnitude = this.norm();
	}

	/**
	 * @return l'addresse ainsi que tous les paramètres du vecteur, autant sous
	 *         forme polaire que composée
	 */
	public String toString() {
		return address() + "   Magnitude : " + this.norm() + "   Angle : " + angle + " radians ("
				+ (angle / (Math.PI * 2)) + " du cercle).    Paramètres : [" + xParam() + ", " + yParam() + "]";
	}

}
