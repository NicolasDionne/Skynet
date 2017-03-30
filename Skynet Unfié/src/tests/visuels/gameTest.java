package tests.visuels;

import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import modele.elements.BoxFactory;
import modele.elements.HitBox;
import modele.elements.MotionPoint;
import modele.game.Game;
import modele.game.Player;

import static javafx.scene.input.KeyCode.*;

/**
 * Les crashs/problèmes sont tout à fait normaux. Ces tests sont des
 * expérimentations pour vérifier les comportements. Ils ne représentent en
 * aucun point l'application
 */
public class gameTest extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {

		Game g = new Game((short) 1);
		BoxFactory boxFactory = new BoxFactory();

		Pane root = new Pane();
		Scene s = new Scene(root);
		root.setPrefHeight(600);
		root.setPrefWidth(1500);

		HitBox hb = g.getPlayersSet().get(0).getHitBox();

		Rectangle rec = boxFactory.getInstance(hb);
		rec.setFill(Paint.valueOf("Red"));
		root.getChildren().add(rec);

		Service sss = new Service() {
			@Override
			protected Task createTask() {
				return new Task() {
					@Override
					protected Object call() throws Exception {

						hb.getCenterPoint().move();
						g.getPlayersSet().get(0).checkObjectBeyondEdges();
						Thread.sleep(100);

						return null;
					}
				};
			}
		};
		sss.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				sss.restart();
			}

		});
		sss.start();
		s.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				String dir = "";
				switch (event.getCode()) {
				case UP: {
					dir = "up";
					break;
				}
				case DOWN: {
					dir = "down";
					break;
				}
				case LEFT: {
					dir = "left";
					break;
				}
				case RIGHT: {
					dir = "right";
					break;
				}
				default: {
					dir = "";
					break;
				}
				}
				for (Player p : g.getPlayersSet()) {
					p.changeDirection(dir);
				}
			}
		});
		s.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == UP || event.getCode() == DOWN || event.getCode() == LEFT
						|| event.getCode() == RIGHT) {

				}
			}
		});
		primaryStage.setScene(s);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch();
	}
}
