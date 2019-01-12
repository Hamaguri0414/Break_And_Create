import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class MapGame extends Application {
    static Stage stage;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
	stage = primaryStage;
	primaryStage.setTitle("Break And Creat");
	//Pane myPane_top = (Pane)FXMLLoader.load(getClass().getResource("MapGame.fxml"));
    //Scene myScene = new Scene(myPane_top);
    //Scene myScene = new Scene((Pane)FXMLLoader.load(getClass().getResource("Title.fxml")));
    Scene myScene = new Scene(FXMLLoader.load(getClass().getResource("Title.fxml")));
    primaryStage.setScene(myScene);
	primaryStage.show();
    }
    
    public static void setScene(Scene title){
        stage.setScene(title);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
