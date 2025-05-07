package tn.esprit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.esprit.controllers.AfficherPostController; // Modifier l'import du contrôleur

public class MainAPP extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Charger AfficherPost.fxml au lieu de MenuPrincipal.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPost.fxml"));
            Parent root = loader.load();

            // Obtenir le contrôleur AfficherPostController
            AfficherPostController controller = loader.getController();
            controller.setPrimaryStage(primaryStage); // Si votre contrôleur a cette méthode

            Scene scene = new Scene(root, 1200, 800); // Dimensions adaptées à l'interface
            primaryStage.setTitle("Gestion des Posts");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            System.err.println("Erreur au démarrage de l'application:");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}