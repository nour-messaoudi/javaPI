package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.entities.Post;
import tn.esprit.services.PostService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AfficherPostController implements Initializable {

    @FXML
    private FlowPane postsContainer;
    @FXML
    private TextField searchField;
    @FXML
    private DatePicker dateFilter;
    @FXML
    private ComboBox<String> sortComboBox;
    @FXML
    private Button prevButton;
    @FXML
    private Button nextButton;
    @FXML
    private Label pageInfoLabel;

    private static final int POSTS_PER_PAGE = 3; // Define how many posts to show per page
    private int currentPage = 1;
    private int totalPages = 1;
    private int totalPosts = 0;

    private Post selectedPost = null;
    private final PostService postService = new PostService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize the sort combo box
        sortComboBox.getItems().addAll("Plus récent", "Plus ancien");
        sortComboBox.getSelectionModel().selectFirst();

        // Listeners for filters
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.isEmpty()) {
                loadPosts();
            } else {
                searchPosts();
            }
        });

        dateFilter.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                filterByDate();
            } else {
                loadPosts();
            }
        });

        sortComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            sortPosts();
        });

        loadPosts();  // Load posts when the page is initialized
    }

    public void loadPosts() {
        postsContainer.getChildren().clear();
        selectedPost = null;

        // Get all posts from the PostService
        var allPosts = postService.getAll();
        totalPosts = allPosts.size();
        totalPages = (int) Math.ceil((double) totalPosts / POSTS_PER_PAGE);

        // Get the posts to display for the current page
        int startIndex = (currentPage - 1) * POSTS_PER_PAGE;
        int endIndex = Math.min(startIndex + POSTS_PER_PAGE, totalPosts);
        var postsToShow = allPosts.subList(startIndex, endIndex);

        // Display the posts for the current page
        for (Post post : postsToShow) {
            VBox postCard = createPostCard(post);
            postsContainer.getChildren().add(postCard);
        }

        updatePaginationControls();
    }

    private void searchPosts() {
        postsContainer.getChildren().clear();
        selectedPost = null;

        String keyword = searchField.getText();
        var searchedPosts = postService.searchByTitle(keyword);
        totalPosts = searchedPosts.size();
        totalPages = (int) Math.ceil((double) totalPosts / POSTS_PER_PAGE);

        int startIndex = (currentPage - 1) * POSTS_PER_PAGE;
        int endIndex = Math.min(startIndex + POSTS_PER_PAGE, totalPosts);
        var postsToShow = searchedPosts.subList(startIndex, endIndex);

        for (Post post : postsToShow) {
            VBox postCard = createPostCard(post);
            postsContainer.getChildren().add(postCard);
        }

        updatePaginationControls();
    }

    private void filterByDate() {
        postsContainer.getChildren().clear();
        selectedPost = null;

        LocalDate date = dateFilter.getValue();
        if (date != null) {
            var filteredPosts = postService.filterByDate(date);
            totalPosts = filteredPosts.size();
            totalPages = (int) Math.ceil((double) totalPosts / POSTS_PER_PAGE);

            int startIndex = (currentPage - 1) * POSTS_PER_PAGE;
            int endIndex = Math.min(startIndex + POSTS_PER_PAGE, totalPosts);
            var postsToShow = filteredPosts.subList(startIndex, endIndex);

            for (Post post : postsToShow) {
                VBox postCard = createPostCard(post);
                postsContainer.getChildren().add(postCard);
            }
        }

        updatePaginationControls();
    }

    private void sortPosts() {
        postsContainer.getChildren().clear();
        selectedPost = null;

        String sortOption = sortComboBox.getValue();
        boolean ascending = "Plus ancien".equals(sortOption);

        var sortedPosts = postService.getAllSorted(ascending);
        totalPosts = sortedPosts.size();
        totalPages = (int) Math.ceil((double) totalPosts / POSTS_PER_PAGE);

        int startIndex = (currentPage - 1) * POSTS_PER_PAGE;
        int endIndex = Math.min(startIndex + POSTS_PER_PAGE, totalPosts);
        var postsToShow = sortedPosts.subList(startIndex, endIndex);

        for (Post post : postsToShow) {
            VBox postCard = createPostCard(post);
            postsContainer.getChildren().add(postCard);
        }

        updatePaginationControls();
    }

    private VBox createPostCard(Post post) {
        VBox card = new VBox();
        card.getStyleClass().add("post-card");
        card.setSpacing(10);
        card.setStyle("-fx-background-color: #F8F2EC; -fx-background-radius: 10; -fx-padding: 15;");
        card.setPrefWidth(300);
        card.setMaxWidth(300);

        // Title
        Label titleLabel = new Label(post.getTitle());
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #6D4C41;");
        titleLabel.setWrapText(true);

        // Content
        Text contentText = new Text(post.getContent());
        contentText.setWrappingWidth(280);
        contentText.setStyle("-fx-font-size: 14px; -fx-fill: #5D4037;");

        // Date
        Label dateLabel = new Label("Créé le: " + post.getCreatedAt().toString());
        dateLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #8D6E63;");

        card.getChildren().addAll(titleLabel, contentText, dateLabel);

        // Handle selection
        card.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCommentairee.fxml"));
                Parent root = loader.load();

                AfficherCommentaireControllere controller = loader.getController();
                controller.setSelectedPost(post);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Commentaires de: " + post.getTitle());
                stage.show();
            } catch (IOException e) {
                showAlert("Erreur", "Impossible d'ouvrir les commentaires: " + e.getMessage());
                e.printStackTrace();
            }
        });

        return card;
    }

    private void updatePaginationControls() {
        pageInfoLabel.setText("Page " + currentPage + " / " + totalPages);
        prevButton.setDisable(currentPage <= 1);
        nextButton.setDisable(currentPage >= totalPages);
    }

    @FXML
    private void goToPreviousPage() {
        if (currentPage > 1) {
            currentPage--;
            loadPosts();
        }
    }

    @FXML
    private void goToNextPage() {
        if (currentPage < totalPages) {
            currentPage++;
            loadPosts();
        }
    }

    @FXML
    private void handleCreatePost() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CreerPost.fxml"));
            Parent root = loader.load();

            CreerPost controller = loader.getController();
            controller.setAfficherPostController(this);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Nouveau Post");
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Impossible d'ouvrir le formulaire de création: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdatePost() {
        if (selectedPost != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierPost.fxml"));
                Parent root = loader.load();

                ModifierPostController controller = loader.getController();
                controller.setPostData(selectedPost);
                controller.setAfficherPostController(this);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Modifier Post");
                stage.show();
            } catch (IOException e) {
                showAlert("Erreur", "Impossible d'ouvrir l'éditeur: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner un post à modifier en cliquant dessus");
        }
    }

    @FXML
    private void handleDeletePost() {
        if (selectedPost != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirmation");
            confirm.setHeaderText("Supprimer le post : " + selectedPost.getTitle());
            confirm.setContentText("Êtes-vous sûr ?");
            confirm.getDialogPane().setStyle("-fx-background-color: #FAF5F0;");

            if (confirm.showAndWait().get() == ButtonType.OK) {
                postService.delete(selectedPost.getId());
                loadPosts();
                showAlert("Succès", "Post supprimé !");
            }
        } else {
            showAlert("Erreur", "Aucun post sélectionné. Veuillez cliquer sur un post pour le sélectionner");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Style for the alert dialog
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: #FAF5F0;");
        dialogPane.lookup(".content.label").setStyle("-fx-text-fill: #5D4037;");

        alert.showAndWait();
    }
}
