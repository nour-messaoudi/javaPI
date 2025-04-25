package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.entities.Post;
import tn.esprit.services.PostService;

public class ModifierPostController {

    @FXML private TextField titreField;
    @FXML private TextArea contenuField;
    @FXML private TextField auteurField;

    private Post currentPost;
    private Stage primaryStage;
    private final PostService postService = new PostService();

    public void setPostData(Post post) {
        this.currentPost = post;
        titreField.setText(post.getTitre());
        contenuField.setText(post.getContenu());
        auteurField.setText(post.getAuteur());
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    private void handleUpdate() {

    }

    @FXML
    private void handleCancel() {
        primaryStage.close();
    }

    private boolean validateInput() {
        if (titreField.getText().isEmpty() || contenuField.getText().isEmpty() || auteurField.getText().isEmpty()) {
            showAlert("Erreur", "Tous les champs sont obligatoires !");
            return false;
        }
        return true;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setPostController(PostController postController) {
    }
}