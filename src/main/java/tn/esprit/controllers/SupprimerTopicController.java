package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import tn.esprit.entities.Topic;
import tn.esprit.services.TopicService;

public class SupprimerTopicController {
    @FXML private Label confirmationLabel;
    @FXML private Button confirmerButton;
    @FXML private Button annulerButton;

    private Topic topic;
    private final TopicService topicService = new TopicService();
    private TopicController topicController;

    public void setTopicData(Topic topic) {
        this.topic = topic;
        confirmationLabel.setText("Voulez-vous vraiment supprimer le topic \"" + topic.getTitre() + "\" ?");
    }

    public void setTopicController(TopicController topicController) {
        this.topicController = topicController;
    }

    @FXML
    private void confirmerSuppression() {
        topicService.delete(topic.getId());
        topicController.refreshTopics();
        closeWindow();
    }

    @FXML
    private void annulerSuppression() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) confirmerButton.getScene().getWindow();
        stage.close();
    }
}
