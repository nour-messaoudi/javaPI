package tn.esprit.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.entities.Commentaire;
import tn.esprit.interfaces.iService;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.util.List;

public class CommentaireService implements iService<Commentaire> {
    Connection cnx = MaConnexion.getInstance().getCnx();

    @Override
    public void add(Commentaire commentaire) {
        String req = "INSERT INTO commentaire (contenu, auteur, dateCreation, topic_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, commentaire.getContenu());
            ps.setString(2, commentaire.getAuteur());
            ps.setTimestamp(3, Timestamp.valueOf(commentaire.getDateCreation()));
            ps.setInt(4, commentaire.getTopicId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        commentaire.setId(generatedKeys.getInt(1));
                    }
                }
            }
            System.out.println("Commentaire ajouté avec ID: " + commentaire.getId());
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du commentaire: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Commentaire commentaire) {
        String req = "UPDATE commentaire SET contenu = ?, auteur = ?, dateCreation = ?, topic_id = ? WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, commentaire.getContenu());
            ps.setString(2, commentaire.getAuteur());
            ps.setTimestamp(3, Timestamp.valueOf(commentaire.getDateCreation()));
            ps.setInt(4, commentaire.getTopicId());
            ps.setInt(5, commentaire.getId());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Commentaire mis à jour");
            } else {
                System.out.println("Aucun commentaire trouvé avec ID: " + commentaire.getId());
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Commentaire commentaire) {
        String req = "DELETE FROM commentaire WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, commentaire.getId());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Commentaire supprimé");
            } else {
                System.out.println("Aucun commentaire trouvé avec ID: " + commentaire.getId());
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        String req = "DELETE FROM commentaire WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Commentaire supprimé avec succès.");
            } else {
                System.out.println("Aucun commentaire trouvé avec ID: " + id);
            }
        } catch (SQLException e) {
            System.err.println("Erreur de suppression du commentaire: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObservableList<Commentaire> getAll() {
        ObservableList<Commentaire> commentaires = FXCollections.observableArrayList();
        String req = "SELECT * FROM commentaire ORDER BY dateCreation DESC";

        try (Statement st = cnx.createStatement(); ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                Commentaire c = new Commentaire(
                        rs.getInt("id"),
                        rs.getString("contenu"),
                        rs.getString("auteur"),
                        rs.getTimestamp("dateCreation").toLocalDateTime(),
                        rs.getInt("topic_id")
                );
                commentaires.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des commentaires: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return commentaires;
    }

    @Override
    public Commentaire getOne(int id) {
        String req = "SELECT * FROM commentaire WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Commentaire(
                            rs.getInt("id"),
                            rs.getString("contenu"),
                            rs.getString("auteur"),
                            rs.getTimestamp("dateCreation").toLocalDateTime(),
                            rs.getInt("topic_id")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du commentaire: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Commentaire> getAllCommentaires() {
        return List.of(); // À implémenter si besoin
    }

    public void ajouter(Commentaire commentaire) {
    }

    public Commentaire getCommentairesParTopic(int id) {
        return null;
    }
}
