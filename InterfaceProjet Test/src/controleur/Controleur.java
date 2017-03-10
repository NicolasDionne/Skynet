package controleur;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import modele.Joueur;
import modele.MouvementJoueur;
import modele.Obstacle;
import modele.ZoneDeJeu;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;

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
	private ImageView imgJoueur;

	Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
	private Joueur j = new Joueur(100,imgJoueur);
	private int gen;
	private IntegerProperty scoreP;
	private TypeJoueur tj;
	private Image imageObst;
	private ObservableList<Obstacle> obstacles;
	public ZoneDeJeu zoneJeu;

	@FXML
	public void initialize() {
		displayJeu.getChildren().remove(zoneJeu);
		zoneJeu = null;
		obstacles = FXCollections.observableArrayList();
		obstacles.clear();
		scoreP = new SimpleIntegerProperty();
		scoreLabel.textProperty().bind(scoreP.asString());
		scoreP.set(0);

		zoneJeu = new ZoneDeJeu(displayJeu, obstacles, scoreP,j);

	}

	@FXML
	void play(ActionEvent event) {
		ImageView obs = new ImageView();
		obs.setImage(imageObst);
		zoneJeu.demarerAnimation();
		// TODO Implémenter la méthode play lorsque le jeu est construit.
	}

	@FXML
	private void pause() {
		zoneJeu.pause();
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

		// if (confirm.showAndWait().get() == ButtonType.OK) {
		initialize();
		// }
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
			System.out.println(e);
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
			System.out.println(e);
		}

		confirm.setTitle("Load Test View");
		confirm.setContentText(ligneRetour.equals("content") ? "true" : "false");

		if (confirm.showAndWait().get() == ButtonType.OK) {
			confirm.close();
		}
	}

	enum TypeJoueur {
		HUMAIN("Humain"), AI("AI");

		String name;

		private TypeJoueur(String name) {
			this.name = name;
		}

		public String toString() {
			return name;
		}

	}

}
