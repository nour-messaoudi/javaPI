package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.entities.Post;
import tn.esprit.services.PostService;
import tn.esprit.util.BadWordsFilter; // ✅ Correct class for filtering

public class CreerPost {

    @FXML private TextField titreField;
    @FXML private TextArea contenuField;
    @FXML private Label titreError;
    @FXML private Label contentError;

    private AfficherPostController afficherPostController;
    private final PostService postService = new PostService();

    public void setAfficherPostController(AfficherPostController controller) {
        this.afficherPostController = controller;
    }

    @FXML
    public void initialize() {
        // Validation en temps réel
        titreField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.trim().isEmpty() || newVal.trim().matches("^\\s*$")) {
                titreError.setText("Le titre ne peut pas être vide ou contenir uniquement des espaces");
            } else {
                titreError.setText("");
            }
        });

        contenuField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.trim().isEmpty() || newVal.trim().matches("^\\s*$")) {
                contentError.setText("Le contenu ne peut pas être vide ou contenir uniquement des espaces");
            } else {
                contentError.setText("");
            }
        });
    }

    @FXML
    private void handleCreate() {
        String titre = titreField.getText().trim();
        String content = contenuField.getText().trim();

        // Validate inputs and check content for profanity
        if (!validateInput()) return;
        if (!validateContent(titre) || !validateContent(content)) return;

        Post newPost = new Post(titre, content);
        postService.add(newPost); // Create and add the new post
        afficherPostController.loadPosts(); // Reload the posts to display the new one
        closeWindow(); // Close the post creation window
    }

    @FXML
    private void handleCancel() {
        closeWindow(); // Close without saving the post
    }

    private boolean validateInput() {
        boolean isValid = true;

        String titre = titreField.getText().trim();
        if (titre.isEmpty() || titre.matches("^\\s*$")) {
            titreError.setText("Le titre ne peut pas être vide ou contenir uniquement des espaces");
            isValid = false;
        }

        String content = contenuField.getText().trim();
        if (content.isEmpty() || content.matches("^\\s*$")) {
            contentError.setText("Le contenu ne peut pas être vide ou contenir uniquement des espaces");
            isValid = false;
        }

        return isValid;
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

    private boolean validateContent(String text) {
        if (BadWordsFilter.containsBadWords(text)) {
            // Show custom window if bad words are detected
            showCustomBadWordsWindow();
            return false;
        }
        return true;
    }

    private void showCustomBadWordsWindow() {
        Stage stage = new Stage();
        stage.setTitle("Contenu Inapproprié");

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        Label label = new Label("Votre texte contient des mots inappropriés !");
        layout.getChildren().add(label);

        Button closeButton = new Button("Fermer");
        closeButton.setOnAction(e -> stage.close());
        layout.getChildren().add(closeButton);

        Scene scene = new Scene(layout, 300, 150);
        stage.setScene(scene);
        stage.show();
    }

}