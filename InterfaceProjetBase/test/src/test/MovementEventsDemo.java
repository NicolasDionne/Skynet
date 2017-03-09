package test;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class MovementEventsDemo extends Application {
	private static final int DISTANCE_MOUVEMENT= 10;


	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		final Circle circle = createCircle();
		final Group group = new Group(circle);

		final Scene scene = new Scene(group, 600, 400, Color.CORNSILK);
		moveCircleOnKeyPress(scene, circle);

		stage.setScene(scene);
		stage.show();
	}

	private Circle createCircle() {
		final Circle circle = new Circle(200, 150, 50, Color.BLUEVIOLET);
		circle.setOpacity(0.7);
		return circle;
	}

	private void moveCircleOnKeyPress(Scene scene, final Circle circle) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case A: {
					System.out.println("avant: " + circle.getCenterY());
					if (circle.getCenterY() - circle.getRadius() - DISTANCE_MOUVEMENT >= 0) {
						circle.setCenterY(circle.getCenterY() - 10);
						System.out.println("apres: " + circle.getCenterY());
					}
					break;
				}
				case Z: {
					System.out.println("down");
					if (circle.getCenterY() + circle.getRadius() < scene.getHeight()) {
						circle.setCenterY(circle.getCenterY() + DISTANCE_MOUVEMENT);
					}
					break;
				}
				default:
					break;
				}
			}
		});
	}

}
