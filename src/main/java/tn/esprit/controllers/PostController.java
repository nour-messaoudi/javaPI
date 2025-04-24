package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import tn.esprit.entities.Post;
import tn.esprit.entities.Topic;
import tn.esprit.services.PostService;
import tn.esprit.services.TopicService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PostController implements Initializable {

    @FXML
    private TextField titreField;

    @FXML
    private TextArea contenuField;

    @FXML
    private ComboBox<Topic> topicIdCombo;

    @FXML
    private ListView<Post> postListView;

    private final PostService postService = new PostService();
    private final TopicService topicService = new TopicService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTopics();
        loadPosts();
    }

    private void loadTopics() {
        List<Topic> topics = topicService.getAllTopics();
        ObservableList<Topic> topicList = FXCollections.observableArrayList(topics);
        topicIdCombo.setItems(topicList);
    }

    private void loadPosts() {
        List<Post> posts = postService.getAllPosts();
        ObservableList<Post> postList = FXCollections.observableArrayList(posts);
        postListView.setItems(postList);
    }

    @FXML
    private void handleAddPost() {
        String titre = titreField.getText();
        String contenu = contenuField.getText();
        Topic selectedTopic = topicIdCombo.getValue();

        if (titre.isEmpty() || contenu.isEmpty() || selectedTopic == null) {
            System.out.println("Tous les champs doivent être remplis.");
            return;
        }

        Post newPost = new Post();
        newPost.setTitre(titre);
        newPost.setContenu(contenu);
        newPost.setTopic(selectedTopic);

        postService.ajouter(newPost);
        loadPosts();

        titreField.clear();
        contenuField.clear();
        topicIdCombo.getSelectionModel().clearSelection();
    }

    public void refreshPosts() {
    }

    public void handleLoadPosts(ActionEvent actionEvent) {
    }
}
