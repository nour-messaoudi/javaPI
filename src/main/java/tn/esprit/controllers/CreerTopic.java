package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.entities.Topic;
import tn.esprit.services.TopicService;

public class CreerTopic {
    @FXML private TextField titreField;
    @FXML private TextArea descriptionField;
    @FXML private TextField createurField;

    private Stage dialogStage;
    private final TopicService topicService = new TopicService();

    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }

    @FXML
    private void handleCreate() {
        if (validateInput()) {
            Topic newTopic = new Topic(
                    titreField.getText(),
                    descriptionField.getText(),
                    createurField.getText(),
                    java.time.LocalDateTime.now()
            );

            topicService.add(newTopic);
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean validateInput() {
        StringBuilder errors = new StringBuilder();

        if (titreField.getText().isEmpty()) {
            errors.append("- Titre obligatoire\n");
        }
        if (descriptionField.getText().isEmpty()) {
            errors.append("- Description obligatoire\n");
        }
        if (createurField.getText().isEmpty()) {
            errors.append("- Créateur obligatoire\n");
        }

        if (errors.length() > 0) {
            showAlert("Erreur de validation", errors.toString());
            return false;
        }
        return true;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(dialogStage);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setTopicController(TopicController topicController) {

    }
}