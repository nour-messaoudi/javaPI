package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entities.Commentaire;
import tn.esprit.services.CommentaireService;
import tn.esprit.util.MaConnexion;

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

            if (!validateInput()) return;
            if (!validateContent(content)) return;

            Commentaire c = new Commentaire(content, Integer.parseInt(postIdField.getText().trim()));

            if (service.add(c)) {
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Commentaire ajouté");
                parentController.refresh();
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
        ((Stage) contentField.getScene().getWindow()).close();
    }
    private boolean validateContent(String text) {
        if (MaConnexion.containsBadWords(text)) {
            showAlert("Contenu inapproprié", "Votre texte contient des mots inappropriés");
            return false;
        }
        return true;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}