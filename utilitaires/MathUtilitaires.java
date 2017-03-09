package utilitaires;


import modele.elements.Point;

public class MathUtilitaires {

    private static final double pi = Math.PI;

    public static Point rotatePoint(Point toRotate, Point origin, double angle) {

        float xFinal = (float) round(origin.getX() + ((toRotate.getX() - origin.getX()) * Math.cos(angle)) - ((toRotate.getY() - origin.getY()) * Math.sin(angle)));
        float yFinal = (float) round(origin.getY() + ((toRotate.getX() - origin.getX()) * Math.sin(angle)) + ((toRotate.getY() - origin.getY()) * Math.cos(angle)));

        System.out.println(Math.hypot((xFinal - origin.getX()), (yFinal - origin.getY())));

        //TODO Vérifier rigoureusement si le code fonctionne bien pour des points en mouvement, et corriger sinon.

        return new Point(xFinal, yFinal);
    }

    public static double reduceAngle(double angleP) {

        if (angleP >= 0) {
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

    public static void main(String[] args) {
        System.out.println(reduceAngle(3.0 / 4.0 * Math.PI));
        System.out.println(reduceAngle(11.0 / 4.0 * Math.PI));
        System.out.println(reduceAngle(reduceAngle(19.0 / 4.0 * Math.PI)));
        System.out.println(reduceAngle(-5.0 / 4.0 * Math.PI));
        System.out.println(reduceAngle(-13.0 / 4.0 * Math.PI));
        System.out.println(reduceAngle(reduceAngle(-21.0 / 4.0 * Math.PI)));
    }

}
