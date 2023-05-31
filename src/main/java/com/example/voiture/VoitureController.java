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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class VoitureController implements Initializable {
    @FXML
    private TableView<Voiture> tab;
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
    private TableColumn<Voiture, String> cl1;
    @FXML
    private TableColumn<Voiture, String> cl2;
    @FXML
    private TableColumn<Voiture, String> cl3;
    @FXML
    private TableColumn<Voiture, Integer> cl4;
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
    private ObservableList<Voiture>l= FXCollections.observableArrayList();
    public void remplircl() throws SQLException, ClassNotFoundException {
        Connection con=Base.openConnection();
        Statement s=con.createStatement();
        ResultSet rs=s.executeQuery("select * from voiture");
        ObservableSet<String> l2=FXCollections.observableSet();
        ObservableSet<String> l3=FXCollections.observableSet();
        ObservableSet<Integer> l4=FXCollections.observableSet();
        ObservableList<String>l22=FXCollections.observableArrayList();
        ObservableList<String>l1=FXCollections.observableArrayList();
        ObservableList<String>l33=FXCollections.observableArrayList();
        ObservableList<Integer>l44=FXCollections.observableArrayList();
        l1.add("Immatricule");l2.add("Modèle");l3.add("Couleur");l4.add(0);
        while(rs.next()){
            l.add(new Voiture(rs.getString(1),rs.getString(2),rs.getString(3),rs.getInt(4)));
            l1.add(rs.getString(1));l2.add(rs.getString(2));l3.add(rs.getString(3));l4.add(rs.getInt(4));
        }
        con.close();
        cl1.setCellValueFactory(new PropertyValueFactory<Voiture,String>("im"));
        cl2.setCellValueFactory(new PropertyValueFactory<Voiture,String>("modele"));
        cl3.setCellValueFactory(new PropertyValueFactory<Voiture,String>("color"));
        cl4.setCellValueFactory(new PropertyValueFactory<Voiture,Integer>("prix"));
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
        c1.setItems(l1);c2.setItems(l22);c3.setItems(l33);c4.setItems(l44);
    }
    @FXML
    void a1(ActionEvent event) throws SQLException, ClassNotFoundException {
        boolean ajout=false;
        Connection con=Base.openConnection();
        l.clear();
        String sql="select * from voiture";
        if(c1.getSelectionModel().getSelectedItem()!=null && !"Immatricule".equals(c1.getSelectionModel().getSelectedItem())){
            sql+=" where immat='"+c1.getSelectionModel().getSelectedItem()+"'";
            ajout=true;
        }
        if(c2.getSelectionModel().getSelectedItem()!=null && !"Modèle".equals(c2.getSelectionModel().getSelectedItem())){
            if (!ajout) {
                sql+=" where modele='"+c2.getSelectionModel().getSelectedItem()+"'";
                ajout=true;
            }
            else
                sql+=" and modele='"+c2.getSelectionModel().getSelectedItem()+"'";
        }
        if(c3.getSelectionModel().getSelectedItem()!=null && !"Couleur".equals(c3.getSelectionModel().getSelectedItem())){
            if (!ajout) {
                sql+=" where color='"+c3.getSelectionModel().getSelectedItem()+"'";
                ajout=true;
            }
            else
                sql+=" and color='"+c3.getSelectionModel().getSelectedItem()+"'";
        }
        if(c4.getSelectionModel().getSelectedItem()!=null && !(c4.getSelectionModel().getSelectedItem()==0)){
            if (!ajout) {
                sql+=" where prix="+c4.getSelectionModel().getSelectedItem();
                ajout=true;
            }
            else
                sql+=" and prix="+c4.getSelectionModel().getSelectedItem();
        }
        System.out.println(sql);
        Statement s=con.createStatement();
        ResultSet rs=s.executeQuery(sql);
        byte []b=null;
        while (rs.next()){
            l.add(new Voiture(rs.getString(1),rs.getString(2),rs.getString(3),rs.getInt(4)));b=rs.getBlob(5).getBytes(1,(int)rs.getBlob(5).length());
        }
        cl1.setCellValueFactory(new PropertyValueFactory<Voiture,String>("im"));
        cl2.setCellValueFactory(new PropertyValueFactory<Voiture,String>("modele"));
        cl3.setCellValueFactory(new PropertyValueFactory<Voiture,String>("color"));
        cl4.setCellValueFactory(new PropertyValueFactory<Voiture,Integer>("prix"));
        tab.setItems(l);
        if(l.size()==1){
            t1.setText(l.get(0).getIm());t2.setText(l.get(0).getModele());t3.setText(l.get(0).getColor());t4.setText(l.get(0).getPrix()+"");t5.setImage(new Image(new ByteArrayInputStream(b),t5.getFitHeight(),t5.getFitWidth(),true,true));
        }else{
            t1.setText("");t2.setText("");t3.setText("");t4.setText("");t5.setImage(new Image("C:\\Users\\iheb\\IdeaProjects\\voiture\\src\\main\\resources\\images\\first.png",t5.getFitWidth(),t5.getFitHeight(),true,true));
        }
        c1.getSelectionModel().select("Immatricule");c2.getSelectionModel().select("Modèle");c3.getSelectionModel().select("Couleur");c4.getSelectionModel().select(0);
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
            alert("Vérifier le modèle (Longueur maximale 20 caractères)!");
        } else {
            if (t3.getText().isEmpty() || t3.getText().length() > 20) {
                alert("Vérifier la couleur (Longueur maximale 20 caractères)!");
            } else {
                try{
                    if (t4.getText().isEmpty() || Integer.parseInt(t4.getText()) < 10) {
                        alert("Vérifier le prix (prix minimal 10 dinars)!");
                    } else {
                        if (t5.getImage().getUrl()==null||t5.getImage().getUrl().toString().equals("C:\\Users\\iheb\\IdeaProjects\\voiture\\src\\main\\resources\\images\\first.png")) {
                            alert("Importer un photo png ou jpg!");
                        }
                        else {
                            if (t1.getText().isEmpty() || t1.getText().length() > 20) {
                                alert("Vérifier l'identifiant (Longueur maximale 20 caractères)!");
                            }
                            else{
                                Connection con = Base.openConnection();
                                String sql = "insert into voiture values(?,?,?,?,?)";
                                PreparedStatement st = con.prepareStatement(sql);
                                st.setString(1,t1.getText());
                                st.setString(2,t2.getText());
                                st.setString(3,t3.getText());
                                st.setInt(4,Integer.parseInt(t4.getText()));
                                FileInputStream fis=new FileInputStream(new File(t5.getImage().getUrl().toString()));
                                st.setBlob(5,fis);
                                try {
                                    st.executeUpdate();
                                    l.clear();
                                    remplircl();
                                    con.close();
                                    t1.setText("");t2.setText("");t3.setText("");t4.setText("");t5.setImage(new Image("C:\\Users\\iheb\\IdeaProjects\\voiture\\src\\main\\resources\\images\\first.png",t5.getFitWidth(),t5.getFitHeight(),true,true));
                                    conf("La voiture est insérée avec succès!");
                                } catch (SQLException e) {
                                    alert("Identifiant déjà utilisé!");
                                }
                            }
                        }
                    }}catch (NumberFormatException | FileNotFoundException e){
                    alert("Le prix doit être entré en chiffres!");
                }
            }
        }
    }
    @FXML
    void imp(MouseEvent event){
        FileChooser fc=new FileChooser();
        fc.setInitialDirectory(new File("/"));
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG","*.jpg"),new FileChooser.ExtensionFilter("PNG","*.png"));
        File f=fc.showOpenDialog(null);
        if(f!=null){
            t5.setImage(new Image(f.getAbsolutePath(),t5.getFitWidth(),t5.getFitHeight(),true,true));
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
    void a3(ActionEvent event) throws SQLException, ClassNotFoundException, FileNotFoundException {
        String sql="update voiture set";
        boolean test=true,test1=false;
        if(!t2.getText().isEmpty()){
            if(t2.getText().length() < 21){
                sql+=" modele='"+t2.getText()+"',";
                test1=true;
            }
            else{
                test=false;
                alert("Vérifier le modèle (Longueur maximale 20 caractères)!");
            }
        }
        if(!t3.getText().isEmpty()){
            if(t3.getText().length() < 21){
                sql+=" color='"+t3.getText()+"',";
                test1=true;
            }
            else{
                test=false;
                alert("Vérifier la couleur (Longueur maximale 20 caractères)!");
            }
        }
        if(!t4.getText().isEmpty()){
            try{
                if(Integer.parseInt(t4.getText())>9){
                    sql+=" prix="+t4.getText()+",";
                    test1=true;
                }
                else {
                    test=false;
                    alert("Le prix doit être supérieur à 10 dinars!");
                }
            }catch (NumberFormatException e){
                test=false;
                alert("Le prix doit être entré en chiffres!");
            }
        }
        boolean test2=false;
        Connection con2=Base.openConnection();
        PreparedStatement ps=con2.prepareStatement("update voiture set image=? where immat=?");
        FileInputStream fis;
        if (!(t5.getImage().getUrl()==null)&&!t5.getImage().getUrl().toString().equals("file:/C:/Users/iheb/IdeaProjects/voiture/target/classes/images/first.png")){
            ps.setBlob(1,new FileInputStream(new File(t5.getImage().getUrl().toString())));
            test2=true;
        }
        if(sql.length()<22&&!test2){
            alert("Remplir les champs!");
            test=false;
        }
        else {
            if(t1.getText().isEmpty()|| t1.getText().length() > 20){
                alert("Vérifier l'identifiant (Longueur maximale 20 caractères)!");
                test=false;
            }
            else{
                if(test1){
                    sql=sql.substring(0,sql.length()-1)+" where immat='"+t1.getText()+"'";
                    System.out.println(sql);
                    Connection con=Base.openConnection();
                    Statement s=con.createStatement();
                    s.executeUpdate(sql);
                    con.close();
                    l.clear();
                    remplircl();
                }
                if(test2){
                    ps.setString(2,t1.getText());
                    ps.executeUpdate();
                    con2.close();
                }
                t1.setText("");t2.setText("");t3.setText("");t4.setText("");t5.setImage(new Image("C:\\Users\\iheb\\IdeaProjects\\voiture\\src\\main\\resources\\images\\first.png",t5.getFitWidth(),t5.getFitHeight(),true,true));
                conf("Le client est modifié avec succès!");
            }
        }
    }

    @FXML
    void a4(ActionEvent event) throws SQLException, ClassNotFoundException {
        String sql="delete from voiture where";
        boolean test=true;
        if(!t1.getText().isEmpty()){
            if(t1.getText().length() < 21){
                sql+=" immat='"+t1.getText()+"' and";
            }
            else{
                test=false;
                alert("Vérifier l'immatricule (Longueur maximale 20 caractères)!");
            }
        }
        if(!t2.getText().isEmpty()){
            if(t2.getText().length() < 21){
                sql+=" modele='"+t2.getText()+"' and";
            }
            else{
                test=false;
                alert("Vérifier le modèle (Longueur maximale 20 caractères)!");
            }
        }
        if(!t3.getText().isEmpty()){
            if(t3.getText().length() < 21){
                sql+=" color='"+t3.getText()+"' and";
            }
            else{
                test=false;
                alert("Vérifier la couleur (Longueur maximale 20 caractères)!");
            }
        }
        if(sql.length()<28){
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
            t1.setText("");t2.setText("");t3.setText("");t4.setText("");t5.setImage(new Image("C:\\Users\\iheb\\IdeaProjects\\voiture\\src\\main\\resources\\images\\first.png",t5.getFitWidth(),t5.getFitHeight(),true,true));
            conf("Les voitures sont supprimés avec succès!");
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
