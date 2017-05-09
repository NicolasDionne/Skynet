package tests.visuels;

import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import modele.elements.BoxFactory;
import modele.elements.ai_related.VisionGrid;
import modele.elements.hitbox.HitBox;
import modele.elements.hitbox.MotionPoint;
import modele.game.game_objects.GameObjectType;
import modele.game.game_objects.Player;
import modele.game.game_objects.PlayerType;
import utilitaires.Parametres;

/**
 * Les crashs/problèmes sont tout à fait normaux. Ces tests sont des
 * expérimentations pour vérifier les comportements. Ils ne représentent en
 * aucun point l'application
 */
public class gridTest extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {

		BoxFactory boxFactory = new BoxFactory();

		Pane root = new Pane();
		Scene s = new Scene(root);
		root.setPrefHeight(600);
		root.setPrefWidth(1500);

		HitBox h = new HitBox((short) 20, (short) 20, 600, 200);
		Player p = new Player(GameObjectType.HUMAN, h,new Parametres(0, 0, 0, 0));

		VisionGrid v = new VisionGrid(h);

		for (HitBox hb : v.getHitBoxes()) {
			Rectangle r = boxFactory.getInstance(hb);
			root.getChildren().add(r);
			r.setFill(Paint.valueOf("green"));
		}

		HitBox hbb = new HitBox((short) 60, (short) 60, 500, 200);
		MotionPoint origin = new MotionPoint(200, 200);
		hbb.setOrigin(origin);
		hbb.getCenterPoint().getRotationParameters().setAngularVelocity(0.1f);
		Rectangle rr = boxFactory.getInstance(hbb);
		root.getChildren().add(rr);

		Rectangle r = boxFactory.getInstance(h);
		r.setFill(Paint.valueOf("red"));
		root.getChildren().add(r);

		Service sss = new Service() {
			@Override
			protected Task createTask() {
				return new Task() {
					@Override
					protected Object call() throws Exception {

						h.getCenterPoint().move();
						p.checkObjectBeyondEdges();

						hbb.getCenterPoint().getRotationParameters().updateAngle();
						hbb.getCenterPoint().rotate(origin);

						for (HitBox hhh : v.getHitBoxes()) {
							if (hhh.checkCollision(hbb)) {
								System.out.println("collision");
							}
						}
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
				p.changeDirection(dir);

			}
		});
		s.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				String dir = "";
				int i = 0;
				if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.LEFT
						|| event.getCode() == KeyCode.RIGHT) {
					Service s = new Service() {
						@Override
						protected Task createTask() {
							return new Task() {
								@Override
								protected Void call() throws Exception {

									for (int i = 0; i < 20; i++) {
										Thread.sleep(50);
									}

									p.getHitBox().getCenterPoint().setVelocity(0);
									p.getHitBox().getCenterPoint().setAcceleration(0);

									return null;
								}
							};
						}
					};
					s.start();
				}
			}

		});

		primaryStage.setScene(s);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
