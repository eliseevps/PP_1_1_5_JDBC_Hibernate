package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        String createUsersTableSql = "CREATE TABLE IF NOT EXISTS USERS ("
                + "ID INT NOT NULL AUTO_INCREMENT, "
                + "NAME VARCHAR(45) NOT NULL,"
                + "LASTNAME VARCHAR(45) NOT NULL, "
                + "AGE INT(3) NOT NULL, "
                + "PRIMARY KEY (ID))";
        Session session = Util.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.createSQLQuery(createUsersTableSql).executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void dropUsersTable() {
        String dropUsersTableSql = "DROP TABLE IF EXISTS USERS";
        Session session = Util.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.createSQLQuery(dropUsersTableSql).executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().getCurrentSession();
        try (session) {
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().getCurrentSession();
        try (session) {
            session.beginTransaction();
            session.createQuery("delete User where id = id");
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSessionFactory().getCurrentSession();
        List<User> users = null;
        try (session) {
            session.beginTransaction();
            users = session.createQuery("from User").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        String cleanUsersTableSql = "TRUNCATE USERS";
        Session session = Util.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.createSQLQuery(cleanUsersTableSql).executeUpdate();
        session.getTransaction().commit();
    }
}
