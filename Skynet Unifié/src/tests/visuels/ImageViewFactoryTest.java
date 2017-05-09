package tests.visuels;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import modele.elements.BoxFactory;
import modele.elements.hitbox.HitBox;


public class ImageViewFactoryTest extends Application {

    BoxFactory bf = new BoxFactory();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        root.setPrefSize(100,100);
        Scene s = new Scene(root);
        primaryStage.setScene(s);
        primaryStage.show();

        HitBox hb = new HitBox((short) 32, (short) 20, 50, 50);

        Rectangle r = bf.getInstance(hb);
        root.getChildren().add(r);

        ImageView iv = bf.getInstanceImage(hb, "images/joueur.png");

        root.getChildren().add(iv);

        Circle c = new Circle(hb.getCenterPoint().getX(),hb.getCenterPoint().getY(),5);
        c.setFill(Paint.valueOf("red"));
        root.getChildren().add(c);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
