package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.entities.Commentaire;
import tn.esprit.services.CommentaireService;
import tn.esprit.util.BadWordsFilter; // ✅ Correct import

public class CreerCommentaire {

    @FXML private TextField contentField;
    @FXML private TextField postIdField;

    private AfficherCommentaireController parentController;
    private final CommentaireService service = new CommentaireService();

    public void setParentController(AfficherCommentaireController controller) {
        this.parentController = controller;
    }

    @FXML
    private void handleAdd() {
        try {
            String content = contentField.getText().trim();

            // Validate input
            if (!validateInput()) return;
            if (!validateContent(content)) return;

            // Create Commentaire object
            Commentaire c = new Commentaire(content, Integer.parseInt(postIdField.getText().trim()));

            // Add to service
            if (service.add(c)) {
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Commentaire ajouté");
                parentController.refresh();  // Refresh parent controller's view
                closeWindow();
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de l'ajout");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "ID post invalide");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", e.getMessage());
        }
    }

    private boolean validateInput() {
        if (contentField.getText() == null || contentField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le contenu ne peut pas être vide");
            return false;
        }

        if (postIdField.getText() == null || postIdField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "L'ID du post est obligatoire");
            return false;
        }

        try {
            Integer.parseInt(postIdField.getText().trim());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "L'ID du post doit être un nombre valide");
            return false;
        }

        return true;
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void closeWindow() {
        // Close the current window
        ((Stage) contentField.getScene().getWindow()).close();
    }

    private boolean validateContent(String text) {
        if (BadWordsFilter.containsBadWords(text)) {
            System.out.println("Bad words detected!");
            showCustomBadWordsWindow();
            return false;
        }
        return true;
    }

    private void showCustomBadWordsWindow() {
        Stage stage = new Stage();
        stage.setTitle("Contenu Inapproprié");

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        Label label = new Label("Votre texte contient des mots inappropriés !");
        layout.getChildren().add(label);

        Button closeButton = new Button("Fermer");
        closeButton.setOnAction(e -> stage.close());
        layout.getChildren().add(closeButton);

        Scene scene = new Scene(layout, 300, 150);
        stage.setScene(scene);
        stage.show();
    }

}
