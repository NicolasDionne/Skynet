package modele.elements;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.awt.*;

public class BoxFactory {

    private DoubleProperty width;
    private DoubleProperty height;

    public BoxFactory() {
        width = new SimpleDoubleProperty();
        height = new SimpleDoubleProperty();
        //TODO Établir les limites de la zone factory avec un binding des limites de la scène
    }

    public Rectangle getInstanceBox(HitBox hb) {
        Rectangle r = new Rectangle();
        return r;

        //TODO Finaliser le getInstance
    }


    public double getWidth() {
        return width.get();
    }

    public DoubleProperty widthProperty() {
        return width;
    }

    public void setWidth(double width) {
        this.width.set(width);
    }

    public double getHeight() {
        return height.get();
    }

    public DoubleProperty heightProperty() {
        return height;
    }

    public void setHeight(double height) {
        this.height.set(height);
    }
}
