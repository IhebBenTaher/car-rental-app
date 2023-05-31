package com.example.voiture;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    private Button b1;

    @FXML
    private Button b2;

    @FXML
    private Button b3;

    @FXML
    private Button b4;

    @FXML
    private Button b5;
    @FXML
    private Button b6;
    @FXML
    private Button b7;
    @FXML
    private Button b8;
    @FXML
    private Pane show;

    @FXML
    void m1(ActionEvent event) throws IOException {
        show.getChildren().removeAll();
        Pane p= FXMLLoader.load(getClass().getResource("accueil.fxml"));
        show.getChildren().setAll(p);
    }

    @FXML
    void m2(ActionEvent event) throws IOException {
        show.getChildren().removeAll();
        Pane p= FXMLLoader.load(getClass().getResource("voiture.fxml"));
        show.getChildren().setAll(p);
    }

    @FXML
    void m3(ActionEvent event) throws IOException {
        Pane p= FXMLLoader.load(getClass().getResource("client.fxml"));
        show.getChildren().setAll(p);
    }

    @FXML
    void m4(ActionEvent event) throws IOException {
        Pane p= FXMLLoader.load(getClass().getResource("location.fxml"));
        show.getChildren().setAll(p);
    }

    @FXML
    void m5(ActionEvent event) throws IOException {
        Pane p= FXMLLoader.load(getClass().getResource("contrat.fxml"));
        show.getChildren().setAll(p);
    }
    @FXML
    void m6(ActionEvent event) throws IOException {
        Pane p= FXMLLoader.load(getClass().getResource("stat1.fxml"));
        show.getChildren().setAll(p);
    }
    @FXML
    void m7(ActionEvent event) throws IOException {
        Pane p= FXMLLoader.load(getClass().getResource("stat2.fxml"));
        show.getChildren().setAll(p);
    }
    @FXML
    void m8(ActionEvent event) throws IOException {
        b8.getScene().getWindow().hide();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Pane p= FXMLLoader.load(getClass().getResource("accueil.fxml"));
            show.getChildren().add(p);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
