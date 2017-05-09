package tests.visuels;

import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.LEFT;
import static javafx.scene.input.KeyCode.RIGHT;
import static javafx.scene.input.KeyCode.UP;

import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import modele.elements.BoxFactory;
import modele.elements.hitbox.HitBox;
import modele.game.Game;
import modele.game.game_objects.Player;

/**
 * Les crashs/problèmes sont tout à fait normaux. Ces tests sont des
 * expérimentations pour vérifier les comportements. Ils ne représentent en
 * aucun point l'application
 */
public class gameTest extends Application {
	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) throws Exception {

		Game g = new Game((short) 1, (short) 0, null, null, null, null);
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
				int dir = 0;
				switch (event.getCode()) {
				case UP: {
					dir = 1;
					break;
				}
				case DOWN: {
					dir = -1;
					break;
				}

				default: {
					dir = 0;
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
}
