package modele;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import modele.elements.HitBox;
import modele.exceptions.ConstructorException;

public class Obstacle {
	public static final short TAILLE_OBSTACLE = 32;
	public static final short RAYON_OBSTACLE = 16;
	public static final int LONGUEUR_ECRAN = 1068;
	public short taille;
	public double positionX;
	public double positionY;
	public DoubleProperty positionXProperty;
	public DoubleProperty positionYProperty;
	public Image image;
	private ImageView apparence;
	private HitBox hitBox;

	/**
	 * constructeur d'obstacles, prends en compte la hauteur de départ de
	 * l'obstacle et l'image qui lui est assignée, de préférence de 32x32 pixels
	 * 
	 * @param position
	 *            position en Y de départ de l'obstacle
	 * @param image
	 *            l'image assignée à l'obstacle (32x32)
	 * @throws ConstructorException
	 */
	public Obstacle() throws ConstructorException {
		Random r = new Random();
		this.setTaille(TAILLE_OBSTACLE);
		this.setPositionY(r.nextInt(175) + 64);
		this.setPositionX(LONGUEUR_ECRAN + RAYON_OBSTACLE);
		BufferedImage imageTemp = null;
		try {
			imageTemp = ImageIO.read(new File("res/images/obstacle.png"));
		} catch (IOException e) {
			System.out.println("image des obstacles introuvable!");
		}
		image = SwingFXUtils.toFXImage(imageTemp, null);
		this.setImage(image);
		apparence = new ImageView(image);
		positionYProperty = new SimpleDoubleProperty();
		positionYProperty.set(this.positionY);
		positionXProperty = new SimpleDoubleProperty();
		positionXProperty.set(this.positionX);
		apparence.setX(positionXProperty.get());
		apparence.setY(positionYProperty.get());

		hitBox = new HitBox((short) TAILLE_OBSTACLE, (short) TAILLE_OBSTACLE, (float) apparence.getX(),
				(float) apparence.getY());
		hitBox.getCenterPoint().xProperty().bind(positionXProperty);
		hitBox.getCenterPoint().yProperty().bind(positionYProperty);
		
		
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public double getPositionY() {
		return positionY;
	}

	public void setPositionY(double positionY) {
		this.positionY = positionY;
	}

	public DoubleProperty getPositionYProperty() {
		return positionYProperty;
	}

	public void setPositionYProperty(DoubleProperty positionYProperty) {
		this.positionYProperty = positionYProperty;
		setPositionY(this.positionYProperty.doubleValue());
	}

	public short getTaille() {
		return taille;
	}

	public void setTaille(short taille) {
		this.taille = taille;
	}

	public double getPositionX() {
		return positionX;
	}

	public void setPositionX(double position) {
		this.positionX = position;
	}

	public DoubleProperty getPositionXProperty() {
		return positionXProperty;

	}

	public void setPositionXProperty(DoubleProperty positionXProperty) {
		this.positionXProperty = positionXProperty;
		setPositionX(this.positionXProperty.doubleValue());
	}

	public ImageView getApparence() {
		return apparence;
	}

	public void setApparence(ImageView apparence) {
		this.apparence = apparence;
	}

	public HitBox getHitBox() {
		return hitBox;
	}

}
