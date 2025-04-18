package tn.esprit.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.entities.Commentaire;
import tn.esprit.interfaces.iService;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.time.LocalDateTime;

public class CommentaireService implements iService<Commentaire> {
    Connection cnx = MaConnexion.getInstance().getCnx();

    @Override
    public void add(Commentaire commentaire) {
        String req = "INSERT INTO `commentaire`(`post_id`, `contenu`, `auteur`, `dateCreation`) VALUES (?,?,?,?)";
        try (PreparedStatement ps = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, commentaire.getPostId());
            ps.setString(2, commentaire.getContenu());
            ps.setString(3, commentaire.getAuteur());
            ps.setTimestamp(4, Timestamp.valueOf(commentaire.getDateCreation()));

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        commentaire.setId(generatedKeys.getInt(1));
                    }
                }
            }
            System.out.println("Commentaire added successfully with ID: " + commentaire.getId());
        } catch (SQLException e) {
            System.err.println("Error adding commentaire: " + e.getMessage());
            throw new RuntimeException("Failed to add commentaire", e);
        }
    }

    @Override
    public void update(Commentaire commentaire) {
        String req = "UPDATE `commentaire` SET `post_id`=?, `contenu`=?, `auteur`=?, `dateCreation`=? WHERE `id`=?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, commentaire.getPostId());
            ps.setString(2, commentaire.getContenu());
            ps.setString(3, commentaire.getAuteur());
            ps.setTimestamp(4, Timestamp.valueOf(commentaire.getDateCreation()));
            ps.setInt(5, commentaire.getId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Commentaire updated successfully");
            } else {
                System.out.println("No commentaire found with ID: " + commentaire.getId());
            }
        } catch (SQLException e) {
            System.err.println("Error updating commentaire: " + e.getMessage());
            throw new RuntimeException("Failed to update commentaire", e);
        }
    }

    @Override
    public void delete(Commentaire commentaire) {
        String req = "DELETE FROM `commentaire` WHERE `id`=?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, commentaire.getId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Commentaire deleted successfully");
            } else {
                System.out.println("No commentaire found with ID: " + commentaire.getId());
            }
        } catch (SQLException e) {
            System.err.println("Error deleting commentaire: " + e.getMessage());
            throw new RuntimeException("Failed to delete commentaire", e);
        }
    }

    @Override
    public ObservableList<Commentaire> getAll() {
        ObservableList<Commentaire> commentaires = FXCollections.observableArrayList();
        String req = "SELECT * FROM `commentaire` ORDER BY `dateCreation` DESC";

        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(req)) {

            while (rs.next()) {
                Commentaire commentaire = new Commentaire(
                        rs.getInt("id"),
                        rs.getInt("post_id"),
                        rs.getString("contenu"),
                        rs.getString("auteur"),
                        rs.getTimestamp("dateCreation").toLocalDateTime()
                );
                commentaires.add(commentaire);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching commentaires: " + e.getMessage());
            throw new RuntimeException("Failed to fetch commentaires", e);
        }
        return commentaires;
    }

    @Override
    public Commentaire getOne(int id) {
        String req = "SELECT * FROM `commentaire` WHERE `id`=?";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Commentaire(
                            rs.getInt("id"),
                            rs.getInt("post_id"),
                            rs.getString("contenu"),
                            rs.getString("auteur"),
                            rs.getTimestamp("dateCreation").toLocalDateTime()
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching commentaire with ID " + id + ": " + e.getMessage());
            throw new RuntimeException("Failed to fetch commentaire", e);
        }
        return null;
    }

    public ObservableList<Commentaire> getByPost(int postId) {
        ObservableList<Commentaire> commentaires = FXCollections.observableArrayList();
        String req = "SELECT * FROM `commentaire` WHERE `post_id`=? ORDER BY `dateCreation` DESC";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, postId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Commentaire commentaire = new Commentaire(
                            rs.getInt("id"),
                            rs.getInt("post_id"),
                            rs.getString("contenu"),
                            rs.getString("auteur"),
                            rs.getTimestamp("dateCreation").toLocalDateTime()
                    );
                    commentaires.add(commentaire);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching commentaires for post " + postId + ": " + e.getMessage());
            throw new RuntimeException("Failed to fetch commentaires by post", e);
        }
        return commentaires;
    }
}