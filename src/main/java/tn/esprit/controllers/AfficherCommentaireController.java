package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AfficherCommentaireController {

    @FXML
    private ListView<String> commentaireListView;

    public void initialize() {
        // Remplir la ListView avec quelques commentaires d'exemple
        commentaireListView.getItems().addAll(
                "Super intéressant !",
                "Je ne suis pas d'accord avec ce point.",
                "Merci pour le partage !"
        );
    }

    @FXML
    private void ajouterCommentaire() {
        // Logique pour ajouter un commentaire
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Ajouter Commentaire");
        alert.setHeaderText(null);
        alert.setContentText("Un nouveau commentaire a été ajouté !");
        alert.showAndWait();

        // Ajouter un nouveau commentaire dans la liste
        commentaireListView.getItems().add("Nouveau commentaire ajouté.");
    }
}
