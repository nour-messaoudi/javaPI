package tn.esprit;

import javafx.collections.ObservableList;
import tn.esprit.entities.Commentaire;
import tn.esprit.entities.Post;
import tn.esprit.entities.Topic;
import tn.esprit.services.CommentaireService;
import tn.esprit.services.PostService;
import tn.esprit.services.TopicService;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        TopicService topicService = new TopicService();
        PostService postService = new PostService();
        CommentaireService commentaireService = new CommentaireService();

        // Test Topic
        Topic topic = new Topic("JavaFX", "Discussion sur JavaFX", "Admin", LocalDateTime.now());
        topicService.add(topic);

        // Test Post
        Post post = new Post(topic.getId(), "Premier Post", "Contenu du post", "User1", LocalDateTime.now());
        postService.add(post);

        // Test Commentaire
        Commentaire commentaire = new Commentaire(post.getId(), "Premier commentaire", "User2", LocalDateTime.now());
        commentaireService.add(commentaire);

        // Affichage
        System.out.println("\nListe des topics:");
        ObservableList<Topic> topics = topicService.getAll();
        topics.forEach(System.out::println);

        System.out.println("\nListe des posts:");
        ObservableList<Post> posts = postService.getAll();
        posts.forEach(System.out::println);

        System.out.println("\nListe des commentaires:");
        ObservableList<Commentaire> commentaires = commentaireService.getAll();
        commentaires.forEach(System.out::println);
    }
}