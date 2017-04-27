package controleur;

import java.util.ArrayList;

import ai.apprentissage.nonsupervise.CompetitionInter;
import ai.apprentissage.nonsupervise.competitionInter.CompetitionInterLiens;
import ai.coeur.Reseau;
import javafx.animation.AnimationTimer;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import modele.elements.visuals.ExtendedImageView;
import modele.elements.hitbox.HitBox;
import modele.game.Game;
import modele.game.game_objects.Enemy;
import modele.game.game_objects.GameObjectType;
import modele.game.game_objects.Player;
import modele.graphique.GraphiqueIA;

public class Controleur {

	@FXML
	private Label scoreLabel;

	@FXML
	private Pane displayJeu;

	@FXML
	private Button btnReinit;

	@FXML
	private Button btnJouer;

	@FXML
	private Button btnQuit;

	@FXML
	private ImageView limitUp;

	@FXML
	private ImageView limitDown;

	@FXML
	private Pane affichageReseau;

	public static final int PLAFOND = 35;
	public static final int PLANCHER = 220;
	public static final int EDGE = 1066;
	public static final int MID_HEIGHT = PLAFOND + ((PLANCHER - PLAFOND) / 2);
	public static final float DIFFICULTY_INCREMENT = 0.05f;

	public static final float TIME_BETWEEN_ENEMIES_DEFAULT = 2f;
	public static final float TIME_BETWEEN_DOUBLES_DEFAULT = 5f;

	public GraphiqueIA graph;

	private CompetitionInter regleApprentissageCompetitionInter;

	private ArrayList<Reseau> listeReseaux;

	Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);

	AnimationTimer timer;
	Game game;
	boolean animStarted = false;
	float timeBetweenEnemies = 0;
	float timerScaleFactor = 1;

	public ArrayList<Reseau> getListeReseaux() {
		return listeReseaux;
	}

	public void setListeReseaux(ArrayList<Reseau> listeReseaux) {
		this.listeReseaux = listeReseaux;
	}

	@FXML
	public void initialize() {
		gameStop();
	}

	@FXML
	void play() {

		if (game.isStopped()) {
			newGame();
			demarerAnimation();
		}
		if (game.isPaused()) {
			game.run();
		}

	}

	@FXML
	private void pause() {
		game.pause();
	}

	/**
	 * le point de lancement du thread, il calcule à répétition le nombre de
	 * temps passé depuis le dernier check et, si il n'est pas de zéro, update
	 * les positions des objets De plus, a intervalle regulier il ajoute un
	 * obstacle dans lecran
	 */
	public void demarerAnimation() {
		if (!animStarted) {
			game.run();

			timer = new AnimationTimer() {

				private long previousTime = 0;

				@Override
				public void handle(long now) {

					float secondsElapsed = (now - previousTime) / 1e9f;

					if (game.isRunning()) {

						if (game.getTimeBetweenDoubles() >= TIME_BETWEEN_DOUBLES_DEFAULT) {
							game.spawnTopBottom();
						}
						if (game.getTimeBetweenEnemies() * game.getTimerScaleFactor() >= TIME_BETWEEN_ENEMIES_DEFAULT) {
							game.spawn();
						}

						game.setTimeBetweenDoubles(game.getTimeBetweenDoubles() + secondsElapsed);
						game.setTimeBetweenEnemies(game.getTimeBetweenEnemies() + secondsElapsed);

						game.update();
						game.render(displayJeu);
					}

					previousTime = now;

					if (game.isStopped()) {
						gameStop();
						timer.stop();
						timerScaleFactor = 1;
						timeBetweenEnemies = 0;
						play();
					}
				}
			};
			timer.start();

		}
		animStarted = true;

	}

	@FXML
	void quit(ActionEvent event) {
		confirm.setTitle("Quitter");
		confirm.setContentText("Voulez-vous vraiment quitter l'application?");

		if (confirm.showAndWait().get() == ButtonType.OK) {
			System.exit(0);
		}
	}

	@FXML
	void reinit(ActionEvent event) {
		confirm.setTitle("Réinitialiser");
		confirm.setContentText("Voulez-vous vraiment réinitialiser la progression et le score?");

		pause();

		if (confirm.showAndWait().get() == ButtonType.OK) {
			gameStop();
		} else
			play();
	}

	private void gameStop() {

		graph = null;
		affichageReseau.getChildren().clear();

		animStarted = false;
		displayJeu.getChildren().clear();
		game = new Game((short) 0, (short) 0, graph, listeReseaux, this);

	}

	private void newGame() {
		if (listeReseaux != null) {
			if (regleApprentissageCompetitionInter == null) {
				regleApprentissageCompetitionInter = new CompetitionInterLiens(listeReseaux, 10);
			}
			regleApprentissageCompetitionInter.faireUneIterationApprentissage();
		}
		graph = new GraphiqueIA(affichageReseau);
		game = new Game((short) 1, (short) 4, graph, listeReseaux, this);

		// Les ennemis "floor" et "roof" sont pour que l'intelligence
		// artificielle reconnaisse qu'il y a des murs.
		Enemy roof = new Enemy(new HitBox((short) 1000, (short) PLAFOND, 500, -PLAFOND));
		game.getEnemiesSet().add(roof);
		ExtendedImageView roofIV = new ExtendedImageView(roof, "voidImage");
		game.getEnemyImagesSet().add(roofIV);

		Enemy floor = new Enemy(new HitBox((short) 1000, (short) PLAFOND, 500, PLANCHER + 2 * PLAFOND));
		game.getEnemiesSet().add(floor);
		ExtendedImageView floorIV = new ExtendedImageView(floor, "voidImage");
		game.getEnemyImagesSet().add(floorIV);

		game.getPlayersSet().forEach(p -> {
			ExtendedImageView iv = new ExtendedImageView(p, "joueur");
			// ExtendedRectangle iv = new ExtendedRectangle(p);
			displayJeu.getChildren().add(iv);
		});
		scoreLabel.textProperty().bind(game.scoreProperty().asString());

	}

	@FXML
	void debutMouvement(KeyEvent event) {
		if (game.getPlayersSet().size() > 0) {
			Player p = game.getPlayersSet().get(0);

			if (p.getObjectType() == GameObjectType.HUMAN) {
				switch (event.getCode()) {
				case UP: {
					p.changeDirection(1);
					break;
				}
				case DOWN: {
					p.changeDirection(-1);
					break;
				}
				default: {
					p.changeDirection(0);
					break;
				}
				}
			}
		}

	}

	@FXML
	void finMouvement() {

		if (game.getPlayersSet().size() > 0) {
			Player p = game.getPlayersSet().get(0);
			if (p.getObjectType() == GameObjectType.HUMAN)
				p.changeDirection(0);
		}
	}

}
