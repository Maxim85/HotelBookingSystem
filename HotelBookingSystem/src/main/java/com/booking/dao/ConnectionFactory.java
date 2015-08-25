package com.booking.dao;

import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Maksym.
 */
public class ConnectionFactory {
    private static final String URL = "jdbc:mysql://localhost:3306/hotelbookingdb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static Connection createConnection() {
        Connection connection = null;
        try {
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
