package tn.esprit.entities;

import java.time.LocalDateTime;
import tn.esprit.util.BadWordsFilter;

public class Commentaire {
    private Integer id;
    private String content;
    private int postId;
    private LocalDateTime createdAt;

    public Commentaire() {}

    public Commentaire(String content, int postId) {
        this.setContent(content); // Validation via setter
        this.postId = postId;
        this.createdAt = LocalDateTime.now();
    }

    // Getters & Setters avec validation
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Le commentaire ne peut pas être vide");
        }
        if (BadWordsFilter.containsBadWords(content)) {
            throw new IllegalArgumentException("Commentaire inapproprié détecté");
        }
        this.content = content;
    }

    public int getPostId() { return postId; }
    public void setPostId(int postId) { this.postId = postId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public void setIdPost(int i) {

    }
}