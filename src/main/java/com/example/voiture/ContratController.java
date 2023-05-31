package com.example.voiture;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;

import static java.time.Period.*;

public class ContratController implements Initializable {

    @FXML
    private Button b1;
    private ObservableList<Location>l=FXCollections.observableArrayList();
    @FXML
    private Button b2;
    @FXML
    private Button b3;
    @FXML
    private ComboBox<String> c1;
    @FXML
    private ComboBox<String> c2;
    @FXML
    private ComboBox<String> c3;
    @FXML
    private TableColumn<Location,String> cl1;
    @FXML
    private TableColumn<Location,String> cl2;
    @FXML
    private TableColumn<Location,String> cl3;
    @FXML
    private TableColumn<Location, LocalDate> cl4;
    @FXML
    private TableColumn<Location, LocalDate> cl5;
    @FXML
    private DatePicker d1;
    @FXML
    private DatePicker d2;
    @FXML
    private TextField t1;
    @FXML
    private TextField t2;
    @FXML
    private TextField t3;
    @FXML
    private TextField t4;
    @FXML
    private TextField t5;
    @FXML
    private TextField t6;
    @FXML
    private TableView<Location> tab;
    @FXML
    void a1(ActionEvent event) throws SQLException, ClassNotFoundException {
        String sql="select * from loc where";
        if(c1.getSelectionModel().getSelectedItem()!=null){
            sql+=" idl='"+c1.getSelectionModel().getSelectedItem()+"' and";
        }
        if(c2.getSelectionModel().getSelectedItem()!=null){
            sql+=" idcl='"+c2.getSelectionModel().getSelectedItem()+"' and";
        }
        if(c3.getSelectionModel().getSelectedItem()!=null){
            sql+=" idm='"+c3.getSelectionModel().getSelectedItem()+"' and";
        }
        if(d1.getValue()!=null){
            sql+=" dd=to_date('"+d1.getValue()+"','yyyy-mm-dd') and";
        }
        System.out.println(d1.getValue());
        if(d2.getValue()!=null){
            sql+=" df=to_date('"+d2.getValue()+"','yyyy-mm-dd') and";
        }
        if(sql.length()<27){
            alert("Remplir les champs!");
        }
        else{
            update1(sql.substring(0,sql.length()-4));
            if(l.size()==1){
                c1.getSelectionModel().select(l.get(0).getId());
                c2.getSelectionModel().select(l.get(0).getIdc());
                c3.getSelectionModel().select(l.get(0).getIdm());
                d1.setValue(l.get(0).getDd());
                d2.setValue(l.get(0).getDf());
                Connection con=Base.openConnection();
                Statement s=con.createStatement();
                ResultSet rs=s.executeQuery("select nom, prenom from cl where idc='"+c2.getSelectionModel().getSelectedItem()+"'");
                rs.next();t1.setText(rs.getString(1));t2.setText(rs.getString(2));
                rs=s.executeQuery("select modele, prix from voiture where immat='"+c3.getSelectionModel().getSelectedItem()+"'");
                rs.next();t3.setText(rs.getString(1));t4.setText(rs.getInt(2)+"");
                con.close();
                t5.setText(""+(Period.between(d1.getValue(),d2.getValue()).getDays()+1));
                t6.setText(""+(Integer.parseInt(t5.getText())*Integer.parseInt(t4.getText())));
            }
        }
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
        if(c1.getSelectionModel().getSelectedItem()==null||c1.getSelectionModel().getSelectedItem().isEmpty()){
            alert("Taper l'identifiant du location à modifier!");
        }
        else{
            if((c2.getSelectionModel().getSelectedItem()==null||c2.getSelectionModel().getSelectedItem().isEmpty())&&(c3.getSelectionModel().getSelectedItem()==null||c3.getSelectionModel().getSelectedItem().isEmpty())&&d1.getValue()==null&&d2.getValue()==null){
                alert("Remplir les champs!");
            }
            else {
                Connection con=Base.openConnection();
                Statement s=con.createStatement();
                ResultSet r=s.executeQuery("select * from loc where idl='"+c1.getSelectionModel().getSelectedItem()+"'");
                r.next();
                String o1=r.getString(1),o2=r.getString(2),o3=r.getString(3);
                LocalDate o4=r.getDate(4).toLocalDate(),o5=r.getDate(5).toLocalDate();
                int o6=r.getInt(6),o7=r.getInt(7);
                String m1=o1,m2=c2.getSelectionModel().getSelectedItem()==null?o2:c2.getSelectionModel().getSelectedItem(),m3=c3.getSelectionModel().getSelectedItem()==null?o3:c3.getSelectionModel().getSelectedItem();
                LocalDate m4=d1.getValue()==null?o4:d1.getValue(),m5=d2.getValue()==null?o5:d2.getValue();
                s.executeUpdate("delete from loc where idl='"+o1+"'");
                if(m4.isBefore(LocalDate.now())||m5.isBefore(m4)||!verif(m4,m5,m3)){
                    s.executeUpdate("insert into loc values('"+o1+"','"+o2+"','"+o3+"',to_date('"+o4+"','yyyy-mm-dd'),to_date('"+o5+"','yyyy-mm-dd'),"+o6+","+o7+")");
                    con.close();
                    alert("Vérifier les dates!");
                }
                else {
                    int m6=Period.between(m4,m5).getDays()+1;
                    r=s.executeQuery("select prix from voiture where immat='"+m3+"'");
                    System.out.println("select prix from voiture where immat='"+m3+"'");
                    r.next();
                    int m7=m6*r.getInt(1);
                    s.executeUpdate("insert into loc values('"+m1+"','"+m2+"','"+m3+"',to_date('"+m4+"','yyyy-mm-dd'),to_date('"+m5+"','yyyy-mm-dd'),"+m6+","+m7+")");
                    con.close();
                    update("select * from loc");
                    conf("Location modifiée avec succès");
                }
            }
        }
    }
    public boolean verif(LocalDate a, LocalDate b, String c) throws SQLException, ClassNotFoundException {
        Connection con = Base.openConnection();
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery("select dd, df from loc where idm='" + c + "'");
        while (rs.next()) {
            if (!(a.isAfter(rs.getDate(2).toLocalDate()) || b.isBefore(rs.getDate(1).toLocalDate()))) {
                return false;
            }
        }
        return true;
    }
    @FXML
    void a3(ActionEvent event) throws SQLException, ClassNotFoundException {
        String sql="delete from loc where";
        if(c1.getSelectionModel().getSelectedItem()!=null){
            sql+=" idl='"+c1.getSelectionModel().getSelectedItem()+"' and";
        }
        if(c2.getSelectionModel().getSelectedItem()!=null){
            sql+=" idcl='"+c2.getSelectionModel().getSelectedItem()+"' and";
        }
        if(c3.getSelectionModel().getSelectedItem()!=null){
            sql+=" idm='"+c3.getSelectionModel().getSelectedItem()+"' and";
        }
        if(d1.getValue()!=null){
            sql+=" dd=to_date('"+d1.getValue()+"','yyyy-mm-dd') and";
        }
        System.out.println(d1.getValue());
        if(d2.getValue()!=null){
            sql+=" df=to_date('"+d2.getValue()+"','yyyy-mm-dd') and";
        }
        if(sql.length()<25){
            alert("Remplir les champs!");
        }
        else{
            Connection con=Base.openConnection();
            Statement s=con.createStatement();
            s.executeUpdate(sql.substring(0,sql.length()-4));
            update("select * from loc");
            c1.getSelectionModel().clearSelection();c2.getSelectionModel().clearSelection();c3.getSelectionModel().clearSelection();
            t1.setText("");t2.setText("");t3.setText("");t4.setText("");t5.setText("");t6.setText("");
            d1.setValue(null);d2.setValue(null);
            conf("Les locations sont supprimées!");
        }
    }
    private ObservableList<String>l1=FXCollections.observableArrayList();
    private void update(String k) throws SQLException, ClassNotFoundException {
        Connection con=Base.openConnection();
        Statement s=con.createStatement();
        l.clear();
        l1.clear();
        ResultSet rs=s.executeQuery(k);
        while (rs.next()){
            l.add(new Location(rs.getString(1),rs.getString(2),rs.getString(3),rs.getDate(4).toLocalDate(),rs.getDate(5).toLocalDate()));
            l1.add(rs.getString(1));
        }
        cl1.setCellValueFactory(new PropertyValueFactory<Location,String>("id"));
        cl2.setCellValueFactory(new PropertyValueFactory<Location,String>("idc"));
        cl3.setCellValueFactory(new PropertyValueFactory<Location,String>("idm"));
        cl4.setCellValueFactory(new PropertyValueFactory<Location,LocalDate>("dd"));
        cl5.setCellValueFactory(new PropertyValueFactory<Location,LocalDate>("df"));
        tab.setItems(l);
        c1.setItems(l1);
        con.close();
    }
    private void update1(String k) throws SQLException, ClassNotFoundException {
        Connection con=Base.openConnection();
        Statement s=con.createStatement();
        l.clear();
        ResultSet rs=s.executeQuery(k);
        while (rs.next()){
            l.add(new Location(rs.getString(1),rs.getString(2),rs.getString(3),rs.getDate(4).toLocalDate(),rs.getDate(5).toLocalDate()));
        }
        cl1.setCellValueFactory(new PropertyValueFactory<Location,String>("id"));
        cl2.setCellValueFactory(new PropertyValueFactory<Location,String>("idc"));
        cl3.setCellValueFactory(new PropertyValueFactory<Location,String>("idm"));
        cl4.setCellValueFactory(new PropertyValueFactory<Location,LocalDate>("dd"));
        cl5.setCellValueFactory(new PropertyValueFactory<Location,LocalDate>("df"));
        tab.setItems(l);
        con.close();
    }
    private void Remplircv() throws SQLException, ClassNotFoundException {
        Connection con=Base.openConnection();
        Statement s=con.createStatement();
        ObservableList<String> c= FXCollections.observableArrayList();
        ObservableList<String> v= FXCollections.observableArrayList();
        ResultSet rs=s.executeQuery("select idc from cl");
        while (rs.next()){
            c.add(rs.getString(1));
        }
        c2.setItems(c);
        rs=s.executeQuery("select immat from voiture");
        while (rs.next()){
            v.add(rs.getString(1));
        }
        c3.setItems(v);
        con.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Remplircv();
            update("select * from loc");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}