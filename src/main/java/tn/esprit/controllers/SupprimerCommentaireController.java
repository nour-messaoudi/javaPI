package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tn.esprit.entities.Commentaire;
import tn.esprit.services.CommentaireService;

public class SupprimerCommentaireController {
    @FXML private Label confirmationLabel;
    @FXML private Button confirmerButton;
    @FXML private Button annulerButton;

    private Commentaire commentaire;
    private final CommentaireService commentaireService = new CommentaireService();
    private CommentaireController commentaireController;

    public void setCommentaireData(Commentaire commentaire) {
        this.commentaire = commentaire;
        confirmationLabel.setText("Voulez-vous vraiment supprimer ce commentaire : \"" + commentaire.getContenu() + "\" ?");
    }

    public void setCommentaireController(CommentaireController commentaireController) {
        this.commentaireController = commentaireController;
    }

    @FXML
    private void confirmerSuppression() {
        commentaireService.delete(commentaire.getId());
        commentaireController.refreshCommentaires();
        closeWindow();
    }

    @FXML
    private void annulerSuppression() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) confirmerButton.getScene().getWindow();
        stage.close();
    }
}
