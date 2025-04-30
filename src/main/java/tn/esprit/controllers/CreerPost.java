package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.entities.Post;
import tn.esprit.services.PostService;

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
        if (validateInput()) {
            Post newPost = new Post(
                    titreField.getText().trim(),
                    contenuField.getText().trim()
            );
            postService.add(newPost);

            afficherPostController.loadPosts();
            closeWindow();
        }
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private boolean validateInput() {
        boolean isValid = true;

        // Validation du titre
        String titre = titreField.getText().trim();
        if (titre.isEmpty() || titre.matches("^\\s*$")) {
            titreError.setText("Le titre ne peut pas être vide ou contenir uniquement des espaces");
            isValid = false;
        }

        // Validation du contenu
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
}