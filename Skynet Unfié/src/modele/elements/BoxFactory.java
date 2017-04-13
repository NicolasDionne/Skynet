package modele.elements;

import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;


public class BoxFactory {

    public Rectangle getInstance(HitBox hb) {

        Rectangle r = new Rectangle(hb.getWidth(), hb.getHeight());

        r.xProperty().bind(hb.getCenterPoint().xProperty().subtract(hb.getWidth() / 2.0));
        r.yProperty().bind(hb.getCenterPoint().yProperty().subtract(hb.getHeight() / 2.0));

        r.rotateProperty().bind(hb.getSelfRotationParameters().angleProperty().multiply(360 / 2.0 / Math.PI));

        return r;

    }

    public ImageView getInstanceImage(HitBox hb, String url) {

        ImageView iv = new ImageView(url);

        iv.xProperty().bind(hb.getCenterPoint().xProperty().subtract(hb.getWidth() / 2.0));
        iv.yProperty().bind(hb.getCenterPoint().yProperty().subtract(hb.getHeight() / 2.0));

        iv.rotateProperty().bind(hb.getSelfRotationParameters().angleProperty().multiply(360 / 2.0 / Math.PI));

        return iv;
    }


}
