package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tn.esprit.entities.Topic;
import tn.esprit.services.TopicService;

import java.io.IOException;

public class ModifierTopicController {
    @FXML private TextField titreField;
    @FXML private TextArea descriptionField;
    @FXML private TextField createurField;

    private Topic currentTopic;
    private Stage primaryStage;
    private TopicController topicController;
    private final TopicService topicService = new TopicService();

    public void setTopicData(Topic topic) {
        this.currentTopic = topic;
        titreField.setText(topic.getTitre());
        descriptionField.setText(topic.getDescription());
        createurField.setText(topic.getCreateur());
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void setTopicController(TopicController controller) {
        this.topicController = controller;
    }

    @FXML
    private void handleUpdate() {
        if (validateInput()) {
            currentTopic.setTitre(titreField.getText());
            currentTopic.setDescription(descriptionField.getText());
            currentTopic.setCreateur(createurField.getText());

            topicService.update(currentTopic);
            topicController.refreshTopics();
            primaryStage.close();
        }
    }

    @FXML
    private void handleDelete() {
        if (currentTopic != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/SupprimerTopic.fxml"));
                Parent root = loader.load();

                SupprimerTopicController controller = loader.getController();
                controller.setTopicData(currentTopic);
                controller.setTopicController(topicController);

                Stage dialogStage = new Stage();
                dialogStage.setTitle("Confirmation de suppression");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(primaryStage);
                dialogStage.setScene(new Scene(root));
                dialogStage.showAndWait();

                primaryStage.close();
            } catch (IOException e) {
                showAlert("Erreur", "Impossible d'ouvrir la confirmation", e.getMessage());
            }
        }
    }

    @FXML
    private void handleCancel() {
        primaryStage.close();
    }

    private boolean validateInput() {
        if (titreField.getText().isEmpty() || descriptionField.getText().isEmpty() || createurField.getText().isEmpty()) {
            showAlert("Erreur", "Champs obligatoires", "Tous les champs doivent être remplis");
            return false;
        }
        return true;
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}