package tn.esprit.controllers;

import javafx.fxml.FXML;
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
    private final CommentaireService service = new CommentaireService();
    private AfficherCommentaireController afficherController;

    public void setCommentaire(Commentaire commentaire) {
        this.commentaire = commentaire;
        // Adapter le message de confirmation pour correspondre à l'attribut content
        confirmationLabel.setText("Voulez-vous vraiment supprimer ce commentaire : \"" + commentaire.getContent() + "\" ?");
    }

    public void setAfficherCommentaireController(AfficherCommentaireController controller) {
        this.afficherController = controller;
    }

    @FXML
    private void confirmerSuppression() {
        // Suppression du commentaire par son ID
        service.delete(commentaire.getId());
        afficherController.refresh();  // Mise à jour de l'affichage
        close();
    }

    @FXML
    private void annulerSuppression() {
        close();
    }

    private void close() {
        Stage stage = (Stage) confirmerButton.getScene().getWindow();
        stage.close();
    }
}
