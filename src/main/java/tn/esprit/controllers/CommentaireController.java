package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import tn.esprit.entities.Commentaire;
import tn.esprit.services.CommentaireService;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class CommentaireController {

    @FXML private TableView<Commentaire> commentaireTable;
    @FXML private TableColumn<Commentaire, String> contenuCol;
    @FXML private TableColumn<Commentaire, String> dateCol;
    @FXML private TextArea commentaireInput;
    @FXML private Button ajouterBtn;

    private final CommentaireService commentaireService = new CommentaireService();

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @FXML
    private void initialize() {
        contenuCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getContent()));
        dateCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getCreatedAt().format(formatter))
        );
    }

    @FXML
    private void ajouterCommentaire() {
        String contenu = commentaireInput.getText().trim();
        if (contenu.isEmpty()) {
            showAlert("Champ vide", "Veuillez saisir un commentaire.");
            return;
        }

        // Créer un nouveau commentaire avec les nouveaux attributs
        Commentaire commentaire = new Commentaire();
        commentaire.setContent(contenu);
        commentaire.setIdPost(1); // Remplacer par l'id du post réel auquel le commentaire appartient
        commentaire.setCreatedAt(OffsetDateTime.now().toLocalDateTime());  // Date actuelle
        commentaire.setId(null); // L'ID sera généré par la base de données

        // Appel du service pour ajouter le commentaire
        commentaireService.add(commentaire);

        commentaireInput.clear();
        loadCommentaires();
    }

    private void loadCommentaires() {
        ObservableList<Commentaire> commentaires = FXCollections.observableArrayList(
                commentaireService.getCommentairesParPost(1)  // Remplacer l'id ici par celui du post réel
        );
        commentaireTable.setItems(commentaires);
    }

    private void showAlert(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void refreshCommentaires() {
        loadCommentaires();  // Recharger les commentaires
    }
}
