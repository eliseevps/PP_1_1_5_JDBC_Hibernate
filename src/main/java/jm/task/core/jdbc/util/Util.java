package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.sql.Connection;
import java.sql.DriverManager;

public class Util {
    private final static String URL = "jdbc:mysql://localhost:3306/pp";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "Kataroot";
    private static Connection connection;
    private static SessionFactory sessionFactory;

    private Util() {

    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                connection.setAutoCommit(false);
            } catch (Exception e) {
                System.out.println("No connection. Exception!");
            }
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                sessionFactory = new Configuration()
                        .configure("hibernate.cfg.xml")
                        .addAnnotatedClass(User.class)
                        .buildSessionFactory();
            } catch (Exception e) {
                System.out.println("No connection. Exception!");
            }
        }
        return sessionFactory;
    }
}
