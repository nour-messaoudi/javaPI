package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import tn.esprit.entities.Commentaire;
import tn.esprit.services.CommentaireService;
import tn.esprit.util.BadWordsFilter;  // Import your custom BadWordsFilter

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class CommentaireController {

    @FXML private TableView<Commentaire> commentaireTable;
    @FXML private TableColumn<Commentaire, String> contenuCol;
    @FXML private TableColumn<Commentaire, String> dateCol;
    @FXML private TextArea commentaireInput;
    @FXML private Button ajouterBtn;

    private final CommentaireService commentaireService = new CommentaireService();
    private int currentPage = 0; // Current page for pagination
    private final int pageSize = 50; // Number of comments per page (adjust as needed)
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @FXML
    private void initialize() {
        contenuCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getContent()));
        dateCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getCreatedAt().format(formatter))
        );
        loadCommentaires();  // Initial loading of comments
    }

    @FXML
    private void ajouterCommentaire() {
        String contenu = commentaireInput.getText().trim();
        if (contenu.isEmpty()) {
            showAlert("Champ vide", "Veuillez saisir un commentaire.");
            return;
        }

        // Check for bad words in the content
        if (BadWordsFilter.containsBadWords(contenu)) {
            showCustomBadWordsWindow();
            return;
        }

        // Créer un nouveau commentaire avec les nouveaux attributs
        Commentaire commentaire = new Commentaire();
        commentaire.setContent(contenu);
        commentaire.setIdPost(1); // Remplacer par l'id du post réel auquel le commentaire appartient
        commentaire.setCreatedAt(OffsetDateTime.now().toLocalDateTime());  // Date actuelle
        commentaire.setId(null); // L'ID sera généré par la base de données

        // Appel du service pour ajouter le commentaire
        commentaireService.add(commentaire);

        commentaireInput.clear();
        loadCommentaires();  // Refresh the list of comments after adding a new one
    }

    private void loadCommentaires() {
        // Fetch limited comments for pagination
        ObservableList<Commentaire> commentaires = FXCollections.observableArrayList(
                commentaireService.getCommentsByPostId(1, pageSize, currentPage * pageSize)  // Fetch 50 comments per page
        );
        commentaireTable.setItems(commentaires);
    }

    private void showAlert(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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

    // Method to go to the next page of comments (pagination)
    @FXML
    private void nextPage() {
        currentPage++;
        loadCommentaires();  // Load the next page of comments
    }

    // Method to go to the previous page of comments (pagination)
    @FXML
    private void previousPage() {
        if (currentPage > 0) {
            currentPage--;
            loadCommentaires();  // Load the previous page of comments
        }
    }

    public void refreshCommentaires() {
        loadCommentaires();  // Reload the comments manually (if needed)
    }
}
