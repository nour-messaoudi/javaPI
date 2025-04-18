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
import tn.esprit.entities.Topic;
import tn.esprit.services.TopicService;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class TopicController {
    @FXML private TableView<Topic> topicsTable;
    @FXML private TableColumn<Topic, Integer> idCol;
    @FXML private TableColumn<Topic, String> titreCol;
    @FXML private TableColumn<Topic, String> descriptionCol;
    @FXML private TableColumn<Topic, String> createurCol;
    @FXML private TableColumn<Topic, String> dateCol;

    private final TopicService topicService = new TopicService();
    private Stage primaryStage;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    private void initialize() {
        configureTableColumns();
        refreshTopics();
    }

    private void configureTableColumns() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titreCol.setCellValueFactory(new PropertyValueFactory<>("titre"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        createurCol.setCellValueFactory(new PropertyValueFactory<>("createur"));
        dateCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDateCreation().format(dateFormatter))
        );
        topicsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    @FXML
    private void showCreateTopic() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CreerTopic.fxml"));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Nouveau Topic");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.setScene(new Scene(root));

            CreerTopic controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setTopicController(this);

            dialogStage.showAndWait();
        } catch (IOException e) {
            showAlert("Erreur", "Impossible d'ouvrir le formulaire", e.getMessage());
        }
    }

    @FXML
    private void showEditTopic() {
        Topic selectedTopic = topicsTable.getSelectionModel().getSelectedItem();
        if (selectedTopic != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierTopic.fxml"));
                Parent root = loader.load();

                ModifierTopicController controller = loader.getController();
                controller.setTopicData(selectedTopic);
                controller.setPrimaryStage(primaryStage);
                controller.setTopicController(this);

                Stage dialogStage = new Stage();
                dialogStage.setTitle("Modifier Topic");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(primaryStage);
                dialogStage.setScene(new Scene(root));
                dialogStage.showAndWait();
            } catch (IOException e) {
                showAlert("Erreur", "Impossible d'ouvrir l'éditeur", e.getMessage());
            }
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner un topic à modifier", "");
        }
    }

    @FXML
    private void showDeleteTopic() {
        Topic selectedTopic = topicsTable.getSelectionModel().getSelectedItem();
        if (selectedTopic != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/SupprimerTopic.fxml"));
                Parent root = loader.load();

                SupprimerTopicController controller = loader.getController();
                controller.setTopicData(selectedTopic);
                controller.setTopicController(this);

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
            showAlert("Aucune sélection", "Veuillez sélectionner un topic à supprimer", "");
        }
    }

    public void refreshTopics() {
        ObservableList<Topic> topics = topicService.getAll();
        topicsTable.setItems(topics);
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setTopicService(TopicService mockService) {
    }
}