package utilitaires;

import modele.elements.hitbox.Point;;

public class MathUtilitaires {

	private static final double pi = Math.PI;

	public static float getRandomInRange(float min, float max) {
		return (float) Math.random() * (max - min) + min;
	}

	public static Point rotatePoint(Point point, Point point2, double angle) {

		double x1 = point.getX() - point2.getX();
		double y1 = point.getY() - point2.getY();

		double x2 = x1 * Math.cos(angle) - y1 * Math.sin(angle);
		double y2 = x1 * Math.sin(angle) + y1 * Math.cos(angle);

		point.setX(x2 + point2.getX());
		point.setY(y2 + point2.getY());

		return point;
	}

	public static double reduceAngle(double angleP) {

		if (angleP > 0) {
			while (angleP >= pi * 2) {
				angleP -= (pi * 2);
			}
		} else {
			while (angleP < 0) {
				angleP += (pi * 2);
			}
		}

		return round(angleP);
	}

	public static double round(double param) {
		return Math.round(param * 100000) / 100000.0;
	}

	public static float round(float param) {
		return (float) (Math.round(param * 100000) / 100000.0);
	}

}
