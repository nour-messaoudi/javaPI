package tn.esprit.controllers;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.scene.layout.VBox; // Import VBox
import tn.esprit.entities.Commentaire;
import tn.esprit.entities.Post;
import tn.esprit.services.CommentaireService;
import tn.esprit.util.BadWordsFilter;

public class AfficherCommentaireControllere {

    @FXML
    private TableView<Commentaire> commentsTableView;

    @FXML
    private TableColumn<Commentaire, String> contentColumn;

    @FXML
    private TableColumn<Commentaire, String> dateColumn;

    @FXML
    private TextArea commentInputField;

    private final CommentaireService commentaireService = new CommentaireService();

    private Post selectedPost;

    public void setSelectedPost(Post post) {
        this.selectedPost = post;
        loadComments(); // Load comments when the post is selected
    }

    @FXML
    public void initialize() {
        setupTableColumns();
    }

    private void setupTableColumns() {
        // Configure the content column
        contentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContent()));

        // Configure the date column
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCreatedAt().toString()));
    }

    private void loadComments() {
        ObservableList<Commentaire> comments = FXCollections.observableArrayList();

        // Fetch comments for the selected post
        if (selectedPost != null) {
            comments.addAll(commentaireService.getCommentsByPostId(selectedPost.getId(), 50, 0)); // Limit to 50, starting at offset 0
        }

        // Set the items for the TableView
        commentsTableView.setItems(comments);
    }

    @FXML
    private void handleAddComment() {
        String content = commentInputField.getText().trim();

        // Validate input
        if (content.isEmpty()) {
            showAlert("Erreur", "Le commentaire ne peut pas être vide.");
            return;
        }

        // Check for bad words
        if (BadWordsFilter.containsBadWords(content)) {
            // Show custom window if bad words are detected
            showCustomBadWordsWindow();
            return; // Prevent adding the comment
        }

        // Create new Commentaire and add it to the service
        Commentaire newComment = new Commentaire(content, selectedPost.getId());
        if (commentaireService.add(newComment)) {
            showAlert("Succès", "Commentaire ajouté avec succès.");
            commentInputField.clear();
            loadComments(); // Refresh the comments table
        } else {
            showAlert("Erreur", "Impossible d'ajouter le commentaire.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showCustomBadWordsWindow() {
        // Ensure that the window opens on the JavaFX application thread
        Platform.runLater(() -> {
            Stage stage = new Stage();
            stage.setTitle("Contenu Inapproprié");

            // Create VBox layout
            VBox layout = new VBox(10);
            layout.setAlignment(Pos.CENTER);

            // Add Label to VBox
            Label label = new Label("Votre texte contient des mots inappropriés !");
            layout.getChildren().add(label);

            // Add Close button to VBox
            Button closeButton = new Button("Fermer");
            closeButton.setOnAction(e -> stage.close());
            layout.getChildren().add(closeButton);

            // Create Scene with VBox layout
            Scene scene = new Scene(layout, 300, 150);
            stage.setScene(scene);

            // Show the window
            stage.show();

            // Ensure the window comes to the front
            stage.toFront();
        });
    }
}
