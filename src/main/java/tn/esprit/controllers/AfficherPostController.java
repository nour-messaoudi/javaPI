package tn.esprit.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import tn.esprit.entities.Post;
import tn.esprit.services.PostService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherPostController implements Initializable {

    @FXML
    private TableView<Post> tableViewPosts;
    @FXML
    private TableColumn<Post, Integer> colId;
    @FXML
    private TableColumn<Post, String> colTitre;
    @FXML
    private TableColumn<Post, String> colContenu;
    @FXML
    private TableColumn<Post, String> colAuteur;
    @FXML
    private TableColumn<Post, String> colDateCreation;

    @FXML
    private Button btnAjouter;
    @FXML
    private Button btnModifier;
    @FXML
    private Button btnSupprimer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        colTitre.setCellValueFactory(cellData -> cellData.getValue().titreProperty());
        colContenu.setCellValueFactory(cellData -> cellData.getValue().contenuProperty());
        colAuteur.setCellValueFactory(cellData -> cellData.getValue().auteurProperty());
        colDateCreation.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateCreation().toString()));

        btnAjouter.setOnAction(e -> ajouterPost());
        btnModifier.setOnAction(e -> modifierPost());
        btnSupprimer.setOnAction(e -> supprimerPost());

        loadPosts();
    }

    private void loadPosts() {
        PostService ps = new PostService();
        List<Post> posts = ps.getAll();
        ObservableList<Post> observableList = FXCollections.observableArrayList(posts);
        tableViewPosts.setItems(observableList);
    }

    private void ajouterPost() {
        System.out.println("Ajouter un post...");
        // Tu peux ici ouvrir une autre vue FXML avec formulaire
    }

    private void modifierPost() {
        Post selectedPost = tableViewPosts.getSelectionModel().getSelectedItem();
        if (selectedPost != null) {
            System.out.println("Modifier post : " + selectedPost.getTitre());
            // Tu peux ouvrir une autre vue FXML pour l'édition
        } else {
            showAlert("Veuillez sélectionner un post à modifier.");
        }
    }

    private void supprimerPost() {
        Post selectedPost = tableViewPosts.getSelectionModel().getSelectedItem();
        if (selectedPost != null) {
            PostService ps = new PostService();
            ps.supprimer(selectedPost.getId());
            loadPosts(); // Rafraîchir la liste
        } else {
            showAlert("Veuillez sélectionner un post à supprimer.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
