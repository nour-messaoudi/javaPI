package tn.esprit.entities;

import java.time.LocalDateTime;

public class Topic {

    //Attr
    private int id;
    private String titre;
    private String description;
    private String createur;
    private LocalDateTime dateCreation;

    //constructor


    public Topic() {
    }

    public Topic(String titre, String description, String createur, LocalDateTime dateCreation) {
        this.titre = titre;
        this.description = description;
        this.createur = createur;
        this.dateCreation = dateCreation;
    }

    public Topic(int id, String titre, String description, String createur, LocalDateTime dateCreation) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.createur = createur;
        this.dateCreation = dateCreation;
    }

    //Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateur() {
        return createur;
    }

    public void setCreateur(String createur) {
        this.createur = createur;
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
        return "Topic{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", createur='" + createur + '\'' +
                ", dateCreation=" + dateCreation +
                '}';
    }
}
