package com.example.voiture;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


public class ClientController implements Initializable {
    @FXML
    private TableView<Client> tab;
    @FXML
    private Button b1;

    @FXML
    private Button b2;

    @FXML
    private Button b3;

    @FXML
    private Button b4;

    @FXML
    private ComboBox<String> c1;

    @FXML
    private ComboBox<String> c2;

    @FXML
    private ComboBox<String> c3;

    @FXML
    private ComboBox<Integer> c4;

    @FXML
    private ComboBox<String> c5;

    @FXML
    private ComboBox<String> c6;

    @FXML
    private TableColumn<Client, String> cl1;

    @FXML
    private TableColumn<Client, String> cl2;

    @FXML
    private TableColumn<Client, String> cl3;

    @FXML
    private TableColumn<Client, Integer> cl4;

    @FXML
    private TableColumn<Client, String> cl5;

    @FXML
    private TableColumn<Client, String> cl6;

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
    private ObservableList<Client>l= FXCollections.observableArrayList();
    public void remplircl() throws SQLException, ClassNotFoundException {
        Connection con=Base.openConnection();
        Statement s=con.createStatement();
        ResultSet rs=s.executeQuery("select * from cl");
        ObservableSet<String> l2=FXCollections.observableSet();
        ObservableSet<String> l3=FXCollections.observableSet();
        ObservableSet<Integer> l4=FXCollections.observableSet();
        ObservableSet<String> l5=FXCollections.observableSet();
        ObservableSet<String> l6=FXCollections.observableSet();
        ObservableList<String>l22=FXCollections.observableArrayList();
        ObservableList<String>l1=FXCollections.observableArrayList();
        ObservableList<String>l33=FXCollections.observableArrayList();
        ObservableList<Integer>l44=FXCollections.observableArrayList();
        ObservableList<String>l55=FXCollections.observableArrayList();
        ObservableList<String>l66=FXCollections.observableArrayList();
        l1.add("Identifiant");l2.add("Nom");l3.add("Prénom");l4.add(0);l5.add("Sexe");l6.add("Ville");
        while(rs.next()){
            l.add(new Client(rs.getString(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getString(5),rs.getString(6)));
            l1.add(rs.getString(1));l2.add(rs.getString(2));l3.add(rs.getString(3));l4.add(rs.getInt(4));l5.add(rs.getString(5));l6.add(rs.getString(6));
        }
        con.close();
        cl1.setCellValueFactory(new PropertyValueFactory<Client,String>("idc"));
        cl2.setCellValueFactory(new PropertyValueFactory<Client,String>("nom"));
        cl3.setCellValueFactory(new PropertyValueFactory<Client,String>("prenom"));
        cl4.setCellValueFactory(new PropertyValueFactory<Client,Integer>("age"));
        cl5.setCellValueFactory(new PropertyValueFactory<Client,String>("sexe"));
        cl6.setCellValueFactory(new PropertyValueFactory<Client,String>("ville"));
        tab.setItems(l);
        for (String a:l2){
            l22.add(a);
        }
        for (String a:l3){
            l33.add(a);
        }
        for (int a:l4){
            l44.add(a);
        }
        for (String a:l5){
            l55.add(a);
        }
        for (String a:l6){
            l66.add(a);
        }
        c1.setItems(l1);c2.setItems(l22);c3.setItems(l33);c4.setItems(l44);c5.setItems(l55);c6.setItems(l66);
    }
    @FXML
    void a1(ActionEvent event) throws SQLException, ClassNotFoundException {
        boolean ajout=false;
        Connection con=Base.openConnection();
        l.clear();
        String sql="select * from cl";
        if(c1.getSelectionModel().getSelectedItem()!=null && !"Identifiant".equals(c1.getSelectionModel().getSelectedItem())){
            sql+=" where idc='"+c1.getSelectionModel().getSelectedItem()+"'";
            ajout=true;
        }
        if(c2.getSelectionModel().getSelectedItem()!=null && !"Nom".equals(c2.getSelectionModel().getSelectedItem())){
            if (!ajout) {
                sql+=" where nom='"+c2.getSelectionModel().getSelectedItem()+"'";
                ajout=true;
            }
            else
                sql+=" and nom='"+c2.getSelectionModel().getSelectedItem()+"'";
        }
        if(c3.getSelectionModel().getSelectedItem()!=null && !"Prénom".equals(c3.getSelectionModel().getSelectedItem())){
            if (!ajout) {
                sql+=" where prenom='"+c3.getSelectionModel().getSelectedItem()+"'";
                ajout=true;
            }
            else
                sql+=" and prenom='"+c3.getSelectionModel().getSelectedItem()+"'";
        }
        if(c4.getSelectionModel().getSelectedItem()!=null && !(c4.getSelectionModel().getSelectedItem()==0)){
            if (!ajout) {
                sql+=" where age="+c4.getSelectionModel().getSelectedItem();
                ajout=true;
            }
            else
                sql+=" and age="+c4.getSelectionModel().getSelectedItem();
        }
        if(c5.getSelectionModel().getSelectedItem()!=null && !"Sexe".equals(c5.getSelectionModel().getSelectedItem())){
            if (!ajout) {
                sql+=" where sexe='"+c5.getSelectionModel().getSelectedItem()+"'";
                ajout=true;
            }
            else
                sql+=" and sexe='"+c5.getSelectionModel().getSelectedItem()+"'";
        }
        if(c6.getSelectionModel().getSelectedItem()!=null && !"Ville".equals(c6.getSelectionModel().getSelectedItem())){
            if (!ajout) {
                sql+=" where ville='"+c6.getSelectionModel().getSelectedItem()+"'";
                ajout=true;
            }
            else
                sql+=" and ville='"+c6.getSelectionModel().getSelectedItem()+"'";
        }
        System.out.println(sql);
        Statement s=con.createStatement();
        ResultSet rs=s.executeQuery(sql);
        while (rs.next()){
            l.add(new Client(rs.getString(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getString(5),rs.getString(6)));
        }
        cl1.setCellValueFactory(new PropertyValueFactory<Client,String>("idc"));
        cl2.setCellValueFactory(new PropertyValueFactory<Client,String>("nom"));
        cl3.setCellValueFactory(new PropertyValueFactory<Client,String>("prenom"));
        cl4.setCellValueFactory(new PropertyValueFactory<Client,Integer>("age"));
        cl5.setCellValueFactory(new PropertyValueFactory<Client,String>("sexe"));
        cl6.setCellValueFactory(new PropertyValueFactory<Client,String>("ville"));
        tab.setItems(l);
        if(l.size()==1){
            t1.setText(l.get(0).getIdc());t2.setText(l.get(0).getNom());t3.setText(l.get(0).getPrenom());t4.setText(l.get(0).getAge()+"");t5.setText(l.get(0).getSexe());t6.setText(l.get(0).getVille());
        }else{
            t1.setText("");t2.setText("");t3.setText("");t4.setText("");t5.setText("");t6.setText("");
        }
        c1.getSelectionModel().select("Identifiant");c2.getSelectionModel().select("Nom");c3.getSelectionModel().select("Prénom");c4.getSelectionModel().select(0);c5.getSelectionModel().select("Sexe");c6.getSelectionModel().select("Ville");
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
        if (t2.getText().isEmpty() || t2.getText().length() > 20) {
            System.out.println("nom");
            alert("Vérifier le nom (Longueur maximale 20 caractères)!");
        } else {
            if (t3.getText().isEmpty() || t3.getText().length() > 20) {
                alert("Vérifier le prénom (Longueur maximale 20 caractères)!");
            } else {
                try{
                if (t4.getText().isEmpty() || Integer.parseInt(t4.getText()) < 18 || Integer.parseInt(t4.getText()) > 100) {
                    alert("L'âge doit être entre 18 et 100!");
                } else {
                    if (t5.getText().isEmpty() || !t5.getText().equals("Homme") && !t5.getText().equals("Femme")) {
                        alert("Le sexe doit être soit Homme soit Femme!");
                    } else {
                        if (t6.getText().isEmpty() || t6.getText().length() > 20) {
                            alert("Vérifier la ville (Longueur maximale 20 caractères)!");
                        }
                        else {
                            if (t1.getText().isEmpty()) {
                                alert("Le champs identifiant est vide!");
                            }
                            else{
                                Connection con = Base.openConnection();
                                String sql = "insert into cl values('" + t1.getText() + "','" + t2.getText() + "','" + t3.getText() + "'," + t4.getText() + ",'" + t5.getText() + "','" + t6.getText() + "')";
                                Statement st = con.createStatement();
                                try {
                                    st.executeUpdate(sql);
                                    l.clear();
                                    remplircl();
                                    con.close();
                                    t1.setText("");t2.setText("");t3.setText("");t4.setText("");t5.setText("");t6.setText("");
                                    conf("Le client est inséré avec succès!");
                                } catch (SQLException e) {
                                    alert("Identifiant déjà utilisé!");
                                }
                            }
                        }
                    }
                }}catch (NumberFormatException e){
                    alert("L'âge doit être entré en chiffres!");
                }
            }
        }
    }
    @FXML
    void check(KeyEvent event) {
        /*if((TextField)event.getSource()==t4){
            System.out.println("b");
            char a=((TextField) event.getSource()).getText().charAt(((TextField) event.getSource()).getText().length()-1);
            if(a!='0'&&a!='1'&&a!='2'&&a!='3'&&a!='4'&&a!='5'&&a!='6'&&a!='7'&&a!='8'&&a!='9'){
                System.out.println("e");
                event.consume();
                //t4.setText(t4.getText().substring(0,t4.getText().length()-1));

            }
        }
        else{
            if((TextField)event.getSource()==t5){
                if(((TextField) event.getSource()).getText().length()>10) {
                    t5.setText(t5.getText().substring(0,t5.getText().length()-1));
                    t5.setFocusTraversable(true);
                }
        }
            else {
                if(((TextField) event.getSource()).getText().length()>20) {
                    event.consume();
                }
            }
        }*/
    }
    @FXML
    void a3(ActionEvent event) throws SQLException, ClassNotFoundException {
        String sql="update cl set";
        boolean test=true;
        if(!t2.getText().isEmpty()){
            if(t2.getText().length() < 21){
                sql+=" nom='"+t2.getText()+"',";
            }
            else{
                test=false;
                alert("Vérifier le nom (Longueur maximale 20 caractères)!");
            }
        }
        if(!t3.getText().isEmpty()){
            if(t3.getText().length() < 21){
                sql+=" prenom='"+t3.getText()+"',";
            }
            else{
                test=false;
                alert("Vérifier le prenom (Longueur maximale 20 caractères)!");
            }
        }
        if(!t4.getText().isEmpty()){
            try{
                if(Integer.parseInt(t4.getText())<101 && Integer.parseInt(t4.getText())>17){
                    sql+=" age="+t4.getText()+",";
                }
                else {
                    test=false;
                    alert("L'âge doit être compris entre 18 et 100!");
                }
            }catch (NumberFormatException e){
                test=false;
                alert("L'âge doit être entré en chiffres!");
            }
        }
        if (!t5.getText().isEmpty()){
            if(t5.getText().equals("Homme")||t5.getText().equals("Femme")){
                sql+=" sexe='"+t5.getText()+"',";
            }
            else {
                test=false;
                alert("Le sexe doit être soit Homme soit Femme!");
            }
        }
        if (!t6.getText().isEmpty()){
            if(t6.getText().length() < 21){
                sql+=" ville='"+t6.getText()+"',";
            }
            else {
                test=false;
                alert("Vérifier la ville (Longueur maximale 20 caractères)!");
            }
        }
        if(sql.length()<17){
            alert("Remplir les champs!");
            test=false;
        }
        else {
            if(t1.getText().isEmpty()){
                alert("Taper l'identifiant du client à modifier!");
                test=false;
            }
            else{
                if(test){
                    sql=sql.substring(0,sql.length()-1)+" where idc='"+t1.getText()+"'";
                    System.out.println(sql);
                    Connection con=Base.openConnection();
                    Statement s=con.createStatement();
                    s.executeUpdate(sql);
                    con.close();
                    l.clear();
                    remplircl();
                    t1.setText("");t2.setText("");t3.setText("");t4.setText("");t5.setText("");t6.setText("");
                    conf("Le client est modifié avec succès!");
                }
            }
        }
    }

    @FXML
    void a4(ActionEvent event) throws SQLException, ClassNotFoundException {
        String sql="delete from cl where";
        boolean test=true;
        if(!t1.getText().isEmpty()){
            if(t1.getText().length() < 21){
                sql+=" idc='"+t1.getText()+"' and";
            }
            else{
                test=false;
                alert("Vérifier l'identifiant (Longueur maximale 20 caractères)!");
            }
        }
        if(!t2.getText().isEmpty()){
            if(t2.getText().length() < 21){
                sql+=" nom='"+t2.getText()+"' and";
            }
            else{
                test=false;
                alert("Vérifier le nom (Longueur maximale 20 caractères)!");
            }
        }
        if(!t3.getText().isEmpty()){
            if(t3.getText().length() < 21){
                sql+=" prenom='"+t3.getText()+"' and";
            }
            else{
                test=false;
                alert("Vérifier le prenom (Longueur maximale 20 caractères)!");
            }
        }
        if(!t4.getText().isEmpty()){
            try{
                if(Integer.parseInt(t4.getText())<101 && Integer.parseInt(t4.getText())>17){
                    sql+=" age="+t4.getText()+" and";
                }
                else {
                    test=false;
                    alert("L'âge doit être compris entre 18 et 100!");
                }
            }catch (NumberFormatException e){
                test=false;
                alert("L'âge doit être entré en chiffres!");
            }
        }
        if (!t5.getText().isEmpty()){
            if(t5.getText().equals("Homme")||t5.getText().equals("Femme")){
                sql+=" sexe='"+t5.getText()+"' and";
            }
            else {
                test=false;
                alert("Le sexe doit être soit Homme soit Femme!");
            }
        }
        if (!t6.getText().isEmpty()){
            if(t6.getText().length() < 21){
                sql+=" ville='"+t6.getText()+"' and";
            }
            else {
                test=false;
                alert("Vérifier la ville (Longueur maximale 20 caractères)!");
            }
        }
        if(sql.length()<22){
            alert("Remplir les champs!");
            test=false;
        }
        if(test){
            sql=sql.substring(0,sql.length()-4);
            System.out.println(sql);
            Connection con=Base.openConnection();
            Statement s=con.createStatement();
            s.executeUpdate(sql);
            con.close();
            l.clear();
            remplircl();
            t1.setText("");t2.setText("");t3.setText("");t4.setText("");t5.setText("");t6.setText("");
            conf("Les clients sont supprimés avec succès!");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            remplircl();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
