
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
    private PostController postController;
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

    public void setPostController(PostController controller) {
        this.postController = controller;
    }

    @FXML
    private void handleUpdate() {
        currentPost.setTitre(titreField.getText());
        currentPost.setContenu(contenuField.getText());
        currentPost.setAuteur(auteurField.getText());

        postService.update(currentPost);
        postController.refreshPosts();
        primaryStage.close();
    }

    @FXML
    private void handleDelete() {
        // Implémentez la logique de suppression comme pour Topic
    }

    @FXML
    private void handleCancel() {
        primaryStage.close();
    }
}
