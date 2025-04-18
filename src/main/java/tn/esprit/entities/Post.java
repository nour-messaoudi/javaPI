package tn.esprit.entities;

import javafx.beans.property.*;
import java.time.LocalDateTime;

public class Post {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final IntegerProperty topicId = new SimpleIntegerProperty();
    private final StringProperty titre = new SimpleStringProperty();
    private final StringProperty contenu = new SimpleStringProperty();
    private final StringProperty auteur = new SimpleStringProperty();
    private final ObjectProperty<LocalDateTime> dateCreation = new SimpleObjectProperty<>();

    public Post(int id, int topicId, String titre, String contenu, String auteur, LocalDateTime dateCreation) {}

    public Post(int topicId, String titre, String contenu, String auteur, LocalDateTime dateCreation) {
        setTopicId(topicId);
        setTitre(titre);
        setContenu(contenu);
        setAuteur(auteur);
        setDateCreation(dateCreation);
    }

    // Property getters
    public IntegerProperty idProperty() { return id; }
    public IntegerProperty topicIdProperty() { return topicId; }
    public StringProperty titreProperty() { return titre; }
    public StringProperty contenuProperty() { return contenu; }
    public StringProperty auteurProperty() { return auteur; }
    public ObjectProperty<LocalDateTime> dateCreationProperty() { return dateCreation; }

    // Getters/Setters standards
    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }
    public int getTopicId() { return topicId.get(); }
    public void setTopicId(int topicId) { this.topicId.set(topicId); }
    public String getTitre() { return titre.get(); }
    public void setTitre(String titre) { this.titre.set(titre); }
    public String getContenu() { return contenu.get(); }
    public void setContenu(String contenu) { this.contenu.set(contenu); }
    public String getAuteur() { return auteur.get(); }
    public void setAuteur(String auteur) { this.auteur.set(auteur); }
    public LocalDateTime getDateCreation() { return dateCreation.get(); }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation.set(dateCreation); }
}