package utilitaires;


import modele.elements.Point;

import java.awt.*;
import java.awt.geom.Point2D;

public class MathUtilitaires {

    private static final double pi = Math.PI;

    public static float getRandomInRange(float min, float max) {
        return (float) Math.random() * (max - min) + min;
    }

    public static Point rotatePoint(Point toRotate, Point origin, double angle) {

        double x1 = toRotate.getX() - origin.getX();
        double y1 = toRotate.getY() - origin.getY();

        double x2 = x1 * Math.cos(angle) - y1 * Math.sin(angle);
        double y2 = x1 * Math.sin(angle) + y1 * Math.cos(angle);

        toRotate.setX(x2 + origin.getX());
        toRotate.setY(y2 + origin.getY());

        return toRotate;
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
