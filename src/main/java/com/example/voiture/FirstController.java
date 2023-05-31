package com.example.voiture;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;

public class FirstController {
    @FXML
    private StackPane sp;
    @FXML
    private Label l;
    @FXML
    private Button b1;

    @FXML
    private Button b2;

    @FXML
    private Button b3;
    @FXML
    private TextField t1;
    @FXML
    void dho(MouseEvent event) {
        Button b=(Button) event.getSource();
        b.setStyle("-fx-background-color:transparent;-fx-text-fill:white;-fx-border-color:white;-fx-border-radius:7;");
    }
    @FXML
    void hov(MouseEvent event) {
        Button b=(Button) event.getSource();
        b.setStyle("-fx-background-color:white;-fx-text-fill:#1a2a31;-fx-border-color:white;-fx-border-radius:7;");
    }
    @FXML
    void move1(ActionEvent event) {
        TranslateTransition tt=new TranslateTransition();
        l.setText("S'inscrire");
        b3.setText("S'inscrire");
        tt.setByX(-270);
        tt.setDuration(Duration.millis(300));
        tt.setNode(sp);
        tt.play();
    }
    @FXML
    void move2(ActionEvent event) {
        TranslateTransition tt=new TranslateTransition();
        l.setText("Se connecter");
        b3.setText("Se connecter");
        tt.setByX(270);
        tt.setDuration(Duration.millis(300));
        tt.setNode(sp);
        tt.play();
    }
    @FXML
    private PasswordField t2;
    @FXML
    void con(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        if(b3.getText().equals("Se connecter")){
            if(Base.chercheAdmin(t1.getText(), t2.getText())){
                Stage st=(Stage) b3.getScene().getWindow();
                Parent root=FXMLLoader.load(getClass().getResource("home.fxml"));
                Scene s= new Scene(root);
                st.setResizable(false);
                st.setScene(s);
                st.show();
            }
            else{
                Alert a=new Alert(Alert.AlertType.WARNING);
                a.setTitle("Alerte");
                a.setContentText("Vérifier vos informations!");
                a.show();
            }
        }
        else{
            if(!t1.getText().equals("") && !t2.getText().equals("")){
                try{
                    Base.insertAdmin(t1.getText(), t2.getText());
                    Alert a=new Alert(Alert.AlertType.CONFIRMATION);
                    a.setTitle("Confirmation");
                    a.setContentText("Vous êtes inscri avec succès!");
                    a.show();
                }catch (SQLException e){
                    Alert a=new Alert(Alert.AlertType.WARNING);
                    a.setTitle("Alerte");
                    a.setContentText("Changer du nom d'utilisateur!");
                    a.show();
                }
            }
        }
    }
}
