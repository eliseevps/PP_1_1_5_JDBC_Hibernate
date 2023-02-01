package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private final static String URL = "jdbc:mysql://localhost:3306/pp";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "Kataroot";
    private static Connection connection;

    private Util() {

    }

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            connection.setAutoCommit(false);
        } catch (
                SQLException e) {
            System.out.println("No connection. Exception!");
        }
        return connection;
    }
}
