package com.example.voiture;
import java.sql.*;

public class Base {
     public static Connection openConnection() throws ClassNotFoundException, SQLException {
          Class.forName("oracle.jdbc.driver.OracleDriver");
          return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","sys as sysdba","a");
     }
     public static boolean chercheAdmin(String a,String b) throws ClassNotFoundException, SQLException {
          Connection con=openConnection();
          Statement s=con.createStatement();
          String sql="Select * from admin where username='"+a+"' and password='"+b+"'";
          ResultSet rs=s.executeQuery(sql);
          boolean test=rs.next();
          con.close();
          return test;
     }
     public static void insertAdmin(String a,String b) throws ClassNotFoundException, SQLException {
          Connection con=openConnection();
          Statement s=con.createStatement();
          String sql="insert into admin values('"+a+"','"+b+"')";
          s.executeUpdate(sql);
          con.close();
     }
}
