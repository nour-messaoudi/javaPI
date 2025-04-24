package tn.esprit.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MaConnexion {
    private static MaConnexion instance;
    private Connection cnx;

    private static final String URL = "jdbc:mysql://localhost:3306/workshop";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private MaConnexion() {
        try {
            cnx = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexion établie avec la base !");
        } catch (SQLException e) {
            System.err.println("Erreur de connexion : " + e.getMessage());
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
