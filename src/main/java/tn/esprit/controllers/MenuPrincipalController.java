package tn.esprit.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuPrincipalController {
    private Stage primaryStage;

    public void handleAfficherPosts() {
        chargerVue("/AfficherPost.fxml", "Gestion des Posts");
    }

    public void handleAfficherCommentaires() {
        chargerVue("/AfficherCommentaire.fxml", "Gestion des Commentaires");
    }

    private void chargerVue(String cheminFXML, String titre) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(cheminFXML));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(titre);
            stage.setScene(new Scene(root));

            // Position relative à la fenêtre principale
            if (primaryStage != null) {
                stage.setX(primaryStage.getX() + 50);
                stage.setY(primaryStage.getY() + 50);
            }

            stage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Chargement impossible",
                    "Impossible de charger la vue: " + e.getMessage());
        }
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}