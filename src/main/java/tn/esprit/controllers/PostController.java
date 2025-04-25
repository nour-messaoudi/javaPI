package tn.esprit.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
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
import java.time.format.DateTimeFormatter;

public class PostController {
    @FXML private TableView<Post> postsTable;
    @FXML private TableColumn<Post, Integer> idCol;
    @FXML private TableColumn<Post, String> titreCol;
    @FXML private TableColumn<Post, String> contenuCol;
    @FXML private TableColumn<Post, String> auteurCol;
    @FXML private TableColumn<Post, String> dateCol;

    private final PostService postService = new PostService();
    private Stage primaryStage;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    private void initialize() {
        configureTableColumns();
        refreshPosts();
    }

    private void configureTableColumns() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titreCol.setCellValueFactory(new PropertyValueFactory<>("titre"));
        contenuCol.setCellValueFactory(new PropertyValueFactory<>("contenu"));
        auteurCol.setCellValueFactory(new PropertyValueFactory<>("auteur"));
        dateCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDateCreation().format(dateFormatter))
        );
        postsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    @FXML
    private void showCreatePost() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CreerPost.fxml"));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Créer un nouveau Post");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.setScene(new Scene(root));

            CreerPost controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPostController(this);

            dialogStage.showAndWait();
        } catch (IOException e) {
            showAlert("Erreur", "Impossible d'ouvrir le formulaire", e.getMessage());
        }
    }

    @FXML
    private void showEditPost() {
        Post selectedPost = postsTable.getSelectionModel().getSelectedItem();
        if (selectedPost != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierPost.fxml"));
                Parent root = loader.load();

                ModifierPostController controller = loader.getController();
                controller.setPostData(selectedPost);
                controller.setPrimaryStage(primaryStage);
                controller.setPostController(this);

                Stage dialogStage = new Stage();
                dialogStage.setTitle("Modifier Post");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(primaryStage);
                dialogStage.setScene(new Scene(root));
                dialogStage.showAndWait();
            } catch (IOException e) {
                showAlert("Erreur", "Impossible d'ouvrir l'éditeur", e.getMessage());
            }
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner un post à modifier", "");
        }
    }

    @FXML
    private void showDeletePost() {
        Post selectedPost = postsTable.getSelectionModel().getSelectedItem();
        if (selectedPost != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/SupprimerPost.fxml"));
                Parent root = loader.load();

                SupprimerPostController controller = loader.getController();
                controller.setPostData(selectedPost);
                controller.setPostController(this);

                Stage dialogStage = new Stage();
                dialogStage.setTitle("Confirmation de suppression");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(primaryStage);
                dialogStage.setScene(new Scene(root));
                dialogStage.showAndWait();
            } catch (IOException e) {
                showAlert("Erreur", "Impossible d'ouvrir la confirmation", e.getMessage());
            }
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner un post à supprimer", "");
        }
    }

    public void refreshPosts() {
        ObservableList<Post> posts = (ObservableList<Post>) postService.getAll();
        postsTable.setItems(posts);
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
