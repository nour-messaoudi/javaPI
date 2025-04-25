package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tn.esprit.entities.Post;
import tn.esprit.services.PostService;

public class SupprimerPostController {
    @FXML private Label confirmationLabel;
    @FXML private Button confirmerButton;
    @FXML private Button annulerButton;

    private Post post;
    private final PostService postService = new PostService();
    private PostController postController;

    public void setPostData(Post post) {
        this.post = post;
        confirmationLabel.setText("Voulez-vous vraiment supprimer le post \"" + post.getTitre() + "\" ?");
    }

    public void setPostController(PostController postController) {
        this.postController = postController;
    }

    @FXML
    private void confirmerSuppression() {
        postService.delete(post.getId());
        postController.refreshPosts();
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