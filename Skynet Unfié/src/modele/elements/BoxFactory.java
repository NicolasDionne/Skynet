package modele.elements;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.*;

public class BoxFactory {

    public Rectangle getInstance(HitBox hb) {

        Rectangle r = new Rectangle(hb.getWidth(), hb.getHeight());

        r.xProperty().bind(hb.getCenterPoint().xProperty().subtract(hb.getWidth() / 2.0));
        r.yProperty().bind(hb.getCenterPoint().yProperty().subtract(hb.getHeight() / 2.0));

        r.rotateProperty().bind(hb.getSelfRotationParameters().angleProperty().multiply(360 / 2.0 / Math.PI));

        return r;

    }


}
