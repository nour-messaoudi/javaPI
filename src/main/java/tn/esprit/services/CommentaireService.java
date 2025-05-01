package tn.esprit.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.entities.Commentaire;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.time.LocalDateTime;

public class CommentaireService {
    private final Connection cnx = MaConnexion.getInstance().getCnx();

    // Method to add a comment to the database
    public boolean add(Commentaire commentaire) {
        String query = "INSERT INTO commentaire (content, post_id, created_at) VALUES (?, ?, ?)";
        try (PreparedStatement pst = cnx.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, commentaire.getContent());
            pst.setInt(2, commentaire.getPostId());
            pst.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));

            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pst.getGeneratedKeys()) {
                    if (rs.next()) {
                        commentaire.setId(rs.getInt(1)); // Set the generated ID
                    }
                }
                return true; // Success
            }
        } catch (SQLException e) {
            System.err.println("Error while adding comment: " + e.getMessage());
            e.printStackTrace();
        }
        return false; // Failure
    }

    // Method to fetch comments for a specific post with pagination (LIMIT and OFFSET)
    public ObservableList<Commentaire> getCommentsByPostId(int postId, int limit, int offset) {
        ObservableList<Commentaire> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM commentaire WHERE post_id = ? LIMIT ? OFFSET ?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, postId);
            pst.setInt(2, limit); // Limit the number of comments fetched
            pst.setInt(3, offset); // Offset the result set to support pagination

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Commentaire c = new Commentaire();
                    c.setId(rs.getInt("id"));
                    c.setContent(rs.getString("content"));
                    c.setPostId(rs.getInt("post_id"));
                    c.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    list.add(c);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while fetching comments: " + e.getMessage());
            throw new RuntimeException("Error while fetching comments for postId " + postId, e);
        }
        return list;
    }

    // Method to update a comment
    public void update(Commentaire commentaire) {
        String query = "UPDATE commentaire SET content = ?, post_id = ? WHERE id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setString(1, commentaire.getContent());
            pst.setInt(2, commentaire.getPostId());
            pst.setInt(3, commentaire.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while updating comment: " + e.getMessage());
            throw new RuntimeException("Error while updating comment", e);
        }
    }

    // Method to delete a comment
    public boolean delete(int id) {
        String query = "DELETE FROM commentaire WHERE id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, id);
            return pst.executeUpdate() > 0; // Returns true if rows were deleted
        } catch (SQLException e) {
            System.err.println("Error while deleting comment: " + e.getMessage());
            throw new RuntimeException("Error while deleting comment", e);
        }
    }

    // Optional: Method to get a single comment for a specific post (if needed)
    public Commentaire getCommentById(int id) {
        String query = "SELECT * FROM commentaire WHERE id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Commentaire c = new Commentaire();
                    c.setId(rs.getInt("id"));
                    c.setContent(rs.getString("content"));
                    c.setPostId(rs.getInt("post_id"));
                    c.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    return c;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while fetching comment by ID: " + e.getMessage());
        }
        return null;
    }

    // Method to get all comments from the database
    public ObservableList<Commentaire> getAll() {
        ObservableList<Commentaire> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM commentaire";
        try (PreparedStatement pst = cnx.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Commentaire c = new Commentaire();
                c.setId(rs.getInt("id"));
                c.setContent(rs.getString("content"));
                c.setPostId(rs.getInt("post_id"));
                c.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                list.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Error while fetching all comments: " + e.getMessage());
            throw new RuntimeException("Error while fetching all comments", e);
        }
        return list;
    }
}
