package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import tn.esprit.util.MaConnexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StatsController {

    @FXML
    private BarChart<String, Number> commentChart;

    public void initialize() {
        loadTop5Posts();
    }

    private void loadTop5Posts() {
        String query = """
            SELECT p.id AS post_id, COUNT(c.id) AS comment_count
            FROM post p
            LEFT JOIN commentaire c ON p.id = c.post_id
            GROUP BY p.id
            ORDER BY comment_count DESC
            LIMIT 5;
        """;

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Top 5 Posts");

        try (Connection conn = MaConnexion.getInstance().getCnx();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String postId = "Post " + rs.getInt("post_id");
                int count = rs.getInt("comment_count");
                series.getData().add(new XYChart.Data<>(postId, count));
            }

            commentChart.getData().add(series);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
