package tn.esprit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.esprit.controllers.TopicController;

public class MainAPP extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherTopic.fxml"));
        Parent root = loader.load();

        TopicController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);

        primaryStage.setTitle("Gestion des Topics");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}