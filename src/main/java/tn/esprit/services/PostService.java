package tn.esprit.services;

import tn.esprit.entities.Post;
import tn.esprit.interfaces.iService;
import tn.esprit.util.MaConnexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class PostService implements iService<Post> {
    private Connection cnx;

    public PostService() {
        cnx = MaConnexion.getInstance().getCnx();
    }

    @Override
    public void add(Post post) {
        String req = "INSERT INTO post (title, content, created_at) VALUES (?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, post.getTitle());
            ps.setString(2, post.getContent());
            ps.setTimestamp(3, Timestamp.valueOf(post.getCreatedAt()));
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    post.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout: " + e.getMessage());
        }
    }

    @Override
    public void update(Post post) {
        String req = "UPDATE post SET title = ?, content = ? WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, post.getTitle());
            ps.setString(2, post.getContent());
            ps.setInt(3, post.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour: " + e.getMessage());
        }
    }

    @Override
    public boolean delete(int id) {
        String req = "DELETE FROM post WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression: " + e.getMessage());
            return false;
        }
    }

    @Override
    public ObservableList<Post> getAll() {
        ObservableList<Post> posts = FXCollections.observableArrayList();
        String req = "SELECT * FROM post ORDER BY created_at DESC";

        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(req)) {

            while (rs.next()) {
                Post p = new Post(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
                posts.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération: " + e.getMessage());
        }
        return posts;
    }

    @Override
    public Post getById(int id) {
        String req = "SELECT * FROM post WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Post(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("content"),
                            rs.getTimestamp("created_at").toLocalDateTime()
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération: " + e.getMessage());
        }
        return null;
    }

    public ObservableList<Post> searchByTitle(String keyword) {
        ObservableList<Post> posts = FXCollections.observableArrayList();
        String req = "SELECT * FROM post WHERE LOWER(title) LIKE LOWER(?) ORDER BY created_at DESC";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, "%" + keyword + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Post p = new Post(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("content"),
                            rs.getTimestamp("created_at").toLocalDateTime()
                    );
                    posts.add(p);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche: " + e.getMessage());
        }
        return posts;
    }

    public ObservableList<Post> filterByDate(LocalDate date) {
        ObservableList<Post> posts = FXCollections.observableArrayList();
        String req = "SELECT * FROM post WHERE DATE(created_at) = ? ORDER BY created_at DESC";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setDate(1, Date.valueOf(date));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Post p = new Post(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("content"),
                            rs.getTimestamp("created_at").toLocalDateTime()
                    );
                    posts.add(p);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors du filtrage: " + e.getMessage());
        }
        return posts;
    }

    public ObservableList<Post> getAllSorted(boolean ascending) {
        ObservableList<Post> posts = FXCollections.observableArrayList();
        String req = ascending ?
                "SELECT * FROM post ORDER BY created_at ASC" :
                "SELECT * FROM post ORDER BY created_at DESC";

        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(req)) {

            while (rs.next()) {
                Post p = new Post(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
                posts.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors du tri: " + e.getMessage());
        }
        return posts;
    }
}