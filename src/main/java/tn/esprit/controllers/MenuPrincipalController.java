package tn.esprit.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuPrincipalController {

    private Stage primaryStage;

    // Méthode pour afficher les topics
    public void handleAfficherTopics() {
        chargerVue("/AfficherTopic.fxml");
    }

    // Méthode pour afficher les posts
    public void handleAfficherPosts() {
        chargerVue("/AfficherPost.fxml");
    }

    // Méthode pour afficher les commentaires
    public void handleAfficherCommentaires() {
        chargerVue("/AfficherCommentaire.fxml");
    }

    // Méthode pour charger une vue FXML
    private void chargerVue(String cheminFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(cheminFXML));
            Parent root = loader.load();

            // Récupérer le contrôleur pour initialisation si nécessaire
            // Exemple : MenuPrincipalController controller = loader.getController();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            // Gestion de l'exception avec alerte à l'utilisateur
            showAlert("Erreur de chargement", "Impossible de charger la vue", "Une erreur est survenue : " + e.getMessage());
        }
    }

    // Méthode pour afficher une alerte d'erreur
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Méthode pour définir le stage principal
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
