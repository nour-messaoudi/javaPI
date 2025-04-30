package tn.esprit.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tn.esprit.entities.Commentaire;
import tn.esprit.services.CommentaireService;

import java.io.IOException;

public class AfficherCommentaireController {
    @FXML private TableView<Commentaire> tableView;
    @FXML private TableColumn<Commentaire, Integer> colId;
    @FXML private TableColumn<Commentaire, String> colContent;
    @FXML private TableColumn<Commentaire, Integer> colPostId;
    @FXML private TableColumn<Commentaire, String> colDate;
    @FXML private Button btnAjouter;

    private final CommentaireService service = new CommentaireService();

    @FXML
    public void initialize() {
        setupColumns();
        loadData();
    }

    private void setupColumns() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colContent.setCellValueFactory(new PropertyValueFactory<>("content"));
        colPostId.setCellValueFactory(new PropertyValueFactory<>("postId"));
        colDate.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getCreatedAt().toString()));
    }

    private void loadData() {
        ObservableList<Commentaire> commentaires = service.getAll();
        tableView.setItems(commentaires);
    }

    @FXML
    private void handleAjouter() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CreerCommentaire.fxml"));
            Parent root = loader.load();

            CreerCommentaire controller = loader.getController();
            controller.setParentController(this);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Ajouter un commentaire");
            stage.showAndWait();
            loadData();
        } catch (IOException e) {
            showAlert("Erreur", "Impossible d'ouvrir le formulaire d'ajout: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleModifier() {
        Commentaire selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierCommentaire.fxml"));
                Parent root = loader.load();

                ModifierCommentaireController controller = loader.getController();
                controller.setCommentaire(selected);
                controller.setParentController(this);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Modifier le commentaire");
                stage.showAndWait();
                loadData();
            } catch (IOException e) {
                showAlert("Erreur", "Impossible d'ouvrir l'éditeur: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner un commentaire à modifier");
        }
    }

    @FXML
    private void handleSupprimer() {
        Commentaire selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Supprimer le commentaire");
            alert.setContentText("Êtes-vous sûr de vouloir supprimer ce commentaire ?");

            if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
                if (service.delete(selected.getId())) {
                    showAlert("Succès", "Commentaire supprimé avec succès");
                    loadData();
                } else {
                    showAlert("Erreur", "Échec de la suppression");
                }
            }
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner un commentaire à supprimer");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void refresh() {
        loadData();
    }
}