package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import tn.esprit.entities.Commentaire;
import tn.esprit.entities.Topic;
import tn.esprit.services.CommentaireService;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import static com.mysql.cj.protocol.a.MysqlTextValueDecoder.getTimestamp;
import static javax.swing.UIManager.getInt;
import static javax.swing.UIManager.getString;

public class CommentaireController {

    @FXML private TableView<Commentaire> commentaireTable;
    @FXML private TableColumn<Commentaire, String> contenuCol;
    @FXML private TableColumn<Commentaire, String> auteurCol;
    @FXML private TableColumn<Commentaire, String> dateCol;
    @FXML private TextArea commentaireInput;
    @FXML private Button ajouterBtn;

    private final CommentaireService commentaireService = new CommentaireService();
    private Topic topicActuel;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public void setTopic(Topic topic) {
        this.topicActuel = topic;
        loadCommentaires();
    }

    @FXML
    private void initialize() {
        contenuCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getContenu()));
        auteurCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getAuteur()));
        dateCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getDateCreation().format(formatter))
        );
    }

    @FXML
    private void ajouterCommentaire() {
        String contenu = commentaireInput.getText().trim();
        if (contenu.isEmpty()) {
            showAlert("Champ vide", "Veuillez saisir un commentaire.");
            return;
        }

        Commentaire commentaire = new Commentaire(getInt("id"),getString("contenu"),getString("auteur"), getTimestamp("dateCreation").toLocalDateTime(), getInt("topic_id"));
        commentaire.setContenu(contenu);
        commentaire.setAuteur("Utilisateur"); // À remplacer par l’utilisateur courant si nécessaire
        commentaire.setTopic(topicActuel);

        commentaireService.ajouter(commentaire);
        commentaireInput.clear();
        loadCommentaires();
    }

    private OffsetDateTime getTimestamp(String dateCreation) {
        return null;
    }

    private void loadCommentaires() {
        if (topicActuel != null) {
            ObservableList<Commentaire> commentaires = FXCollections.observableArrayList(
                    commentaireService.getCommentairesParTopic(topicActuel.getId())
            );
            commentaireTable.setItems(commentaires);
        }
    }

    private void showAlert(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void refreshCommentaires() {
    }
}
