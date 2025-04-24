package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AfficherTopicController {

    @FXML
    private ListView<String> topicListView;

    public void initialize() {
        // Remplir la ListView avec quelques données initiales
        topicListView.getItems().addAll("Topic 1", "Topic 2", "Topic 3");
    }

    @FXML
    private void ajouterTopic() {
        // Logique pour ajouter un topic
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Ajouter Topic");
        alert.setHeaderText(null);
        alert.setContentText("Un nouveau topic a été ajouté !");
        alert.showAndWait();

        // Ajouter un nouveau topic dans la liste
        topicListView.getItems().add("Nouveau Topic");
    }
}
