package controleur;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.*;

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

    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
    private int score;
    private int gen;
    private IntegerProperty scoreP;
    private IntegerProperty genP;
    private TypeJoueur tj;

    @FXML
    public void initialize() {
        scoreP = new SimpleIntegerProperty();
        scoreLabel.textProperty().bind(scoreP.asString());
    }

    @FXML
    void play(ActionEvent event) {
        //TODO Implémenter la méthode play lorsque le jeu est construit.
        scoreP.set(scoreP.get() + 2000);
        //play the game.
    }

    private void pause() {
        //TODO Implémenter la méthode pause lorsque le jeu est construit.
        //pauses the game
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

        if (confirm.showAndWait().get() == ButtonType.OK) {
            initialize();
        }
    }

    /**
     * Méthode qui permet de sauvegarder une partie. Pour l'instant, elle n'est que dans un état basique, puisqu'il n'y a aucun paramètres à sauvegarder.
     */
    @FXML
    private boolean save() {
        //TODO Implémenter la méthode save dans son entièreté lorsque le jeu est construit.
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
     * Méthode qui permet de charger une partie. Pour l'instant, elle n'est que dans un état basique, puisqu'il n'y a aucun paramètres à charger.
     */
    @FXML
    private void load() {
        //TODO Implémenter la méthode load dans son entièreté lorsque le jeu est construit.
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
