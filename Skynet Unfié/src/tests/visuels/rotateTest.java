package tests.visuels;

import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import modele.elements.HitBox;
import modele.elements.MotionPoint;

import java.util.ArrayList;

/**
 * Les crashs/problèmes sont tout à fait normaux. Ces tests sont des
 * expérimentations pour vérifier les comportements. Ils ne représentent en
 * aucun point l'application
 */
public class rotateTest extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane root = new Pane();
		Scene s = new Scene(root);
		primaryStage.setScene(s);
		primaryStage.show();
		ArrayList<HitBox> hbList = new ArrayList<>();

		s.setOnMouseClicked(event -> {
			if (event.getButton() == MouseButton.PRIMARY) {
				float randSpeed = (float) (Math.random() * 0.2);
				float randAcc = (float) (Math.random() * 0.02);

				HitBox hb = new HitBox((short) 40, (short) 40, (float) event.getX(), (float) event.getY());
				hb.getCenterPoint().getRotationParameters().setAngularVelocity(randSpeed);
				hb.getSelfRotationParameters().setAngularVelocity(randSpeed);
				hb.setOrigin(new MotionPoint(50, 50));
				hb.getOrigin().setVelocity(2);
				hb.getOrigin().setDirection(Math.PI / 4.0);
				hb.getOrigin().setAcceleration(0.05f);
				hb.getOrigin().setAccelerationAngle(Math.PI / 4.0);
				hb.getCenterPoint().setAcceleration(0.05f);
				hb.getCenterPoint().setAccelerationAngle(Math.PI / 4.0);
				hb.getCenterPoint().setVelocity(2);
				hb.getCenterPoint().setDirection(Math.PI / 4.0);
				hbList.add(hb);

				Circle o = new Circle(hb.getOrigin().getX(), hb.getOrigin().getY(), 5, Paint.valueOf("Green"));
				root.getChildren().add(o);
				o.centerXProperty().bind(hb.getOrigin().xProperty());
				o.centerYProperty().bind(hb.getOrigin().yProperty());

				Rectangle r = new Rectangle(event.getX() - 20, event.getY() - 20, 40, 40);
				root.getChildren().add(r);

				Circle cc = new Circle(2, Paint.valueOf("green"));
				cc.centerYProperty().bind(hb.getCenterPoint().yProperty());
				cc.centerXProperty().bind(hb.getCenterPoint().xProperty());
				root.getChildren().add(cc);

				Rotate rot = new Rotate(0, 400, 400);
				r.getTransforms().add(rot);
				rot.angleProperty().bind(hb.getCenterPoint().getRotationParameters().angleProperty().multiply(360)
						.divide(Math.PI).divide(2));
				rot.pivotXProperty().bind(hb.getOrigin().xProperty());
				rot.pivotYProperty().bind(hb.getOrigin().yProperty());

				hb.setHitsOthers(true);

				Service serv = new Service() {
					@Override
					protected Task createTask() {
						return new Task() {
							@Override
							protected Object call() throws Exception {
								while (true) {
									r.setX(r.getX() + hb.getCenterPoint().velocityX());
									r.setY(r.getY() + hb.getCenterPoint().velocityY());
									hb.getCenterPoint().move();
									hb.getOrigin().move();
									hb.getCenterPoint().rotate(hb.getOrigin());
									hb.rotateSelf();

									for (int i = 0; i < hbList.size(); i++) {
										for (int j = i + 1; j < hbList.size(); j++) {
											if (hbList.get(i).checkCollision(hbList.get(j)) == true)
												System.out.println("collision");
										}
									}
									Thread.sleep(50);
								}
							}
						};
					}
				};
				serv.start();

			}
			if (event.getButton() == MouseButton.SECONDARY) {
				Circle c = new Circle(event.getX(), event.getY(), 5, Paint.valueOf("Red"));
				root.getChildren().add(c);
				System.out.println(c.getCenterX() + ", " + c.getCenterY());
			}

		});
		s.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.R) {
				hbList.clear();
				root.getChildren().clear();
			}
			if (event.getCode() == KeyCode.Q) {
				primaryStage.close();
			}
		});

	}

	public static void main(String[] args) {
		launch(args);
	}
}