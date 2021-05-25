package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private Util util = new Util();

    public UserDaoHibernateImpl() {}

    @Override
    public void createUsersTable() {

        Transaction transaction = null;
        String query = "CREATE TABLE IF NOT EXISTS users(" +
                "id integer primary key auto_increment, " +
                "name varchar(100), " +
                "lastName varchar(100), " +
                "age int);";

        try (Session session = util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query oQuery = session.createSQLQuery(query);
            oQuery.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {

        Transaction transaction = null;
        String query = "DROP TABLE IF EXISTS users;";

        try (Session session = util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query oQuery = session.createSQLQuery(query);
            oQuery.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        Transaction transaction = null;

        try (Session session = util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {

        Transaction transaction = null;

        try (Session session = util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = (User) session.load(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();
        Transaction transaction = null;

        try (Session session = util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            users = session.createQuery("from User").getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            return users;
        }
    }

    @Override
    public void cleanUsersTable() {

        Transaction transaction = null;
        String query = "TRUNCATE TABLE users;";

        try (Session session = util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query oQuery = session.createSQLQuery(query);
            oQuery.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
