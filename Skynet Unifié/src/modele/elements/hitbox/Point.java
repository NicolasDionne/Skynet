package modele.elements.hitbox;

//Classe générique de point; similaire à celle de java.awt, mais elle prend des floats en paramètre, ce qui est utile pour les calculs.

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import utilitaires.AddressString;

public class Point implements AddressString {

    private DoubleProperty x;
    private DoubleProperty y;

    public Point(float a, float b) {
        x = new SimpleDoubleProperty();
        y = new SimpleDoubleProperty();

        setX(a);
        setY(b);
    }

    public Point() {
        this(0, 0);
    }

    public double getX() {
        return x.get();
    }

    public DoubleProperty xProperty() {
        return x;
    }

    public void setX(double x) {
        this.x.set(x);
    }

    public double getY() {
        return y.get();
    }

    public DoubleProperty yProperty() {
        return y;
    }

    public void setY(double y) {
        this.y.set(y);
    }

    public String toString() {
        return address() + "(" + x.get() + ", " + y.get() + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Point point = (Point) obj;
        if (x.get() != point.getX()) return false;

        return y.get() == point.getY();
    }
}