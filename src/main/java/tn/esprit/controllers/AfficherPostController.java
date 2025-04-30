package tn.esprit.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.entities.Post;
import tn.esprit.services.PostService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AfficherPostController implements javafx.fxml.Initializable {

    @FXML private TableView<Post> tableViewPosts;
    @FXML private TableColumn<Post, Integer> colId;
    @FXML private TableColumn<Post, String> colTitle;
    @FXML private TableColumn<Post, String> colContent;
    @FXML private TableColumn<Post, String> colCreatedAt;

    private final PostService postService = new PostService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTableColumns();
        loadPosts();
    }

    private void setupTableColumns() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colContent.setCellValueFactory(new PropertyValueFactory<>("content"));
        colCreatedAt.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCreatedAt().toString()));
    }

    public void loadPosts() {
        tableViewPosts.getItems().clear();
        tableViewPosts.setItems(postService.getAll());
    }

    @FXML
    private void handleCreatePost() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("CreerPost.fxml"));
            Parent root = loader.load();

            CreerPost controller = loader.getController();
            controller.setAfficherPostController(this);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Nouveau Post");
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Impossible d'ouvrir le formulaire de création: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdatePost() {
        Post selected = tableViewPosts.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ModifierPost.fxml"));
                Parent root = loader.load();

                ModifierPostController controller = loader.getController();
                controller.setPostData(selected);
                controller.setAfficherPostController(this);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Modifier Post");
                stage.show();
            } catch (IOException e) {
                showAlert("Erreur", "Impossible d'ouvrir l'éditeur: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner un post à modifier");
        }
    }

    @FXML
    private void handleDeletePost() {
        Post selected = tableViewPosts.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirmation");
            confirm.setHeaderText("Supprimer le post : " + selected.getTitle());
            confirm.setContentText("Êtes-vous sûr ?");

            if (confirm.showAndWait().get() == ButtonType.OK) {
                postService.delete(selected.getId());
                loadPosts();
                showAlert("Succès", "Post supprimé !");
            }
        } else {
            showAlert("Erreur", "Aucun post sélectionné");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}