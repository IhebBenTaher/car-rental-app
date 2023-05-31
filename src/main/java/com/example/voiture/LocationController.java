package com.example.voiture;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.converter.LocalDateTimeStringConverter;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.ResourceBundle;

public class LocationController implements Initializable {

    @FXML
    private Button b1;

    @FXML
    private Button b2;

    @FXML
    private Button b3;

    @FXML
    private ComboBox<String> c1;

    @FXML
    private TextField c2;

    @FXML
    private TextField c3;

    @FXML
    private TextField c4;

    @FXML
    private TextField l1;

    @FXML
    private DatePicker l2;

    @FXML
    private DatePicker l3;

    @FXML
    private TextField l4;

    @FXML
    private TextField l5;

    @FXML
    private ComboBox<String> v1;

    @FXML
    private TextField v2;

    @FXML
    private TextField v3;

    @FXML
    private TextField v4;

    @FXML
    private ImageView v5;

    @FXML
    void a1(ActionEvent event) throws SQLException, ClassNotFoundException {
        Connection con=Base.openConnection();
        Statement s=con.createStatement();
        ResultSet rs=s.executeQuery("Select modele, color, prix, image from voiture where immat='"+v1.getSelectionModel().getSelectedItem()+"'");
        if(rs.next()){
            v2.setText(rs.getString(1));
            v3.setText(rs.getString(2));
            v4.setText(rs.getInt(3)+"");
            byte []b=null;
            b=rs.getBlob(4).getBytes(1,(int)rs.getBlob(4).length());
            v5.setImage(new Image(new ByteArrayInputStream(b),v5.getFitWidth(), v5.getFitHeight(), true,true));
            LocalDate ld1=l2.getValue(),ld2=l3.getValue();
            if(!(ld1==null || ld2==null || ld1.isBefore(LocalDate.now()) ||ld1.isAfter(ld2))){
                l5.setText(((Period.between(ld1,ld2).getDays()+1)*Integer.parseInt(v4.getText()))+"");
            }
        }
        else{
            alert("Choisir une immatricule!");
        }
        con.close();
    }
    void alert(String b){
        Alert a=new Alert(Alert.AlertType.WARNING);
        a.setTitle("Avertissement");
        a.setContentText(b);
        a.show();
    }
    void conf(String b){
        Alert a=new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Confirmation");
        a.setContentText(b);
        a.show();
    }
    @FXML
    void a2(ActionEvent event) throws SQLException, ClassNotFoundException {
        Connection con=Base.openConnection();
        Statement s=con.createStatement();
        ResultSet rs=s.executeQuery("Select nom, prenom, age from cl where idc='"+c1.getSelectionModel().getSelectedItem()+"'");
        if(rs.next()){
            c2.setText(rs.getString(1));
            c3.setText(rs.getString(2));
            c4.setText(rs.getInt(3)+"");
        }
        else{
            alert("Choisir un identifiant!");
        }
        con.close();
    }
    @FXML
    void put(ActionEvent event){
        LocalDate ld1=l2.getValue(),ld2=l3.getValue();
        if(!(ld1==null || ld2==null || ld1.isBefore(LocalDate.now()) ||ld1.isAfter(ld2))){
            l4.setText((Period.between(ld1,ld2).getDays()+1)+"");
            if(!v4.getText().isEmpty()){
                l5.setText(((Period.between(ld1,ld2).getDays()+1)*Integer.parseInt(v4.getText()))+"");
            }
        }
    }
    @FXML
    void a3(ActionEvent event) throws SQLException, ClassNotFoundException {
        if(l1.getText().isEmpty()){
            alert("Taper un identifiant de location!");
        }
        else{
            if(l1.getText().length()>20){
                alert("Vérifier l'identifiant de location (longueur maximale 20 caractères)!");
            }
            else {
                if(v2.getText().isEmpty()){
                    alert("Taper une immatricule!");
                }
                else{
                    if(c2.getText().isEmpty()){
                        alert("Taper l'identifiant du client!");
                    }
                    else{
                        LocalDate ld1=l2.getValue();
                        LocalDate ld2=l3.getValue();
                        Date d=new Date();
                        if(ld1==null || ld2==null || ld1.isBefore(LocalDate.now()) ||ld1.isAfter(ld2) || !verif(ld1,ld2,v1.getSelectionModel().getSelectedItem())){
                            alert("Vérifier les dates!");
                        }
                        else {
                            Connection con=Base.openConnection();
                            PreparedStatement s=con.prepareStatement("insert into loc values(?,?,?,?,?,?,?)");
                            s.setString(1,l1.getText());s.setString(2,c1.getSelectionModel().getSelectedItem());
                            s.setString(3,v1.getSelectionModel().getSelectedItem());s.setDate(4, java.sql.Date.valueOf(ld1));
                            s.setDate(5, java.sql.Date.valueOf(ld2));s.setInt(6,Period.between(ld1,ld2).getDays()+1);
                            s.setInt(7,(Period.between(ld1,ld2).getDays()+1)*Integer.parseInt(v4.getText()));
                            try{
                                s.executeUpdate();
                                l1.setText("");l4.setText("");l5.setText("");l3.setValue(null);l2.setValue(null);
                                c1.getSelectionModel().clearSelection();v1.getSelectionModel().clearSelection();
                                c2.setText("");c3.setText("");c4.setText("");v2.setText("");v3.setText("");v4.setText("");
                                v5.setImage(new Image("C:\\Users\\iheb\\IdeaProjects\\voiture\\src\\main\\resources\\images\\first.png",v5.getFitWidth(), v5.getFitHeight(), true,true));
                                //l4.setText((Period.between(ld1,ld2).getDays()+1)+"");l5.setText(((Period.between(ld1,ld2).getDays()+1)*Integer.parseInt(v4.getText()))+"");
                                conf("Location ajoutée avec succès!");
                            }
                            catch (SQLException e){alert("Identifiant déjà utilisé!");}
                            con.close();
                        }
                    }
                }
            }
        }
    }
    public boolean verif(LocalDate a, LocalDate b, String c) throws SQLException, ClassNotFoundException {
        Connection con=Base.openConnection();
        Statement s=con.createStatement();
        ResultSet rs=s.executeQuery("select dd, df from loc where idm='"+c+"'");
        while (rs.next()){
            if(!(a.isAfter(rs.getDate(2).toLocalDate())||b.isBefore(rs.getDate(1).toLocalDate()))) {
                return false;
            }
        }
        return true;
    }
    public void remplir() throws SQLException, ClassNotFoundException {
        ObservableList<String> lv= FXCollections.observableArrayList();
        ObservableList<String> lc= FXCollections.observableArrayList();
        Connection con=Base.openConnection();
        Statement s=con.createStatement();
        ResultSet rs=s.executeQuery("select immat from voiture");
        while (rs.next())
            lv.add(rs.getString(1));
        v1.setItems(lv);
        rs=s.executeQuery("select idc from cl");
        while (rs.next())
            lc.add(rs.getString(1));
        c1.setItems(lc);
        con.close();
        v5.setImage(new Image("C:\\Users\\iheb\\IdeaProjects\\voiture\\src\\main\\resources\\images\\first.png",v5.getFitWidth(), v5.getFitHeight(), true,true));
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            remplir();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
