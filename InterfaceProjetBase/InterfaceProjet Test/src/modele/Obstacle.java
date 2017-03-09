package modele;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;

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
	
/**
 * constructeur d'obstacles, prends en compte la hauteur de départ de l'obstacle et l'image qui lui est assignée, de préférence de 32x32 pixels
 * @param position position en Y de départ de l'obstacle
 * @param image l'image assignée à l'obstacle (32x32)
 */
	public Obstacle(int position, Image image) {
		this.setTaille(TAILLE_OBSTACLE);   
		this.setPositionY(position);
		//TODO mettre la valeur a LONGUEUR_ECRAN+RAYON_OBSTACLE pour que l<obstacle naparaisse pas dans l'ecran
		this.setPositionX(LONGUEUR_ECRAN-100);
		this.setImage(image);
		positionYProperty = new SimpleDoubleProperty();
		positionYProperty.set(this.positionY);
		positionXProperty = new SimpleDoubleProperty();
		positionXProperty.set(this.positionX);
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

	

	
}
