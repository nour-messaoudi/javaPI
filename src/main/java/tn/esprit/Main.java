package tn.esprit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.entities.Post;
import tn.esprit.services.PostService;
import tn.esprit.services.TranslationService;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        String translated = new TranslationService().translateText("Hello world", "fr");
        System.out.println(translated); // Devrait afficher "Bonjour le monde"
    }

}