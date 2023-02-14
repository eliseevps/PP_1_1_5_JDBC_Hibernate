package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final String CREATE_USERS_TABLE_SQL = "CREATE TABLE IF NOT EXISTS USERS ("
            + "ID INT NOT NULL AUTO_INCREMENT, "
            + "NAME VARCHAR(45) NOT NULL,"
            + "LASTNAME VARCHAR(45) NOT NULL, "
            + "AGE INT(3) NOT NULL, "
            + "PRIMARY KEY (ID))";
    private static final String DROP_USERS_TABLE_SQL = "DROP TABLE IF EXISTS USERS";
    private static final String CLEAN_USERS_TABLE_SQL = "TRUNCATE USERS";
    private static final SessionFactory SESSION_FACTORY = Util.getSessionFactory();
    private static Session session = null;

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();
        session.createSQLQuery(CREATE_USERS_TABLE_SQL).executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void dropUsersTable() {
        session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();
        session.createSQLQuery(DROP_USERS_TABLE_SQL).executeUpdate();
        session.getTransaction().commit();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            session = SESSION_FACTORY.getCurrentSession();
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
        try {
            session = SESSION_FACTORY.getCurrentSession();
            session.beginTransaction();
            session.createQuery("delete User where id = id");
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        try {
            session = SESSION_FACTORY.getCurrentSession();
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
        session = SESSION_FACTORY.getCurrentSession();
        session.beginTransaction();
        session.createSQLQuery(CLEAN_USERS_TABLE_SQL).executeUpdate();
        session.getTransaction().commit();
    }
}