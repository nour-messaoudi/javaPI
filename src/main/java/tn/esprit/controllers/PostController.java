package tn.esprit.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tn.esprit.entities.Post;
import tn.esprit.services.PostService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PostController {
    // Références TableView
    @FXML private TableView<Post> postsTable;
    @FXML private TableColumn<Post, Integer> idCol;
    @FXML private TableColumn<Post, String> titreCol;
    @FXML private TableColumn<Post, String> contenuCol;
    @FXML private TableColumn<Post, String> auteurCol;
    @FXML private TableColumn<Post, String> dateCol;

    // Références Formulaire
    @FXML private TextField titreField;
    @FXML private TextArea contenuField;
    @FXML private TextField auteurField;
    @FXML private ComboBox<Integer> topicIdCombo;

    private final PostService postService = new PostService();
    private Stage primaryStage;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    private void initialize() {
        configureTableColumns();
        loadTopics();
        refreshPosts();
        setupTableSelectionListener();
    }

    private void configureTableColumns() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titreCol.setCellValueFactory(new PropertyValueFactory<>("titre"));
        contenuCol.setCellValueFactory(new PropertyValueFactory<>("contenu"));
        auteurCol.setCellValueFactory(new PropertyValueFactory<>("auteur"));
        dateCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDateCreation().format(dateFormatter))
        );

        // Ajustement automatique des colonnes
        postsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void loadTopics() {
        // À remplacer par votre service de topics
        topicIdCombo.getItems().addAll(1, 2, 3); // Exemple basique
    }

    @FXML
    private void showCreatePost() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CreerPost.fxml"));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Nouveau Post");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.setScene(new Scene(root));

            PostController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);
            controller.initializeForm();

            dialogStage.showAndWait();
            refreshPosts();
        } catch (IOException e) {
            showErrorAlert("Erreur de chargement", "Impossible d'ouvrir le formulaire de création", e);
        }
    }

    private void initializeForm() {
        titreField.clear();
        contenuField.clear();
        auteurField.clear();
        topicIdCombo.getSelectionModel().selectFirst();
    }

    @FXML
    private void handleCreatePost() {
        if (validateForm()) {
            Post newPost = new Post(
                    topicIdCombo.getValue(),
                    titreField.getText(),
                    contenuField.getText(),
                    auteurField.getText(),
                    LocalDateTime.now()
            );

            try {
                postService.add(newPost);
                showSuccessAlert("Post créé avec succès !");
                closeCurrentWindow();
                refreshPosts();
            } catch (Exception e) {
                showErrorAlert("Erreur de création", "Échec de la création du post", e);
            }
        }
    }

    @FXML
    private void handleDeletePost() {
        Post selectedPost = postsTable.getSelectionModel().getSelectedItem();
        if (selectedPost != null) {
            if (showConfirmationDialog("Confirmer la suppression",
                    "Voulez-vous vraiment supprimer ce post ?")) {

                postService.delete(selectedPost);
                refreshPosts();
                showSuccessAlert("Post supprimé avec succès !");
            }
        } else {
            showWarningAlert("Aucune sélection", "Veuillez sélectionner un post à supprimer");
        }
    }

    private void setupTableSelectionListener() {
        postsTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        loadPostDetails(newSelection);
                    }
                });
    }

    private void loadPostDetails(Post post) {
        titreField.setText(post.getTitre());
        contenuField.setText(post.getContenu());
        auteurField.setText(post.getAuteur());
        topicIdCombo.setValue(post.getTopicId());
    }

    private boolean validateForm() {
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
        if (topicIdCombo.getValue() == null) {
            errors.append("- Topic obligatoire\n");
        }

        if (errors.length() > 0) {
            showWarningAlert("Erreur de validation", errors.toString());
            return false;
        }
        return true;
    }

    public void refreshPosts() {
        ObservableList<Post> posts = postService.getAll();
        postsTable.setItems(posts);
    }

    // Méthodes utilitaires pour les dialogues
    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showWarningAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String title, String header, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    private boolean showConfirmationDialog(String title, String message) {
        return false;
    }

    private void closeCurrentWindow() {
        ((Stage) titreField.getScene().getWindow()).close();
    }

    public void cancelCreation(ActionEvent actionEvent) {
    }

    public void createPost(ActionEvent actionEvent) {
    }

    public void showEditPost(ActionEvent actionEvent) {
    }

    public void showDeletePost(ActionEvent actionEvent) {
    }
}