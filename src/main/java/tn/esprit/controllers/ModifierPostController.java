package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.entities.Post;
import tn.esprit.services.PostService;

public class ModifierPostController {

    @FXML private TextField titreField;
    @FXML private TextArea contenuField;

    private Post currentPost;
    private AfficherPostController afficherPostController;
    private final PostService postService = new PostService();

    public void setPostData(Post post) {
        this.currentPost = post;
        titreField.setText(post.getTitle());
        contenuField.setText(post.getContent());
    }

    public void setAfficherPostController(AfficherPostController controller) {
        this.afficherPostController = controller;
    }

    @FXML
    private void handleUpdate() {
        if (validateInput()) {
            currentPost.setTitle(titreField.getText());
            currentPost.setContent(contenuField.getText());
            postService.update(currentPost);

            afficherPostController.loadPosts();
            closeWindow();
        }
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private boolean validateInput() {
        if (titreField.getText().isEmpty() || contenuField.getText().isEmpty()) {
            showAlert("Erreur", "Tous les champs sont obligatoires");
            return false;
        }
        return true;
    }

    private void closeWindow() {
        Stage stage = (Stage) titreField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}