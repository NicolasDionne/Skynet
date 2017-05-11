package controleur;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchEvent.Modifier;
import java.util.ArrayList;
import java.util.Iterator;

import ai.apprentissage.nonsupervise.CompetitionInter;
import ai.apprentissage.nonsupervise.competitionInter.CompetitionInterLiens;
import ai.coeur.Reseau;
import application.Main;
import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import modele.elements.hitbox.HitBox;
import modele.elements.visuals.ExtendedImageView;
import modele.elements.visuals.VisualFactory;
import modele.game.Difficulty;
import modele.game.Game;
import modele.game.game_objects.Enemy;
import modele.game.game_objects.GameObjectType;
import modele.game.game_objects.Player;
import modele.game.game_objects.PlayerAI;
import modele.graphique.GraphiqueIA;
import utilitaires.Parametres;
import utilitaires.parametres.ModificateurParametres;

public class Controleur implements Serializable {

	private static final long serialVersionUID = 3726618757536085818L;

	@FXML
	private transient Label scoreLabel;

	@FXML
	private transient Label generationLabel;

	@FXML
	private transient Pane displayJeu;

	@FXML
	private transient Button btnReinit;

	@FXML
	private transient Button btnJouer;

	@FXML
	private transient Button btnQuit;

	@FXML
	private transient ChoiceBox<String> choixDifficulte;

	@FXML
	private transient CheckBox boxJoueurHumain;

	@FXML
	private transient ImageView limitUp;

	@FXML
	private transient ImageView limitDown;

	@FXML
	private transient Pane affichageReseau;

	@FXML
	private transient MenuItem sauvegarderMenu;

	@FXML
	private transient MenuItem chargerMenu;

	@FXML
	private transient MenuItem modifierMenu;

	@FXML
	private transient MenuItem solutionFinaleMenu;

	public transient static final int PLAFOND = 35;
	public transient static final int PLANCHER = 220;
	public transient static final int EDGE = 1066;
	public transient static final int MID_HEIGHT = PLAFOND + ((PLANCHER - PLAFOND) / 2);
	public transient static final float DIFFICULTY_INCREMENT = 0.05f;
	public transient static final int NB_PLAYER_AI = 25;

	public transient static final float TIME_BETWEEN_DEFAULT = 2f;

	public transient GraphiqueIA graph;

	private CompetitionInter regleApprentissageCompetitionInter;

	private ArrayList<Reseau> listeReseaux;
	public Parametres parametres;

	transient Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);

	transient AnimationTimer timer;
	transient Game game;
	transient boolean animStarted = false;
	transient float timeBetweenEnemies = 0;
	transient float timerScaleFactor = 1;

	private transient VisualFactory vF;

	public ArrayList<Reseau> getListeReseaux() {
		return listeReseaux;
	}

	public void setListeReseaux(ArrayList<Reseau> listeReseaux) {
		this.listeReseaux = listeReseaux;
	}

	@FXML
	public void initialize() {

		vF = new VisualFactory();
		this.parametres = new Parametres(8, 4, 8, 10);
		KeyCodeCombination chargerMenuKeyCombination = new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN);
		chargerMenu.setAccelerator(chargerMenuKeyCombination);

		KeyCodeCombination sauvegarderMenuKeyCombination = new KeyCodeCombination(KeyCode.S,
				KeyCombination.CONTROL_DOWN);
		sauvegarderMenu.setAccelerator(sauvegarderMenuKeyCombination);

		KeyCodeCombination modifierMenuKeyCombination = new KeyCodeCombination(KeyCode.M, KeyCombination.CONTROL_DOWN);
		modifierMenu.setAccelerator(modifierMenuKeyCombination);

		KeyCodeCombination solutionFinaleMenuKeyCombination = new KeyCodeCombination(KeyCode.J,
				KeyCombination.CONTROL_DOWN);
		solutionFinaleMenu.setAccelerator(solutionFinaleMenuKeyCombination);

		initialiserParametres(this.parametres);

		choixDifficulte.getItems().addAll("Très facile", "Facile", "Moyen", "Difficile", "Très difficile");

		gameStop();
		modifierParametres();

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
	 * les positions des objets De plus, à intervalles réguliers il ajoute un
	 * obstacle dans l'écran
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
						if (game.getTimeBetweenEnemies() * game.getTimerScaleFactor() >= TIME_BETWEEN_DEFAULT) {
							game.spawn();
						}

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
		confirm.setContentText(
				"Voulez-vous vraiment réinitialiser la progression, les réseaux, l'apprentissage et le score?");

		pause();

		if (confirm.showAndWait().get() == ButtonType.OK) {
			listeReseaux = null;
			regleApprentissageCompetitionInter = null;
			gameStop();
		} else
			play();
	}

	private void gameStop() {

		graph = null;
		affichageReseau.getChildren().clear();

		animStarted = false;
		displayJeu.getChildren().clear();
		game = new Game((short) 0, (short) 0, graph, listeReseaux, this, Difficulty.VERY_EASY);

	}

	/**
	 * Méthode qui crée une nouvelle partie et génère les éléments nécessaires
	 * pour son fonctionnement.
	 */
	private void newGame() {
		if (listeReseaux != null) {
			if (regleApprentissageCompetitionInter == null) {
				regleApprentissageCompetitionInter = new CompetitionInterLiens(listeReseaux, 10);
			}
			regleApprentissageCompetitionInter.faireUneIterationApprentissage();
		}
		generationLabel.setText("Génération #" + ((regleApprentissageCompetitionInter == null) ? 1
				: regleApprentissageCompetitionInter.getIterationCourante()));
		graph = new GraphiqueIA(affichageReseau, parametres);
		Difficulty dif = Difficulty.VERY_EASY;
		if (choixDifficulte.getValue() != null) {
			switch (choixDifficulte.getValue()) {
			case ("Très facile"):
				dif = Difficulty.VERY_EASY;
				break;
			case ("Facile"):
				dif = Difficulty.EASY;
				break;
			case ("Moyen"):
				dif = Difficulty.MEDIUM;
				break;
			case ("Difficile"):
				dif = Difficulty.HARD;
				break;
			case ("Très difficile"):
				dif = Difficulty.VERY_HARD;
				break;

			}
		}
		if (boxJoueurHumain.isSelected()) {
			game = new Game((short) 1, (short) NB_PLAYER_AI, graph, listeReseaux, this, dif);
		} else {
			game = new Game((short) 0, (short) NB_PLAYER_AI, graph, listeReseaux, this, dif);
		}

		// Les ennemis "floor" et "roof" sont pour que l'intelligence
		// artificielle reconnaisse qu'il y a des murs.
		Enemy roof = new Enemy(new HitBox((short) 1000, (short) PLAFOND, 500, -PLAFOND));
		game.getEnemiesSet().add(roof);
		ExtendedImageView roofIV = vF.getInstance(roof, GameObjectType.VOID.getStyle());
		game.getEnemyImagesSet().add(roofIV);

		Enemy floor = new Enemy(new HitBox((short) 1000, (short) PLAFOND, 500, PLANCHER + 2 * PLAFOND));
		game.getEnemiesSet().add(floor);
		ExtendedImageView floorIV = vF.getInstance(floor, GameObjectType.VOID.getStyle());
		game.getEnemyImagesSet().add(floorIV);

		game.getPlayersSet().forEach(p -> {
			ExtendedImageView iv = vF.getInstance(p, p.getObjectType().getStyle());
			displayJeu.getChildren().add(iv);
		});
		scoreLabel.textProperty().bind(game.scoreProperty().asString());

	}

	@FXML
	void debutMouvement(KeyEvent event) {
		if (game.getPlayersSet().size() > 0) {
			Player p = game.getPlayersSet().get(game.getPlayersSet().size() - 1);

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
			Player p = game.getPlayersSet().get(game.getPlayersSet().size() - 1);
			if (p.getObjectType() == GameObjectType.HUMAN)
				p.changeDirection(0);
		}
	}

	@FXML
	private void sauvegarder() {
		try {
			this.parametres.setValsSelonIntProp();
			FileChooser chooser = new FileChooser();

			ExtensionFilter filter = new ExtensionFilter("fichier de sauvegarde du logiciel Skynet", "*.skynet");
			chooser.getExtensionFilters().add(filter);
			chooser.setSelectedExtensionFilter(filter);
			String pathName = System.getProperty("user.home") + "\\Documents\\Reseau";
			File initialDirectory = new File(pathName);
			if (!initialDirectory.exists()) {
				initialDirectory.mkdirs();
			}
			chooser.setInitialDirectory(initialDirectory);
			File selectedFile = chooser.showSaveDialog(Main.stage);
			if (selectedFile != null && !selectedFile.isDirectory()) {
				FileOutputStream fileOutputStream = new FileOutputStream(selectedFile);
				ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);

				outputStream.writeObject(this);

				outputStream.close();
			}

			if (initialDirectory.isDirectory()) {
				if (initialDirectory.list().length == 0) {
					Path path = FileSystems.getDefault().getPath(initialDirectory.getPath());
					Files.deleteIfExists(path);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void charger() {
		try {
			FileChooser chooser = new FileChooser();

			ExtensionFilter filter = new ExtensionFilter("fichier de sauvegarde du logiciel Skynet", "*.skynet");
			chooser.getExtensionFilters().add(filter);
			chooser.setSelectedExtensionFilter(filter);
			String pathName = System.getProperty("user.home") + "\\Documents\\Reseau";
			File initialDirectory = new File(pathName);
			chooser.setInitialDirectory(initialDirectory);

			if (!initialDirectory.exists()) {
				initialDirectory.mkdirs();
			}

			File selectedFile = chooser.showOpenDialog(Main.stage);

			if (selectedFile != null && !selectedFile.isDirectory()) {
				FileInputStream fileInputStream = new FileInputStream(selectedFile);
				ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);

				Controleur charge = (Controleur) inputStream.readObject();
				this.listeReseaux = charge.listeReseaux;
				this.regleApprentissageCompetitionInter = charge.regleApprentissageCompetitionInter;
				initialiserParametres(charge.parametres);
				this.parametres.setIntsPropsSelonVal();

				inputStream.close();
			}

			if (initialDirectory.isDirectory()) {
				if (initialDirectory.list().length == 0) {
					Path path = FileSystems.getDefault().getPath(initialDirectory.getPath());
					Files.deleteIfExists(path);
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void modifierParametres() {
		Stage stage;
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/utilitaires/parametres/modificateurParametres/Vue.fxml"));
			GridPane root = loader.load();
			ModificateurParametres modificateurParametres = loader.getController();
			modificateurParametres.setSpinners(this.parametres);
			Scene s = new Scene(root);
			stage = new Stage();
			stage.setScene(s);
			stage.showAndWait();
			this.parametres.setValNbLignes(modificateurParametres.getSpinnerNbLignes().getValue());
			this.parametres.setValNbColonnes(modificateurParametres.getSpinnerNbColonnes().getValue());
			this.parametres.setValNbNiveaux(modificateurParametres.getSpinnerNbNiveaux().getValue());
			this.parametres.setValNbNeuronesParNiveau(modificateurParametres.getSpinnerNbNeuronesPN().getValue());
			this.parametres.setValsSelonIntProp();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initialiserParametres(Parametres parametres) {
		this.parametres.setNbColonnes(new SimpleIntegerProperty(parametres.getValNbCol()));
		this.parametres.setNbLignes(new SimpleIntegerProperty(parametres.getValNbLig()));
		this.parametres.setNbNiveaux(new SimpleIntegerProperty(parametres.getValNbNiv()));
		this.parametres.setNbNeuronesParNiveau(new SimpleIntegerProperty(parametres.getValNbNeuPN()));

		this.parametres.getNbLignes().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (oldValue != newValue) {
					ctrlNbJ();
					listeReseaux = null;
					regleApprentissageCompetitionInter = null;
				}
			}
		});

		this.parametres.getNbColonnes().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (oldValue != newValue) {
					ctrlNbJ();
					listeReseaux = null;
					regleApprentissageCompetitionInter = null;
				}
			}
		});

		this.parametres.getNbNiveaux().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (oldValue != newValue) {
					ctrlNbJ();
					listeReseaux = null;
					regleApprentissageCompetitionInter = null;
				}
			}
		});

		this.parametres.getNbNeuronesParNiveau().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (oldValue != newValue) {
					ctrlNbJ();
					listeReseaux = null;
					regleApprentissageCompetitionInter = null;
				}
			}
		});
	}

	@FXML
	private void ctrlNbJ() {
		if (!game.getPlayersSet().isEmpty()) {
			for (Player player : game.getPlayersSet()) {
				if (player.getClass() == PlayerAI.class) {
					((PlayerAI) player).getReseau().setScore(game.getScore());
				}
			}
			game.getPlayersSet().clear();
		}
	}

}