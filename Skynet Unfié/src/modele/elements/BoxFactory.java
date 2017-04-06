package modele.elements;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.*;

public class BoxFactory {

    public ImageView getInstance(HitBox hb, String url) {
    	
    	Image image;
    	ImageView iv = new ImageView();
    	BufferedImage imageTemp = null;
    	try {
    	   imageTemp = ImageIO.read(new File("res/images/obstacle.png"));
    	} catch (IOException e) {
    		System.out.println("image des obstacles introuvable!");
    	}
    	image = SwingFXUtils.toFXImage(imageTemp, null);
		iv.setImage(image);

        iv.xProperty().bind(hb.getCenterPoint().xProperty().subtract(hb.getWidth() / 2.0));
        iv.yProperty().bind(hb.getCenterPoint().yProperty().subtract(hb.getHeight() / 2.0));

        iv.rotateProperty().bind(hb.getSelfRotationParameters().angleProperty().multiply(360 / 2.0 / Math.PI));

        System.out.println(iv.getImage());
        
        
        return iv;

        
        
    }


}
