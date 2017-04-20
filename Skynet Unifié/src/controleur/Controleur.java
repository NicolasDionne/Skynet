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
import modele.elements.BoxFactory;
import modele.elements.hitbox.HitBox;
import modele.game.Game;
import modele.game.game_objects.Enemy;
import modele.game.game_objects.Player;
import modele.game.game_objects.GameObjectType;
import modele.graphique.GraphiqueIA;

import java.io.*;
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

	public static final int PLAFOND = 64;
	public static final int PLANCHER = 243;
	public static final int EDGE = 1066;
	public static final int MID_HEIGHT = PLAFOND + ((PLANCHER - PLAFOND) / 2);
	public static final float DIFFICULTY_INCREMENT = 0.02f;
	public GraphiqueIA graph;

	Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);

	AnimationTimer timer;
	Game game;
	BoxFactory boxFactory;
	boolean animStarted = false;
	long lastUpdate = 0;
	float timeBetweenEnemies = 0;
	float timerScaleFactor;

	@FXML
	public void initialize() {
		gameStop();
		boxFactory = new BoxFactory();
		System.out.println(MID_HEIGHT);
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
			LongProperty tempsEcouleDepuisDerniereVerification = new SimpleLongProperty(0);

			Timeline spawnEnemyTimeLine = new Timeline(new KeyFrame(Duration.millis(1000), e -> {

				if (game.isRunning()) {
					HitBox hb = game.spawnEnemy().getHitBox();
					Rectangle r = boxFactory.getInstance(hb);
					displayJeu.getChildren().add(r);
				}

			}));
			// spawnEnemyTimeLine.setCycleCount(Animation.INDEFINITE);
			spawnEnemyTimeLine.play();
			spawnEnemyTimeLine.setOnFinished(e -> spawnEnemyTimeLine.play());

			timer = new AnimationTimer() {
				@Override
				public void handle(long now) {

					if (tempsEcouleDepuisDerniereVerification.get() > 0) {
						game.update();
						// System.out.println(now);
					}
					if (now - lastUpdate >= 20)
						tempsEcouleDepuisDerniereVerification.set(now);
					if (game.isStopped()) {
						gameStop();
						timer.stop();
						spawnEnemyTimeLine.stop();
					}
					for (int i = displayJeu.getChildren().size() - 1; i >= 0; i--) {
						Rectangle r = (Rectangle) displayJeu.getChildren().get(i);
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

		animStarted = false;
		
		clearObstacles();
		if (graph != null) {
			graph.resetLiens();
		}
		game = new Game((short) 0, (short) 0, graph);

	}

	private void newGame() {
		graph = new GraphiqueIA(affichageReseau);
		game = new Game((short) 1, (short) 0, graph);

		game.getPlayersSet().forEach(p -> {
			Rectangle r = boxFactory.getInstance(p.getHitBox());
			displayJeu.getChildren().add(r);
			r.setFill(Paint.valueOf("red"));
		});
		// TODO fix le score
		// scoreLabel.textProperty().bind(game.scoreProperty().asString());

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
	
	private void clearObstacles(){
		
	}

}
