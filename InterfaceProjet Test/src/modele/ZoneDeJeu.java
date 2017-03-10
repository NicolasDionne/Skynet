package modele;

import java.util.ListIterator;

import javafx.animation.AnimationTimer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

public class ZoneDeJeu {
	public ObservableList<Obstacle> obstacles = FXCollections.observableArrayList();
	private DoubleProperty largeur = new SimpleDoubleProperty();
	private DoubleProperty hauteur = new SimpleDoubleProperty();
	private boolean animationDemaree = false;
	private boolean enPause = true;
	private Pane pane;
	public static final int RGB_MAX = 255;
	public IntegerProperty scoreProperty;
	private boolean obst = false;
	public int delaiEntreObstacles = 4;
	public int vitesseDesObstacles = -1;
	public Joueur joueur;
	private int mouvementJoueur;

	public ZoneDeJeu(Pane pane, ObservableList<Obstacle> obstacles, IntegerProperty score, Joueur joueur) {
		this.pane = pane;
		scoreProperty = score;
		largeur.bind(this.pane.widthProperty());
		hauteur.bind(this.pane.heightProperty());
		this.obstacles = obstacles;
		this.mouvementJoueur = 0;
		this.joueur = joueur;
	}

	/**
	 * le point de lancement du thread, il calcule à répétition le nombre de
	 * temps passé depuis le dernier check et, si il n'est pas de zéro, update
	 * les positions des objets De plus, a intervalle regulier il ajoute un
	 * obstacle dans lecran
	 */
	public void demarerAnimation() {
		if (!animationDemaree) {
			enPause = false;
			obst = false;
			lancerJoueur();
			LongProperty tempsEcouleDepuisDerniereVerification = new SimpleLongProperty(0);
			AnimationTimer timer = new AnimationTimer() {
				@Override
				public void handle(long tempsEcoulé) {

					if (tempsEcouleDepuisDerniereVerification.get() > 0) {
						long tempsMettreAJour = tempsEcoulé - tempsEcouleDepuisDerniereVerification.get();
						if (!enPause) {
							if ((Math.round(Math.round(tempsEcoulé / 1_000_000_000.0)) % delaiEntreObstacles) == 0
									&& !obst) {
								ajouterObstacle();
								obst = true;
							} else if (!((Math.round(Math.round(tempsEcoulé / 1_000_000_000.0)) % 2) == 0)) {
								obst = false;
							}

							// TODO mettre le traitement du déplacement du
							// joueur
							bougerJoueur();
							incrementerScore();
							bougerObstacles();
							// TODO regarder si il y a des collisions a ce point
						}
					}
					tempsEcouleDepuisDerniereVerification.set(tempsEcoulé);
				}
			};
			timer.start();
			animationDemaree = true;
		}
	}

	/**
	 * cree un obstacle a une hauteur aleatoire juste hors de l'ecran.
	 */
	private void ajouterObstacle() {
		Obstacle ob = new Obstacle();
		obstacles.add(ob);
		pane.getChildren().add(ob.getApparence());
	}

	/**
	 * augmente le score de 1
	 * 
	 * @param tempsEcoulé
	 */
	private void incrementerScore() {
		this.scoreProperty.set(scoreProperty.get() + 1);
	}

	/**
	 * mets le jeu en pause en empechant l'animation timer d'entrer dans sa
	 * boucle et le remets en fonction quand il est arreter
	 */
	public void pause() {
		enPause = !enPause;
	}

	/**
	 * methode determinant si je joueur est en collision avec un obstacle en ce
	 * moment et qui retourne si, oui ou non, le joueur est en collision
	 * 
	 * @return vrai si en collision, faux si inversement
	 */
	private boolean enCollision() {
		boolean retour = false;
		for (ListIterator<Obstacle> it = obstacles.listIterator(); it.hasNext();) {

			// TODO le code trouvant si l'obstacle est en collision avec le
			// joueur
		}

		return retour;
	}

	/**
	 * deplace les obstacles en X xelon le temps passer
	 * 
	 * @param tempsEcouler
	 */
	private void bougerObstacles() {
		for (ListIterator<Obstacle> it = obstacles.listIterator(); it.hasNext();) {
			Obstacle ob = it.next();
			ob.setPositionXProperty(
					new SimpleDoubleProperty(ob.getPositionXProperty().doubleValue() + vitesseDesObstacles));
			ob.getApparence().setX(ob.getApparence().getX() + vitesseDesObstacles);
		}

	}

	/**
	 * mets un ecouteur sur le clavier pour savoir quand bouger le joueur
	 */
	private void lancerJoueur() {

		pane.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case UP: {
					mouvementJoueur = -1;
					joueur.setPosition(joueur.getPosition()-1);
					joueur.getApparence().setY(joueur.getApparence().getY()-1);
				}
					break;
				case DOWN: {
					{
						mouvementJoueur = 1;
						joueur.setPosition(joueur.getPosition()-1);
						joueur.getApparence().setY(joueur.getApparence().getY()+1);
					}
					break;
				}
				default: {
					mouvementJoueur = 0;
					break;
				}

				}

			}
		});
	}
	private void bougerJoueur(){
		joueur.getPositionProperty().set(joueur.getPositionProperty().get()+mouvementJoueur);
		
		mouvementJoueur=0;
	}
}
