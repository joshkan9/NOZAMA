package org.example.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class to connect to the Nozama database.
 */
public class NozamaDatabase {
    public Connection connect() {
        String url = "jdbc:mysql://localhost:3306/tcss_445_ecommerce_project";
        String user = "root";
        String password = "password";
        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();

        } catch (SQLException e2) {
            e2.printStackTrace();
        }

        return con;
    }
}