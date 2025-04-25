package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.entities.Commentaire;
import tn.esprit.entities.Post;
import tn.esprit.services.CommentaireService;
import tn.esprit.services.PostService;

import java.sql.Timestamp;
import java.util.List;

import static com.mysql.cj.protocol.a.MysqlTextValueDecoder.getTimestamp;
import static javax.swing.UIManager.getInt;
import static javax.swing.UIManager.getString;

public class CreerCommentaire {

    @FXML private TextArea contenuField;
    @FXML private TextField auteurField;
    @FXML private ComboBox<Post> postComboBox;

    private Stage dialogStage;
    private final CommentaireService commentaireService = new CommentaireService();
    private final PostService postService = new PostService();

    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }

    @FXML
    private void initialize() {
        List<Post> posts = postService.getAllPosts();
        postComboBox.getItems().addAll(posts);
    }

    @FXML
    private void handleCreate() {
        if (validateInput()) {
            Commentaire commentaire = new Commentaire(getInt("id"),getString("contenu"),getString("auteur"), getTimestamp("dateCreation").toLocalDateTime(),getInt("topic_id"));
            commentaire.setContenu(contenuField.getText());
            commentaire.setAuteur(auteurField.getText());
            commentaire.setPost(postComboBox.getValue());

            commentaireService.ajouter(commentaire);
            dialogStage.close();
        }
    }

    private Timestamp getTimestamp(String dateCreation) {
        return null;
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean validateInput() {
        StringBuilder errors = new StringBuilder();

        if (contenuField.getText().isEmpty()) {
            errors.append("- Contenu obligatoire\n");
        }
        if (auteurField.getText().isEmpty()) {
            errors.append("- Auteur obligatoire\n");
        }
        if (postComboBox.getValue() == null) {
            errors.append("- Post associé obligatoire\n");
        }

        if (errors.length() > 0) {
            showAlert("Erreur de validation", errors.toString());
            return false;
        }
        return true;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(dialogStage);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
