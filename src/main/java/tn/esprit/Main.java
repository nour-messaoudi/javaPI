package tn.esprit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.entities.Post;
import tn.esprit.services.PostService;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        PostService postService = new PostService();

        // Test CRUD
        Post post = new Post("Test Post", "Contenu de test");
        postService.add(post);

        ObservableList<Post> posts = postService.getAll();
        System.out.println("Liste des posts:");
        posts.forEach(System.out::println);

        if (!posts.isEmpty()) {
            Post toUpdate = posts.get(0);
            toUpdate.setTitle("Titre modifié");
            postService.update(toUpdate);

            postService.delete(toUpdate.getId());
        }
    }
}