import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception{
        setUpStage(stage);
    }

    private void setUpStage(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/LoginScene.fxml"));
        stage.setTitle("Resuplify");
        stage.setScene(new Scene(root));
        stage.show();
    }

}