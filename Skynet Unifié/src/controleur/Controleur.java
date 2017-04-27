package controleur;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import modele.elements.visuals.ExtendedImageView;
import modele.elements.visuals.ExtendedRectangle;
import modele.elements.hitbox.HitBox;
import modele.elements.hitbox.MotionPoint;
import modele.game.Game;
import modele.game.game_objects.Enemy;
import modele.game.game_objects.GameObjectType;
import modele.game.game_objects.Player;
import modele.graphique.GraphiqueIA;

import java.io.*;
import java.util.ArrayList;
import java.util.TreeSet;

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

	public GraphiqueIA graph;

	Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);

	AnimationTimer timer;
	Game game;
	boolean animStarted = false;
	float timeBetweenEnemies = 0;
	float timerScaleFactor = 1;

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
					previousTime = now;

					timeBetweenEnemies += secondsElapsed;

					if (timeBetweenEnemies * timerScaleFactor >= 2f) {

						timeBetweenEnemies = 0;
						timerScaleFactor += DIFFICULTY_INCREMENT;

						Enemy enemy = game.spawnEnemy();
						ExtendedImageView r = new ExtendedImageView(enemy, "obstacle");
						// ExtendedRectangle r = new ExtendedRectangle(enemy);
						displayJeu.getChildren().add(r);
					}

					game.update();

					if (game.isStopped()) {
						gameStop();
						timer.stop();
						timerScaleFactor = 1;
						timeBetweenEnemies = 0;
					}

					for (int i = displayJeu.getChildren().size() - 1; i >= 0; i--) {
						ImageView r = (ImageView) displayJeu.getChildren().get(i);
						if (r.getX() < -Enemy.ENEMY_DIM)
							displayJeu.getChildren().remove(i);
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
		game = new Game((short) 0, (short) 0, graph, new ArrayList<>());

	}

	private void newGame() {

		graph = new GraphiqueIA(affichageReseau);
		game = new Game((short) 1, (short) 0, graph, new ArrayList<>());

		// Les ennemis "floor" et "roof" sont pour que l'intelligence
		// artificielle reconnaisse qu'il y a des murs.
		Enemy roof = new Enemy(new HitBox((short) 1000, (short) PLAFOND, 500, -PLAFOND));
		game.getEnemiesSet().add(roof);

		Enemy floor = new Enemy(new HitBox((short) 1000, (short) PLAFOND, 500, PLANCHER + 2 * PLAFOND));
		game.getEnemiesSet().add(floor);

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

	/**
	 * Méthode qui permet de sauvegarder une partie. Pour l'instant, elle n'est
	 * que dans un état basique, puisqu'il n'y a aucun paramètres à sauvegarder.
	 */
	@FXML
	private boolean save() {
		// TODO Implémenter la méthode save dans son entièreté lorsque le jeu
		// est construit.
		String content = "content";
		try {
			/* f.createNewFile(); */
			PrintWriter sortie = new PrintWriter(new FileWriter("save"));

			sortie.println(content);

			sortie.close();
		} catch (IOException e) {
		}

		return true;
	}

	/**
	 * Méthode qui permet de charger une partie. Pour l'instant, elle n'est que
	 * dans un état basique, puisqu'il n'y a aucun paramètres à charger.
	 */
	@FXML
	private void load() {
		// TODO Implémenter la méthode load dans son entièreté lorsque le jeu
		// est construit.
		String ligneRetour = null;

		try {
			BufferedReader entree = new BufferedReader(new FileReader("save"));
			ligneRetour = entree.readLine();

			entree.close();
		} catch (IOException e) {
		}

		confirm.setTitle("Load Test View");
		confirm.setContentText(ligneRetour.equals("content") ? "true" : "false");

		if (confirm.showAndWait().get() == ButtonType.OK) {
			confirm.close();
		}
	}

}
