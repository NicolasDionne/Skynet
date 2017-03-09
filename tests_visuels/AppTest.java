package tests_visuels;


import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Point3D;
import javafx.scene.Scene;
import javafx.scene.effect.Light;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import modele.elements.*;
import modele.math.Vector2D;
import utilitaires.MathUtilitaires;

import java.awt.*;


public class AppTest extends Application {
    private Stage mainStage;

    @Override
    public void start(Stage primaryStage) {
        try {
            mainStage = primaryStage;
            AnchorPane root = new AnchorPane();
            Scene s = new Scene(root);
            mainStage.setScene(s);
            mainStage.show();

            Rectangle rec1 = new Rectangle(600, 200, 50, 50);
            Rectangle rec2 = new Rectangle(800, 700, 50, 50);
            Rectangle rec3 = new Rectangle(1000, 200, 50, 50);
            Rectangle rec4 = new Rectangle(500, 500, 50, 50);


            root.getChildren().add(rec1);
            root.getChildren().add(rec2);
            root.getChildren().add(rec3);
            root.getChildren().add(rec4);

            HitBox hb1 = new HitBox((short) 50, (short) 50, new MotionPoint(600, 200, new Vector2D(), new Vector2D(-10, 0), new RotationParameters()), new RotationParameters(0, -2, 0));
            HitBox hb2 = new HitBox((short) 50, (short) 50, new MotionPoint(600, 700, new Vector2D(), new Vector2D(0, -5), new RotationParameters()), new RotationParameters());
            HitBox hb3 = new HitBox((short) 50, (short) 50, new MotionPoint(1000, 200, new Vector2D(), new Vector2D(), new RotationParameters()), new RotationParameters(0, 5, 0));
            HitBox hb4 = new HitBox((short) 50, (short) 50, new MotionPoint(500, 500, new Vector2D(), new Vector2D(), new RotationParameters(0.05f, 0, 0)), new RotationParameters());

            rec1.xProperty().bind(hb1.getCenterPoint().xProperty());
            rec1.yProperty().bind(hb1.getCenterPoint().yProperty());
            rec2.yProperty().bind(hb2.getCenterPoint().yProperty());
            rec2.xProperty().bind(hb2.getCenterPoint().xProperty());

            rec3.rotateProperty().bind(hb3.getSelfRotationParameters().angleProperty().multiply(360).divide(Math.PI).divide(2));
            rec1.rotateProperty().bind(hb1.getSelfRotationParameters().angleProperty().multiply(360).divide(Math.PI).divide(2));

            hb4.setOrigin(new MotionPoint(300, 300));
            Rotate rotationRec4 = new Rotate(0, hb4.getOrigin().getX(), hb4.getOrigin().getY());
            rec4.getTransforms().add(rotationRec4);
            rotationRec4.angleProperty().bind(hb4.getCenterPoint().getRotationParameters().angleProperty().multiply(360).divide(Math.PI).divide(2));
            rotationRec4.pivotXProperty().bind(hb4.getOrigin().xProperty());
            rotationRec4.pivotYProperty().bind(hb4.getOrigin().yProperty());
            hb4.getOrigin().setVelocity(2);


            Circle c = new Circle(300, 300, 5);
            root.getChildren().add(c);
            c.centerXProperty().bind(hb4.getOrigin().xProperty());
            c.centerYProperty().bind(hb4.getOrigin().yProperty());

            servLineaire(hb1);
            servLineaire2(hb2);
            servRota1(hb3);
            servRota1(hb1);
            servRota2(hb4);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public void servLineaire(HitBox hb) {
        Service serv = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Void call() throws Exception {
                        while (true) {
                            if (MathUtilitaires.round(hb.getCenterPoint().getVelocity() * Math.cos(hb.getCenterPoint().direction())) == -50) {
                                hb.getCenterPoint().setAccelerationAngle(0);
                            }
                            if (MathUtilitaires.round(hb.getCenterPoint().getVelocity() * Math.cos(hb.getCenterPoint().direction())) == 50)
                                hb.getCenterPoint().setAccelerationAngle(Math.PI);
                            hb.getCenterPoint().move();
                            Thread.sleep(50);
                        }
                    }
                };

            }
        };
        serv.start();
    }

    public void servLineaire2(HitBox hb) {
        Service serv = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Void call() throws Exception {
                        while (true) {
                            if (MathUtilitaires.round(hb.getCenterPoint().getVelocity() * Math.sin(hb.getCenterPoint().direction())) == -50) {
                                hb.getCenterPoint().setAccelerationAngle(Math.PI / 2);
                            }
                            if (MathUtilitaires.round(hb.getCenterPoint().getVelocity() * Math.sin(hb.getCenterPoint().direction())) == 50)
                                hb.getCenterPoint().setAccelerationAngle(Math.PI * 3 / 2);
                            hb.getCenterPoint().move();
                            Thread.sleep(50);
                        }
                    }
                };

            }
        };
        serv.start();
    }

    public void servRota1(HitBox hb) {
        Service serv = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Void call() throws Exception {
                        while (true) {
                            hb.rotateSelf();
                            Thread.sleep(50);
                        }
                    }
                };

            }
        };
        serv.start();
    }

    public void servRota2(HitBox hb) {
        Service serv = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Void call() throws Exception {
                        while (true) {
                            hb.getCenterPoint().rotate(hb.getOrigin());
                            hb.getOrigin().move();
                            Thread.sleep(50);
                        }
                    }
                };
            }
        };
        serv.start();
    }

}
