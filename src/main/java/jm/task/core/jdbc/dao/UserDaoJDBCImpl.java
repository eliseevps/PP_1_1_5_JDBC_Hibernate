package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final String createUsersTableSql = "CREATE TABLE IF NOT EXISTS USERS ("
            + "ID INT NOT NULL AUTO_INCREMENT, "
            + "NAME VARCHAR(45) NOT NULL,"
            + "LASTNAME VARCHAR(45) NOT NULL, "
            + "AGE INT(3) NOT NULL, "
            + "PRIMARY KEY (ID))";
    private final String dropUsersTableSql = "DROP TABLE IF EXISTS USERS";
    private final String saveUserSql = "INSERT INTO USERS (NAME, LASTNAME, AGE) VALUES (?, ?, ?)";
    private final String removeUserByIdSql = "DELETE FROM USERS WHERE ID = ?";
    private final String getAllUsersSql = "SELECT * FROM USERS";
    private final String cleanUsersTableSql = "TRUNCATE USERS";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Connection connection = Util.getConnection();
        try (connection; Statement statement = connection.createStatement()) {
            statement.execute(createUsersTableSql);
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        Connection connection = Util.getConnection();
        try (connection; Statement statement = connection.createStatement()) {
            statement.execute(dropUsersTableSql);
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection = Util.getConnection();
        try (connection; PreparedStatement preparedStatement = connection.prepareStatement(saveUserSql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        Connection connection = Util.getConnection();
        try (connection; PreparedStatement preparedStatement = connection.prepareStatement(removeUserByIdSql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        Connection connection = Util.getConnection();
        List<User> userList = new ArrayList<>();
        try (connection; Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(getAllUsersSql)) {
            while (resultSet.next()) {
                userList.add(new User(resultSet.getString("NAME"),
                        resultSet.getString("LASTNAME"),
                        resultSet.getByte("AGE")));
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
        return userList;
    }

    public void cleanUsersTable() {
        Connection connection = Util.getConnection();
        try (connection; Statement statement = connection.createStatement()) {
            statement.execute(cleanUsersTableSql);
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
