package com.example.voiture;

import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Stat1Controller implements Initializable {

    @FXML
    private PieChart c1;

    @FXML
    private PieChart c2;

    @FXML
    private PieChart c3;

    @FXML
    private PieChart c4;
    private void rech1(String a,PieChart b) throws SQLException, ClassNotFoundException {
        Connection con=Base.openConnection();
        Statement s= con.createStatement();
        ResultSet r=s.executeQuery(a);
        ObservableList<PieChart.Data>o= FXCollections.observableArrayList();
        while (r.next()){
            o.add(new PieChart.Data(r.getString(1),r.getInt(2)));
        }
        o.forEach(data -> data.nameProperty().bind(Bindings.concat(data.getName()," : ", data.pieValueProperty())));
        b.getData().addAll(o);
    }
    private void rech2(String a,PieChart b) throws SQLException, ClassNotFoundException {
        Connection con=Base.openConnection();
        Statement s= con.createStatement();
        ResultSet r=s.executeQuery(a);
        ObservableList<PieChart.Data>o= FXCollections.observableArrayList();
        while (r.next()){
            o.add(new PieChart.Data(r.getInt(1)+"",r.getInt(2)));
        }
        o.forEach(data -> data.nameProperty().bind(Bindings.concat(data.getName()," : ", data.pieValueProperty())));
        b.getData().addAll(o);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            rech1("select sexe,count(idl) from loc, cl where idc=idcl group by sexe",c1);
            rech1("select sexe,sum(total) from loc, cl where idc=idcl group by sexe",c3);
            rech2("select age,count(idl) from loc, cl where idc=idcl group by age",c2);
            rech2("select age,sum(total) from loc, cl where idc=idcl group by age",c4);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
