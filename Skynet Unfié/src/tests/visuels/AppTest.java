package tests_visuels;


import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import modele.elements.HitBox;
import modele.elements.MotionPoint;
import modele.elements.RotationParameters;
import modele.math.Vector2D;
import utilitaires.MathUtilitaires;

import java.util.ArrayList;


public class AppTest extends Application {
    private Stage mainStage;
    private ArrayList<HitBox> hbList = new ArrayList<>();

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
            Rectangle rec4 = new Rectangle(475, 475, 50, 50);


            root.getChildren().add(rec1);
            root.getChildren().add(rec2);
            root.getChildren().add(rec3);
            root.getChildren().add(rec4);

            HitBox hb1 = new HitBox((short) 50, (short) 50, new MotionPoint(800, 200, new Vector2D(), new Vector2D(10, 0), new RotationParameters()), new RotationParameters(0, -2, 0));
            HitBox hb2 = new HitBox((short) 50, (short) 50, new MotionPoint(600, 200, new Vector2D(), new Vector2D(0, 5), new RotationParameters()), new RotationParameters());
            HitBox hb3 = new HitBox((short) 50, (short) 50, new MotionPoint(1000, 200, new Vector2D(), new Vector2D(), new RotationParameters()), new RotationParameters(0, 5, 0));
            HitBox hb4 = new HitBox((short) 50, (short) 50, new MotionPoint(500, 500, new Vector2D(), new Vector2D(), new RotationParameters(0, 0.0001f, 0)), new RotationParameters());
            hbList.add(hb1);
            hbList.add(hb2);
            hbList.add(hb3);
            hbList.add(hb4);

            hb1.setHitsOthers(true);
            hb2.setHitsOthers(true);
            hb3.setHitsOthers(true);
            hb4.setHitsOthers(true);

            Circle c1 = new Circle(5, Paint.valueOf("Green"));
            Circle c2 = new Circle(5, Paint.valueOf("Green"));
            Circle c3 = new Circle(5, Paint.valueOf("Green"));
            Circle c4 = new Circle(5, Paint.valueOf("Green"));

            root.getChildren().add(c1);
            root.getChildren().add(c2);
            root.getChildren().add(c3);
            root.getChildren().add(c4);

            c1.centerXProperty().bind(hb1.getCenterPoint().xProperty());
            c1.centerYProperty().bind(hb1.getCenterPoint().yProperty());
            c2.centerXProperty().bind(hb2.getCenterPoint().xProperty());
            c2.centerYProperty().bind(hb2.getCenterPoint().yProperty());
            c3.centerXProperty().bind(hb3.getCenterPoint().xProperty());
            c3.centerYProperty().bind(hb3.getCenterPoint().yProperty());
            c4.centerXProperty().bind(hb4.getCenterPoint().xProperty());
            c4.centerYProperty().bind(hb4.getCenterPoint().yProperty());

            rec1.xProperty().bind(hb1.getCenterPoint().xProperty().subtract(hb1.getWidth() / 2.0));
            rec1.yProperty().bind(hb1.getCenterPoint().yProperty().subtract(hb1.getHeight() / 2.0));
            rec2.yProperty().bind(hb2.getCenterPoint().yProperty().subtract(hb2.getWidth() / 2.0));
            rec2.xProperty().bind(hb2.getCenterPoint().xProperty().subtract(hb2.getHeight() / 2.0));

            rec3.rotateProperty().bind(hb3.getSelfRotationParameters().angleProperty().multiply(360).divide(Math.PI).divide(2));
            rec1.rotateProperty().bind(hb1.getSelfRotationParameters().angleProperty().multiply(360).divide(Math.PI).divide(2));
            rec3.xProperty().bind(hb3.getCenterPoint().xProperty().subtract(hb3.getWidth() / 2.0));
            rec3.yProperty().bind(hb3.getCenterPoint().yProperty().subtract(hb3.getHeight() / 2.0));

            hb4.setOrigin(new MotionPoint(300, 300));
            hb4.getOrigin().setVelocity(0.5f);
            hb4.getOrigin().setDirection(Math.PI / 4.0);
            hb4.getCenterPoint().setVelocity(0.5f);
            hb4.getCenterPoint().setDirection(Math.PI / 4.0);
            //hb4.getSelfRotationParameters().setAngularAcceleration(0.0001f);

            rec4.xProperty().bind(hb4.getCenterPoint().xProperty().subtract(hb4.getWidth() / 2.0));
            rec4.yProperty().bind(hb4.getCenterPoint().yProperty().subtract(hb4.getHeight() / 2.0));

            rec4.rotateProperty().bind(hb4.getSelfRotationParameters().angleProperty().multiply(360).divide(2 * Math.PI));


            Circle c = new Circle(300, 300, 5);
            root.getChildren().add(c);
            c.centerXProperty().bind(hb4.getOrigin().xProperty());
            c.centerYProperty().bind(hb4.getOrigin().yProperty());


            servLineaire(hb1);
            servLineaire2(hb2);
            servRota1(hb3);
            servRota1(hb1);
            servRota2(hb4);
            servCollision();

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
                            hb.getOrigin().move();
                            hb.getCenterPoint().move();
                            hb.getCenterPoint().rotate(hb.getOrigin());
                            hb.rotateSelf();
                            Thread.sleep(50);
                        }
                    }
                };
            }
        };
        serv.start();
    }

    public void servCollision() {
        Service serv = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Object call() throws Exception {
                        while (true) {
                            for (int i = 0; i < hbList.size(); i++) {
                                for (int j = i + 1; j < hbList.size(); j++) {
                                    if (hbList.get(i).checkCollision(hbList.get(j)) == true)
                                        System.out.println("collision");
                                }
                            }
                            Thread.sleep(20);
                        }
                    }
                };
            }
        };
        serv.start();
    }

}
