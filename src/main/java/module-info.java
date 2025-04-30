module tn.esprit {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens tn.esprit to javafx.fxml;
    opens tn.esprit.controllers to javafx.fxml;
    opens tn.esprit.entities to javafx.base;

    exports tn.esprit;
    exports tn.esprit.controllers;
    exports tn.esprit.entities;
}