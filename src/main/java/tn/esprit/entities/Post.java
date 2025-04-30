package tn.esprit.entities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.time.LocalDateTime;

public class Post {
    private final SimpleIntegerProperty id = new SimpleIntegerProperty();
    private final SimpleStringProperty title = new SimpleStringProperty();
    private final SimpleStringProperty content = new SimpleStringProperty();
    private final SimpleObjectProperty<LocalDateTime> createdAt = new SimpleObjectProperty<>();

    // Constructeurs
    public Post() {
        this.createdAt.set(LocalDateTime.now());
    }

    public Post(String title, String content) {
        this();
        this.title.set(title);
        this.content.set(content);
    }

    public Post(int id, String title, String content, LocalDateTime createdAt) {
        this(title, content);
        this.id.set(id);
        this.createdAt.set(createdAt);
    }

    // Méthodes d'accès standard
    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }
    public SimpleIntegerProperty idProperty() { return id; }

    public String getTitle() { return title.get(); }
    public void setTitle(String title) { this.title.set(title); }
    public SimpleStringProperty titleProperty() { return title; }

    public String getContent() { return content.get(); }
    public void setContent(String content) { this.content.set(content); }
    public SimpleStringProperty contentProperty() { return content; }

    public LocalDateTime getCreatedAt() { return createdAt.get(); }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt.set(createdAt); }
    public SimpleObjectProperty<LocalDateTime> createdAtProperty() { return createdAt; }
}