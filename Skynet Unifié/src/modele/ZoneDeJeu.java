package modele;

import java.util.ArrayList;
import java.util.ListIterator;

import controleur.Controleur;
import javafx.animation.AnimationTimer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import modele.exceptions.ConstructorException;
import modele.graphique.GraphiqueIA;

public class ZoneDeJeu {
	public ObservableList<Obstacle> obstacles = FXCollections.observableArrayList();
	public ObservableList<ImageView> imgObstacles = FXCollections.observableArrayList();
	private DoubleProperty largeur = new SimpleDoubleProperty();
	private DoubleProperty hauteur = new SimpleDoubleProperty();
	private boolean animationDemaree = false;
	private boolean enPause = true;
	private Pane displayJeu;
	public static final int RGB_MAX = 255;
	public IntegerProperty scoreProperty;
	private boolean obst = false;
	public int delaiEntreObstacles = 2;
	public int vitesseDesObstacles = -2;
	public Joueur joueur;
	private int mouvementJoueur;
	AnimationTimer timer;
	public GraphiqueIA graph;

	public ZoneDeJeu(Pane displayJeu, ObservableList<Obstacle> obstacles, IntegerProperty score, Joueur joueur, GraphiqueIA graph) {
		this.displayJeu = displayJeu;
		scoreProperty = score;
		largeur.bind(this.displayJeu.widthProperty());
		hauteur.bind(this.displayJeu.heightProperty());
		this.obstacles = obstacles;
		this.mouvementJoueur = 0;
		this.joueur = joueur;
		this.graph = graph;
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
			LongProperty tempsEcouleDepuisDerniereVerification = new SimpleLongProperty(0);
			timer = new AnimationTimer() {
				@Override
				public void handle(long tempsEcoulé) {

					if (tempsEcouleDepuisDerniereVerification.get() > 0) {
						long tempsMettreAJour = tempsEcoulé - tempsEcouleDepuisDerniereVerification.get();
						if (!enPause) {
							if ((Math.round(Math.round(tempsEcoulé / 1_000_000_000.0)) % delaiEntreObstacles) == 0
									&& !obst) {
								try {
									ajouterObstacle();
								} catch (ConstructorException e) {
									e.printStackTrace();
								}
								obst = true;
							} else if (!((Math.round(Math.round(tempsEcoulé / 1_000_000_000.0)) % 2) == 0)) {
								obst = false;
							}

							bougerJoueur();
							incrementerScore();
							bougerObstacles();
							suprimerInutiles();
							//TODO ajouter envoyer les infos a l'ia et recevoir sa reponse
							//graph.setResau(reseau);
							graph.refreshGraph(mouvementJoueur);

							for (Obstacle o : obstacles) {
								if(joueur.getHitBox().checkCollision(o.getHitBox()))
									System.out.println("collision");
							}
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
	 * 
	 * @throws ConstructorException
	 */
	private void ajouterObstacle() throws ConstructorException {
		if (animationDemaree) {
			Obstacle ob = new Obstacle();
			obstacles.add(ob);
			displayJeu.getChildren().add(ob.getApparence());
		}
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
		timer.stop();
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

	public void joueurBouge(int direction) {
		mouvementJoueur = direction;
	}

	public void joueurBougePas() {
		mouvementJoueur = 0;
	}

	private void bougerJoueur() {
		if ((joueur.getPositionProperty().get() + mouvementJoueur + (joueur.HAUTEUR_JOUEUR / 2) > joueur.HAUTEUR_MAX)
				&& joueur.getPositionProperty().get() + mouvementJoueur
						+ (joueur.HAUTEUR_JOUEUR / 2) < joueur.BASSEUR_MAX)
			joueur.getPositionProperty().set(joueur.getPositionProperty().get() + mouvementJoueur);
	}

	public void reinitialiser() {
		for (Obstacle obstacle : obstacles) {
			displayJeu.getChildren().remove(obstacle.getApparence());
		}
		obstacles.clear();
		timer.stop();
		Controleur.scoreP.set(0);
	}

	public void suprimerInutiles() {
		ArrayList<Obstacle> obstaclesASuprimer = new ArrayList<>();
		for (int i = 0; i < obstacles.size(); i++) {
			if (obstacles.get(i).positionX < -40) {
				displayJeu.getChildren().remove(obstacles.get(i).getApparence());
				obstaclesASuprimer.add(obstacles.get(i));
			}
		}
		if (!obstaclesASuprimer.isEmpty()) {
			obstacles.removeAll(obstaclesASuprimer);
		}
	}

	public void redemarer() {
		timer.start();
	}

	public int getMouvementJoueur() {
		return mouvementJoueur;
	}
}
