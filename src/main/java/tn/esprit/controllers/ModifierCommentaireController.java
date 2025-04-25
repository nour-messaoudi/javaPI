package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tn.esprit.entities.Commentaire;
import tn.esprit.services.CommentaireService;

import java.io.IOException;

public class ModifierCommentaireController {

    @FXML private TextArea contenuField;
    @FXML private TextField auteurField;

    private Commentaire currentCommentaire;
    private Stage primaryStage;
    private CommentaireController commentaireController;
    private final CommentaireService commentaireService = new CommentaireService();

    public void setCommentaireData(Commentaire commentaire) {
        this.currentCommentaire = commentaire;
        contenuField.setText(commentaire.getContenu());
        auteurField.setText(commentaire.getAuteur());
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void setCommentaireController(CommentaireController controller) {
        this.commentaireController = controller;
    }

    @FXML
    private void handleUpdate() {
        if (validateInput()) {
            currentCommentaire.setContenu(contenuField.getText());
            currentCommentaire.setAuteur(auteurField.getText());

            commentaireService.update(currentCommentaire);
            commentaireController.refreshCommentaires();
            primaryStage.close();
        }
    }

    @FXML
    private void handleDelete() {
        if (currentCommentaire != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/SupprimerCommentaire.fxml"));
                Parent root = loader.load();

                SupprimerCommentaireController controller = loader.getController();
                controller.setCommentaireData(currentCommentaire);
                controller.setCommentaireController(commentaireController);

                Stage dialogStage = new Stage();
                dialogStage.setTitle("Confirmation de suppression");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(primaryStage);
                dialogStage.setScene(new Scene(root));
                dialogStage.showAndWait();

                primaryStage.close();
            } catch (IOException e) {
                showAlert("Erreur", "Impossible d'ouvrir la confirmation", e.getMessage());
            }
        }
    }

    @FXML
    private void handleCancel() {
        primaryStage.close();
    }

    private boolean validateInput() {
        if (contenuField.getText().isEmpty() || auteurField.getText().isEmpty()) {
            showAlert("Erreur", "Champs obligatoires", "Tous les champs doivent être remplis");
            return false;
        }
        return true;
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
