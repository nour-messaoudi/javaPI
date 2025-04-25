package tn.esprit.entities;

import java.time.LocalDateTime;

public class Commentaire {

    //Attr
    private int id;
    private int postId;
    private String contenu;
    private String auteur;
    private LocalDateTime dateCreation;

    //constructor
    public Commentaire(int id, String contenu, String auteur, LocalDateTime dateCreation, int topicId) {
    }

    public Commentaire(int postId, String contenu, String auteur, LocalDateTime dateCreation) {
        this.postId = postId;
        this.contenu = contenu;
        this.auteur = auteur;
        this.dateCreation = dateCreation;
    }

    public Commentaire(int id, int postId, String contenu, String auteur, LocalDateTime dateCreation) {
        this.id = id;
        this.postId = postId;
        this.contenu = contenu;
        this.auteur = auteur;
        this.dateCreation = dateCreation;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    //Display

    @Override
    public String toString() {
        return "Commentaire{" +
                "id=" + id +
                ", postId=" + postId +
                ", contenu='" + contenu + '\'' +
                ", auteur='" + auteur + '\'' +
                ", dateCreation=" + dateCreation +
                '}';
    }

    public void setPost(Post value) {

    }

    public void setTopic(Topic topicActuel) {
    }

    public int getTopicId() {
        return 0;
    }
}