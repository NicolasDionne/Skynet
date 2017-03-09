package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class Main extends Application {

	private Stage mainStage;

	@Override
	public void start(Stage primaryStage) {
		try {
		    mainStage = primaryStage;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vue/VueInterface.fxml"));
            AnchorPane root = loader.load();
            Scene s = new Scene(root);
            mainStage.setScene(s);
            mainStage.show();
		} catch(Exception e) {
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

    public Main(){
    }
}
