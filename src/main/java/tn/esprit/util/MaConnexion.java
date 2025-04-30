package tn.esprit.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MaConnexion {
    private static MaConnexion instance;
    private Connection cnx;

    private final String URL = "jdbc:mysql://localhost:3306/db_name";
    private final String USER = "root";
    private final String PASSWORD = "";

    private MaConnexion() {
        try {
            cnx = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexion à la base de données établie");
        } catch (SQLException e) {
            System.err.println("Erreur de connexion: " + e.getMessage());
        }
    }

    public static MaConnexion getInstance() {
        if (instance == null) {
            instance = new MaConnexion();
        }
        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }
}