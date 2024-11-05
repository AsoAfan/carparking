package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {

    static final String DB_URL = "jdbc:mysql://localhost:3306/a";
    static final String USER = "root";
    static final String PASS = "banaz12";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stm = conn.createStatement();) {
            String sql = "SELECT * From activities";
            stm.executeQuery(sql);
            System.out.println("Database created successfully...");

        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
