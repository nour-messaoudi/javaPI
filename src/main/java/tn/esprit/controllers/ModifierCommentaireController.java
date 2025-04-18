package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.entities.Commentaire;
import tn.esprit.services.CommentaireService;

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
        currentCommentaire.setContenu(contenuField.getText());
        currentCommentaire.setAuteur(auteurField.getText());

        commentaireService.update(currentCommentaire);
        commentaireController.refreshCommentaires();
        primaryStage.close();
    }

    @FXML
    private void handleDelete() {
        // Implémentez la suppression comme pour les autres entités
    }

    @FXML
    private void handleCancel() {
        primaryStage.close();
    }
}