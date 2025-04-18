package tn.esprit.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.entities.Topic;
import tn.esprit.interfaces.iService;
import tn.esprit.util.MaConnexion;

import java.sql.*;

public class TopicService implements iService<Topic> {
    Connection cnx = MaConnexion.getInstance().getCnx();

    @Override
    public void add(Topic topic) {
        String req = "INSERT INTO `topic`(`titre`, `description`, `createur`, `dateCreation`) VALUES (?,?,?,?)";
        try (PreparedStatement ps = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, topic.getTitre());
            ps.setString(2, topic.getDescription());
            ps.setString(3, topic.getCreateur());
            ps.setTimestamp(4, Timestamp.valueOf(topic.getDateCreation()));

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        topic.setId(generatedKeys.getInt(1));
                    }
                }
            }
            System.out.println("Topic added successfully with ID: " + topic.getId());
        } catch (SQLException e) {
            System.err.println("Error adding topic: " + e.getMessage());
            throw new RuntimeException("Failed to add topic", e);
        }
    }

    @Override
    public void update(Topic topic) {
        String req = "UPDATE `topic` SET `titre`=?, `description`=?, `createur`=?, `dateCreation`=? WHERE `id`=?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, topic.getTitre());
            ps.setString(2, topic.getDescription());
            ps.setString(3, topic.getCreateur());
            ps.setTimestamp(4, Timestamp.valueOf(topic.getDateCreation()));
            ps.setInt(5, topic.getId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Topic updated successfully");
            } else {
                System.out.println("No topic found with ID: " + topic.getId());
            }
        } catch (SQLException e) {
            System.err.println("Error updating topic: " + e.getMessage());
            throw new RuntimeException("Failed to update topic", e);
        }
    }

    @Override
    public void delete(Topic topic) {
        String req = "DELETE FROM `topic` WHERE `id`=?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, topic.getId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Topic deleted successfully");
            } else {
                System.out.println("No topic found with ID: " + topic.getId());
            }
        } catch (SQLException e) {
            System.err.println("Error deleting topic: " + e.getMessage());
            throw new RuntimeException("Failed to delete topic", e);
        }
    }

    @Override
    public ObservableList<Topic> getAll() {
        ObservableList<Topic> topics = FXCollections.observableArrayList();
        String req = "SELECT * FROM `topic` ORDER BY `dateCreation` DESC";

        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(req)) {

            while (rs.next()) {
                Topic topic = new Topic(
                        rs.getInt("id"),
                        rs.getString("titre"),
                        rs.getString("description"),
                        rs.getString("createur"),
                        rs.getTimestamp("dateCreation").toLocalDateTime()
                );
                topics.add(topic);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching topics: " + e.getMessage());
            throw new RuntimeException("Failed to fetch topics", e);
        }
        return topics;
    }

    @Override
    public Topic getOne(int id) {
        String req = "SELECT * FROM `topic` WHERE `id`=?";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Topic(
                            rs.getInt("id"),
                            rs.getString("titre"),
                            rs.getString("description"),
                            rs.getString("createur"),
                            rs.getTimestamp("dateCreation").toLocalDateTime()
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching topic with ID " + id + ": " + e.getMessage());
            throw new RuntimeException("Failed to fetch topic", e);
        }
        return null;
    }
}