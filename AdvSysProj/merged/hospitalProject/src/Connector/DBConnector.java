/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connector;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author spyunes
 */
public class DBConnector {

    private Statement stmt;

    public DBConnector() {
        initializeDB();
    }

    private void initializeDB() {
        try {
            // Load the JDBC driver
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            System.out.println("Driver loaded");

            // Establish a connection
            Connection connection = DriverManager.getConnection("jdbc:ucanaccess:///Users/aasim/FinalDB.accdb");
            System.out.println("Database connected");

            // Create a statement
            stmt = connection.createStatement();
        } catch (ClassNotFoundException | SQLException ex) {
        }
    }

    public Statement getStmt() {
        return stmt;
    }
}
