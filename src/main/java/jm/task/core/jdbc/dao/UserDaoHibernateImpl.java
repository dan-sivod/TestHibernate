package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private Util util = new Util();

    public UserDaoHibernateImpl() {}

    @Override
    public void createUsersTable() {

        Transaction transaction = null;
        Session session = null;
        String query = "CREATE TABLE IF NOT EXISTS users(" +
                "id integer primary key auto_increment, " +
                "name varchar(100), " +
                "lastName varchar(100), " +
                "age int);";
        try {
            session = util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.createSQLQuery(query);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        Session session = null;
        String query = "DROP TABLE IF EXISTS users;";
        try {
            session = util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.createSQLQuery(query);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void removeUserById(long id) {

        Transaction transaction = null;
        Session session = null;
        try {
            session = util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            User user = (User) session.load(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Transaction transaction = null;
        Session session = null;
        try {
            session = util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            users = session.createQuery("from User").getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
            return users;
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        Session session = null;
        String query = "TRUNCATE TABLE users;";
        try {
            session = util.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.createSQLQuery(query);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
