package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entities.Commentaire;
import tn.esprit.services.CommentaireService;

public class ModifierCommentaireController {
    @FXML private TextField contentField;
    @FXML private TextField postIdField;

    private Commentaire commentaire;
    private AfficherCommentaireController parentController;
    private final CommentaireService service = new CommentaireService();

    public void setCommentaire(Commentaire commentaire) {
        this.commentaire = commentaire;
        contentField.setText(commentaire.getContent());
        postIdField.setText(String.valueOf(commentaire.getPostId()));
    }

    public void setParentController(AfficherCommentaireController controller) {
        this.parentController = controller;
    }

    @FXML
    private void handleUpdate() {
        try {
            if (validateInput()) {
                commentaire.setContent(contentField.getText());
                commentaire.setPostId(Integer.parseInt(postIdField.getText()));
                service.update(commentaire);
                parentController.refresh();
                closeWindow();
            }
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue: " + e.getMessage());
        }
    }

    private boolean validateInput() {
        if (contentField.getText().isEmpty() || postIdField.getText().isEmpty()) {
            showAlert("Erreur", "Tous les champs sont obligatoires");
            return false;
        }

        try {
            Integer.parseInt(postIdField.getText());
        } catch (NumberFormatException e) {
            showAlert("Erreur", "L'ID du post doit être un nombre valide");
            return false;
        }

        return true;
    }

    @FXML
    private void closeWindow() {
        ((Stage) contentField.getScene().getWindow()).close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}