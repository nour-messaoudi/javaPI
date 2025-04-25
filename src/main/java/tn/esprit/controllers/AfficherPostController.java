package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.beans.property.SimpleStringProperty;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        colTitre.setCellValueFactory(cellData -> cellData.getValue().titreProperty());
        colContenu.setCellValueFactory(cellData -> cellData.getValue().contenuProperty());
        colAuteur.setCellValueFactory(cellData -> cellData.getValue().auteurProperty());
        colDateCreation.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDateCreation().toString())
        );

        loadPosts();
    }

    private void loadPosts() {
        PostService ps = new PostService();
        List<Post> posts = ps.getAll();
        System.out.println("Nombre de posts récupérés : " + posts.size());
        ObservableList<Post> observableList = FXCollections.observableArrayList(posts);
        tableViewPosts.setItems(observableList);
    }
}
