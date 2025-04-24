package tn.esprit.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuPrincipalController {

    public void handleAfficherTopics() {
        chargerVue("/afficherTopic.fxml");
    }

    public void handleAfficherPosts() {
        chargerVue("/afficherPost.fxml");
    }

    public void handleAfficherCommentaires() {
        chargerVue("/afficherCommentaire.fxml");
    }

    private void chargerVue(String cheminFXML) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(cheminFXML));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPrimaryStage(Stage primaryStage) {
    }
}
