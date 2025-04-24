package tn.esprit.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.entities.Post;
import tn.esprit.interfaces.iService;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

public class PostService implements iService<Post> {
    Connection cnx = MaConnexion.getInstance().getCnx();

    @Override
    public void add(Post post) {
        String req = "INSERT INTO `post`(`topic_id`, `titre`, `contenu`, `auteur`, `dateCreation`) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, post.getTopicId());
            ps.setString(2, post.getTitre());
            ps.setString(3, post.getContenu());
            ps.setString(4, post.getAuteur());
            ps.setTimestamp(5, Timestamp.valueOf(post.getDateCreation()));

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        post.setId(generatedKeys.getInt(1));
                    }
                }
            }
            System.out.println("Post added successfully with ID: " + post.getId());
        } catch (SQLException e) {
            System.err.println("Error adding post: " + e.getMessage());
            throw new RuntimeException("Failed to add post", e);
        }
    }

    @Override
    public void update(Post post) {
        String req = "UPDATE `post` SET `topic_id`=?, `titre`=?, `contenu`=?, `auteur`=?, `dateCreation`=? WHERE `id`=?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, post.getTopicId());
            ps.setString(2, post.getTitre());
            ps.setString(3, post.getContenu());
            ps.setString(4, post.getAuteur());
            ps.setTimestamp(5, Timestamp.valueOf(post.getDateCreation()));
            ps.setInt(6, post.getId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Post updated successfully");
            } else {
                System.out.println("No post found with ID: " + post.getId());
            }
        } catch (SQLException e) {
            System.err.println("Error updating post: " + e.getMessage());
            throw new RuntimeException("Failed to update post", e);
        }
    }

    @Override
    public void delete(Post post) {
        String req = "DELETE FROM `post` WHERE `id`=?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, post.getId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Post deleted successfully");
            } else {
                System.out.println("No post found with ID: " + post.getId());
            }
        } catch (SQLException e) {
            System.err.println("Error deleting post: " + e.getMessage());
            throw new RuntimeException("Failed to delete post", e);
        }
    }

    @Override
    public ObservableList<Post> getAll() {
        ObservableList<Post> posts = FXCollections.observableArrayList();
        String req = "SELECT * FROM `post` ORDER BY `dateCreation` DESC";

        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(req)) {

            while (rs.next()) {
                Post post = new Post(
                        rs.getInt("id"),
                        rs.getInt("topic_id"),
                        rs.getString("titre"),
                        rs.getString("contenu"),
                        rs.getString("auteur"),
                        rs.getTimestamp("dateCreation").toLocalDateTime()
                );
                posts.add(post);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching posts: " + e.getMessage());
            throw new RuntimeException("Failed to fetch posts", e);
        }
        return posts;
    }

    @Override
    public Post getOne(int id) {
        String req = "SELECT * FROM `post` WHERE `id`=?";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Post(
                            rs.getInt("id"),
                            rs.getInt("topic_id"),
                            rs.getString("titre"),
                            rs.getString("contenu"),
                            rs.getString("auteur"),
                            rs.getTimestamp("dateCreation").toLocalDateTime()
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching post with ID " + id + ": " + e.getMessage());
            throw new RuntimeException("Failed to fetch post", e);
        }
        return null;
    }

    public ObservableList<Post> getByTopic(int topicId) {
        ObservableList<Post> posts = FXCollections.observableArrayList();
        String req = "SELECT * FROM `post` WHERE `topic_id`=? ORDER BY `dateCreation` DESC";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, topicId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Post post = new Post(
                            rs.getInt("id"),
                            rs.getInt("topic_id"),
                            rs.getString("titre"),
                            rs.getString("contenu"),
                            rs.getString("auteur"),
                            rs.getTimestamp("dateCreation").toLocalDateTime()
                    );
                    posts.add(post);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching posts for topic " + topicId + ": " + e.getMessage());
            throw new RuntimeException("Failed to fetch posts by topic", e);
        }
        return posts;
    }

    public List<Post> getByTopicId(int topicId) {
        return List.of();
    }

    public List<Post> getAllPosts() {
        return List.of();
    }

    public void ajouter(Post newPost) {
    }
}