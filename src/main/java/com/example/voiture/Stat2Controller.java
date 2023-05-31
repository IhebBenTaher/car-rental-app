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

public class Stat2Controller implements Initializable {

    @FXML
    private PieChart c1;

    @FXML
    private PieChart c2;

    @FXML
    private PieChart c3;

    @FXML
    private PieChart c4;
    private void rech(String a,PieChart b) throws SQLException, ClassNotFoundException {
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            rech("select immat,count(idl) from loc, voiture where idm=immat group by immat",c1);
            rech("select immat,sum(total) from loc, voiture where idm=immat group by immat",c3);
            rech("select color,count(idl) from loc, voiture where idm=immat group by color",c2);
            rech("select color,sum(total) from loc, voiture where idm=immat group by color",c4);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
