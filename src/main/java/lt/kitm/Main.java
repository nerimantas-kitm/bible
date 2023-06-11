package lt.kitm;

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
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/prisijungimo_langas.fxml"));
        primaryStage.setTitle("Bible");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }
}