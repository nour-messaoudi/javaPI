package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.entities.Post;
import tn.esprit.entities.Topic;
import tn.esprit.services.PostService;
import tn.esprit.services.TopicService;

import java.util.List;

public class CreerPost {

    @FXML private TextField titreField;
    @FXML private TextArea contenuField;
    @FXML private TextField auteurField;
    @FXML private ComboBox<Topic> topicComboBox;

    private Stage dialogStage;
    private final PostService postService = new PostService();
    private final TopicService topicService = new TopicService();

    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }

    @FXML
    private void initialize() {
        List<Topic> topics = topicService.getAllTopics();
        topicComboBox.getItems().addAll(topics);
    }

    @FXML
    private void handleCreate() {
        if (validateInput()) {
            Post newPost = new Post();
            newPost.setTitre(titreField.getText());
            newPost.setContenu(contenuField.getText());
            newPost.setAuteur(auteurField.getText());
            newPost.setTopic(topicComboBox.getValue());

            postService.ajouter(newPost);
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean validateInput() {
        StringBuilder errors = new StringBuilder();

        if (titreField.getText().isEmpty()) {
            errors.append("- Titre obligatoire\n");
        }
        if (contenuField.getText().isEmpty()) {
            errors.append("- Contenu obligatoire\n");
        }
        if (auteurField.getText().isEmpty()) {
            errors.append("- Auteur obligatoire\n");
        }
        if (topicComboBox.getValue() == null) {
            errors.append("- Sujet (Topic) obligatoire\n");
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

    public void setPostController(PostController postController) {
        // Si besoin : postController.refreshPosts() après ajout
    }
}
