package org.example;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class NozamaDatabase {

    public NozamaDatabase() {
        // Empty constructor
    }

    public void connect() {
        String url = "jdbc:mysql://localhost:3306/tcss_445_ecommerce_project";
        String user = "root";
        String password = "PUT YOUR PASSWORD HERE!!!!!!!!";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("Connected");

        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();

        } catch (SQLException e2) {
            e2.printStackTrace();
        }
    }
}