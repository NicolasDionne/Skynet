package tests_visuels;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import modele.elements.HitBox;
import modele.elements.Point;
import modele.exceptions.ConstructorException;
import utilitaires.MathUtilitaires;

import java.awt.geom.Line2D;
import java.util.ArrayList;

public class CollisionTest extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ArrayList<HitBox> hbList = new ArrayList<>();
        Pane root = new Pane();
        Scene s = new Scene(root);
        primaryStage.setScene(s);
        primaryStage.show();

        s.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                try {
                    HitBox hb = new HitBox((short) 40, (short) 40, (float) event.getX(), (float) event.getY());

                    hb.setHitsOthers(true);
                    double randomAngle = Math.random() * (Math.PI * 2);

                    hb.getSelfRotationParameters().setAngle(randomAngle);

                    Rectangle r = new Rectangle(hb.getCenterPoint().getX() - 20, hb.getCenterPoint().getY() - 20, hb.getWidth(), hb.getHeight());

                    r.rotateProperty().bind(hb.getSelfRotationParameters().angleProperty().multiply(360).divide(Math.PI).divide(2));

                    root.getChildren().add(r);
                    hbList.add(hb);


                } catch (ConstructorException e) {
                    e.printStackTrace();
                }

            }
            if (event.getButton() == MouseButton.SECONDARY) {
                for (int i = 0; i < hbList.size(); i++) {
                    for (int j = i + 1; j < hbList.size(); j++) {
                        System.out.println(hbList.get(i).checkCollision(hbList.get(j)) ? "collision (" + i + ", " + j + ")" : "pas de collision(" + i + ", " + j + ")");
                    }
                }
            }
            if (event.getButton() == MouseButton.MIDDLE) {
                root.getChildren().clear();
                hbList.clear();
            }
        });
    }
}
