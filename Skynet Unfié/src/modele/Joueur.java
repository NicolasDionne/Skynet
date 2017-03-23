package modele;

import java.io.File;
import java.io.InputStream;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Classe contenant les attributs requis pour faire un personnage joueur
 * 
 * @author Nicola Dionne
 *
 */

public class Joueur {
	public static final short HAUTEUR_JOUEUR = 20;
	public static final short LONGUEUR_JOUEUR = 32;
	private static final double HAUTEUR_MAX = -95;
	private static final double BASSEUR_MAX = 79;
	public short hauteur;
	public short longueur;
	public double position;
	public DoubleProperty positionProperty;
	public ImageView apparence;

	/**
	 * constructeur pour les objets joueurs, reçoit sa position initiale en
	 * paramètre
	 * 
	 * @param position
	 */
	public Joueur(int position, ImageView image) {
		this.setHauteur(HAUTEUR_JOUEUR);
		this.setLongueur(LONGUEUR_JOUEUR);
		positionProperty = new SimpleDoubleProperty();
		positionProperty.set(this.position);
		this.setPosition(position);
		this.apparence=image;
	}

	public DoubleProperty getPositionProperty() {
		return positionProperty;
	}

	public void setPositionProperty(DoubleProperty positionProperty) {
		this.positionProperty = positionProperty;
		setPosition(this.positionProperty.doubleValue());
	}

	public short getHauteur() {
		return hauteur;
	}

	public void setHauteur(short hauteur) {
		this.hauteur = hauteur;
	}

	public short getLongueur() {
		return longueur;
	}

	public void setLongueur(short longueur) {
		this.longueur = longueur;
	}

	public double getPosition() {
		return position;
	}

	public void setPosition(double d) {
		if (d >= HAUTEUR_MAX && d <= BASSEUR_MAX) {
			this.position = d;
		}
	}
	public ImageView getApparence(){
		return apparence;
	}

}
