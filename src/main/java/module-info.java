module tn.esprit {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.httpcomponents.httpclient;
    requires com.fasterxml.jackson.databind;
    requires org.apache.httpcomponents.httpcore;
    requires java.net.http;
    requires com.google.gson;

    opens tn.esprit to javafx.fxml;
    opens tn.esprit.controllers to javafx.fxml;
    opens tn.esprit.entities to javafx.base;

    exports tn.esprit;
    exports tn.esprit.controllers;
    exports tn.esprit.entities;
}