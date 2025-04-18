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
import tn.esprit.entities.Commentaire;
import tn.esprit.services.CommentaireService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommentaireController {
    @FXML private TableView<Commentaire> commentairesTable;
    @FXML private TableColumn<Commentaire, Integer> idCol;
    @FXML private TableColumn<Commentaire, Integer> postIdCol;
    @FXML private TableColumn<Commentaire, String> contenuCol;
    @FXML private TableColumn<Commentaire, String> auteurCol;
    @FXML private TableColumn<Commentaire, String> dateCol;

    @FXML private TextArea contenuField;
    @FXML private TextField auteurField;
    @FXML private ComboBox<Integer> postIdCombo;

    private final CommentaireService commentaireService = new CommentaireService();
    private Stage primaryStage;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    private void initialize() {
        configureTableColumns();
        loadPosts();
        refreshCommentaires();
    }

    private void configureTableColumns() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        postIdCol.setCellValueFactory(new PropertyValueFactory<>("postId"));
        contenuCol.setCellValueFactory(new PropertyValueFactory<>("contenu"));
        auteurCol.setCellValueFactory(new PropertyValueFactory<>("auteur"));
        dateCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDateCreation().format(dateFormatter))
        );
        commentairesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void loadPosts() {
        // À remplacer par votre service de posts
        postIdCombo.getItems().addAll(1, 2, 3); // Exemple basique
    }

    @FXML
    private void showCreateCommentaire() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CreerCommentaire.fxml"));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Nouveau Commentaire");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.setScene(new Scene(root));

            CommentaireController controller = loader.getController();
            controller.setPrimaryStage(primaryStage);
            controller.initializeForm();

            dialogStage.showAndWait();
            refreshCommentaires();
        } catch (IOException e) {
            showErrorAlert("Erreur de chargement", "Impossible d'ouvrir le formulaire de création", e);
        }
    }

    @FXML
    private void handleCreateCommentaire() {
        if (validateForm()) {
            Commentaire newCommentaire = new Commentaire(
                    postIdCombo.getValue(),
                    contenuField.getText(),
                    auteurField.getText(),
                    LocalDateTime.now()
            );

            try {
                commentaireService.add(newCommentaire);
                showSuccessAlert("Commentaire créé avec succès !");
                closeCurrentWindow();
                refreshCommentaires();
            } catch (Exception e) {
                showErrorAlert("Erreur de création", "Échec de la création du commentaire", e);
            }
        }
    }

    @FXML
    private void handleDeleteCommentaire() {
        Commentaire selectedCommentaire = commentairesTable.getSelectionModel().getSelectedItem();
        if (selectedCommentaire != null) {
            if (showConfirmationDialog("Confirmer la suppression",
                    "Voulez-vous vraiment supprimer ce commentaire ?")) {

                commentaireService.delete(selectedCommentaire);
                refreshCommentaires();
                showSuccessAlert("Commentaire supprimé avec succès !");
            }
        } else {
            showWarningAlert("Aucune sélection", "Veuillez sélectionner un commentaire à supprimer");
        }
    }

    private void initializeForm() {
        contenuField.clear();
        auteurField.clear();
        postIdCombo.getSelectionModel().selectFirst();
    }

    private boolean validateForm() {
        StringBuilder errors = new StringBuilder();

        if (contenuField.getText().isEmpty()) {
            errors.append("- Contenu obligatoire\n");
        }
        if (auteurField.getText().isEmpty()) {
            errors.append("- Auteur obligatoire\n");
        }
        if (postIdCombo.getValue() == null) {
            errors.append("- Post obligatoire\n");
        }

        if (errors.length() > 0) {
            showWarningAlert("Erreur de validation", errors.toString());
            return false;
        }
        return true;
    }

    public void refreshCommentaires() {
        ObservableList<Commentaire> commentaires = (ObservableList<Commentaire>) commentaireService.getAll();
        commentairesTable.setItems(commentaires);
    }

    // Méthodes utilitaires pour les dialogues (identique aux autres contrôleurs)
    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showWarningAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String title, String header, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    private boolean showConfirmationDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait().get() == ButtonType.OK;
    }

    @FXML
    private void closeCurrentWindow() {
        ((Stage) contenuField.getScene().getWindow()).close();
    }
}