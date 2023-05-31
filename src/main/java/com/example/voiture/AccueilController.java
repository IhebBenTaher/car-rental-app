package com.example.voiture;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AccueilController implements Initializable {
    private ArrayList<String>l1=new ArrayList<>();
    private ArrayList<String>l2=new ArrayList<>();
    private ArrayList<String>l3=new ArrayList<>();
    private ArrayList<Integer>l4=new ArrayList<>();
    private ArrayList<Image>l5=new ArrayList<>();
    int c=-5;
    @FXML
    private Button b1;
    @FXML
    private Label t;
    @FXML
    private Button b2;

    @FXML
    private TextField t1;

    @FXML
    private TextField t2;

    @FXML
    private TextField t3;

    @FXML
    private TextField t4;

    @FXML
    private ImageView t5;
    @FXML
    void a1(ActionEvent event) throws SQLException {
        if(c<l1.size()-1&&c>-1){
            t1.setText(l1.get(++c));t2.setText(l2.get(c));t3.setText(l3.get(c));t4.setText(l4.get(c)+"");
            t5.setImage(l5.get(c));
        }
    }
    @FXML
    void a2(ActionEvent event) throws SQLException {
        if(c>0){
            t1.setText(l1.get(--c));t2.setText(l2.get(c));t3.setText(l3.get(c));t4.setText(l4.get(c)+"");
            t5.setImage(l5.get(c));
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection con=Base.openConnection();
            Statement s= con.createStatement();
            ResultSet rs=s.executeQuery("select count(immat) from voiture where immat not in (select idm from loc where sysdate between dd and df+1)");
            rs.next();
            t.setText(rs.getInt(1)+" voitures disponibles");
            rs=s.executeQuery("select * from voiture where immat not in(select idm from loc where sysdate between dd and df+1)");
            while(rs.next()){
                l1.add(rs.getString(1));l2.add(rs.getString(2));l3.add(rs.getString(3));l4.add(rs.getInt(4));
                byte []b=rs.getBlob(5).getBytes(1,(int)rs.getBlob(5).length());
                l5.add(new Image(new ByteArrayInputStream(b),t5.getFitWidth(),t5.getFitHeight(),true,true));
                c=0;
            }
            if(c==0){
                t1.setText(l1.get(0));t2.setText(l2.get(c));t3.setText(l3.get(c));t4.setText(l4.get(c)+"");
                t5.setImage(l5.get(c));
            }
            else {
                t5.setImage(new Image("C:\\Users\\iheb\\IdeaProjects\\voiture\\src\\main\\resources\\images\\first.png",t5.getFitWidth(),t5.getFitHeight(),true,true));
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
