package tn.esprit.services;

import tn.esprit.entities.Post;
import tn.esprit.util.MaConnexion;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostService {

    private Connection cnx;

    public PostService() {
        cnx = MaConnexion.getInstance().getCnx();
    }

    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        String req = "SELECT * FROM post";

        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                int id = rs.getInt("id");
                int topicId = rs.getInt("topic_id");
                String titre = rs.getString("titre");
                String contenu = rs.getString("contenu");
                String auteur = rs.getString("auteur");

                // ⚠️ Nom correct de la colonne
                Timestamp ts = rs.getTimestamp("dateCreation");
                LocalDateTime dateCreation = ts != null ? ts.toLocalDateTime() : null;

                Post p = new Post(id, topicId, titre, contenu, auteur, dateCreation);
                posts.add(p);
            }

            System.out.println("Nombre de posts récupérés : " + posts.size());

        } catch (SQLException e) {
            System.err.println("Erreur dans getAll : " + e.getMessage());
        }

        return posts;
    }
    public void ajouter(Post newPost) {
    }

    public void delete(int id) {
    }

    public void add(Post post) {
    }
}




