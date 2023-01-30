package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String createUsersTableSql = "CREATE TABLE IF NOT EXISTS USERS ("
                + "ID INT NOT NULL AUTO_INCREMENT, "
                + "NAME VARCHAR(45) NOT NULL,"
                + "LASTNAME VARCHAR(45) NOT NULL, "
                + "AGE INT(3) NOT NULL, "
                + "PRIMARY KEY (ID))";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute(createUsersTableSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String dropUsersTableSql = "DROP TABLE IF EXISTS USERS";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute(dropUsersTableSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String saveUserSql = "INSERT INTO USERS (NAME, LASTNAME, AGE) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(saveUserSql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String removeUserByIdSql = "DELETE FROM USERS WHERE ID = " + id;
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute(removeUserByIdSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String getAllUsersSql = "SELECT * FROM USERS";
        try (Statement statement = Util.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(getAllUsersSql)) {
            while (resultSet.next()) {
                userList.add(new User(resultSet.getString("NAME"),
                        resultSet.getString("LASTNAME"),
                        resultSet.getByte("AGE")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public void cleanUsersTable() {
        String cleanUsersTableSql = "TRUNCATE USERS";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute(cleanUsersTableSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
