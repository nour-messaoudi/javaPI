package tn.esprit.controllers;

import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.framework.junit5.ApplicationTest;
import tn.esprit.entities.Topic;
import tn.esprit.services.TopicService;

import java.time.LocalDateTime;

import static javafx.beans.binding.Bindings.when;

class TopicControllerTest extends ApplicationTest {

    private TopicController controller;
    private TopicService mockService;

    @Override
    public void start(Stage stage) throws Exception {
        // Mock du service
        mockService = mock(TopicService.class);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherTopic.fxml"));
        Object root = loader.load();
        controller = loader.getController();

        // Injection de dépendance
        controller.setTopicService(mockService);

        stage.setScene(new Scene((Parent) root));
        stage.show();
    }

    private TopicService mock(Class<TopicService> topicServiceClass) {
        return null;
    }

    @BeforeEach
    void setUp() {
        // Configurer les mocks avant chaque test
        when((ObservableBooleanValue) mockService.getAll()).then(FXCollections.observableArrayList(
                new Topic(1, "Test", "Description", "Auteur", LocalDateTime.now())
        ));
    }
}