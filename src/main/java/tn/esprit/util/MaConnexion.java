package tn.esprit.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MaConnexion {
    final String URL = "jdbc:mysql://localhost:3306/workshop";
    final String USER = "root";
    final String PWD = "";

    private Connection cnx;
    private static MaConnexion instance;

    public Connection getCnx() {
        return cnx;
    }

    public static MaConnexion getInstance() {
        if (instance == null)
            instance = new MaConnexion();
        return instance;
    }

    private MaConnexion() {
        try {
            cnx = DriverManager.getConnection(URL, USER, PWD);
            System.out.println("Connexion etablie avec succes");
        } catch (SQLException e) {
            System.err.println("Erreur de connexion: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}