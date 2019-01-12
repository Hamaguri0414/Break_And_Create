import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.util.Duration;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class TitleController implements Initializable {
    public Pane Title;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        javafx.application.Platform.runLater(() -> {
            Title.getScene().setOnKeyReleased(key_event -> {
                keyAction2(key_event);
            });
        });
    }

    public void keyAction2(KeyEvent event){
        KeyCode key = event.getCode();
        if (key == KeyCode.ENTER){
            try {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("MapGame.fxml")));
            MapGame.setScene(scene);
		    } catch (Exception e) {
			e.printStackTrace();
			}
        }
    }
}