package tn.esprit.entities;

import java.time.LocalDateTime;

public class Commentaire {
    private Integer id;
    private String content;
    private int postId;
    private LocalDateTime createdAt;

    public Commentaire() {}

    public Commentaire(String content, int postId) {
        this.content = content;
        this.postId = postId;
        this.createdAt = LocalDateTime.now();
    }

    // Getters & Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public int getPostId() { return postId; }
    public void setPostId(int postId) { this.postId = postId; }

    public void setIdPost(int postId) {
        this.postId = postId;
    }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}