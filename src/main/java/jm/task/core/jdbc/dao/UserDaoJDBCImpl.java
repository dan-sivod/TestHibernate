package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Util util = new Util();

    public UserDaoJDBCImpl(){}

    public void createUsersTable() {

        Connection connection = null;
        try{
            connection = util.getConnection();
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users(" +
                    "id integer primary key auto_increment, " +
                    "name varchar(100), " +
                    "lastName varchar(100), " +
                    "age int);");
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            util.closeConnection();
        }
    }

    public void dropUsersTable() {

        Connection connection = null;
        try{
            connection = util.getConnection();
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS users;");
            connection.commit();
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            util.closeConnection();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        long id = 1;
        String strId = "SELECT * FROM users ORDER BY id DESC LIMIT 1;";
        String querySaveUser = "INSERT INTO users (id, name, lastname, age) VALUES (?,?,?,?)";

        Connection connection = null;
        try{
            connection = util.getConnection();
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            PreparedStatement prepareStatement = connection.prepareStatement(querySaveUser);
            ResultSet oResultSet = statement.executeQuery(strId);
            while(oResultSet.next()) {
                id = oResultSet.getInt("id") + 1;
            }
            prepareStatement.setLong(1, id);
            prepareStatement.setString(2, name);
            prepareStatement.setString(3, lastName);
            prepareStatement.setInt(4, age);
            prepareStatement.execute();
            connection.commit();
            oResultSet.close();
            statement.close();
            prepareStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            util.closeConnection();
        }
    }

    public void removeUserById(long id) {

        String queryRemoveUserId = "DELETE FROM users WHERE id = ?;";

        Connection connection = null;
        try{
            connection = util.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement prepareStatement = connection.prepareStatement(queryRemoveUserId);
            prepareStatement.setLong(1,id);
            prepareStatement.execute();
            connection.commit();
            prepareStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            util.closeConnection();
        }
    }

    public List<User> getAllUsers() {

        List<User> userList = new ArrayList<>();

        try(Connection connection = util.getConnection()){
            Statement statement = connection.createStatement();
            ResultSet oResultSet = statement.executeQuery("SELECT * FROM users;");
            while(oResultSet.next()) {
                User user = new User(oResultSet.getString("name"),
                        oResultSet.getString("lastName"),
                        oResultSet.getByte("age"));
                user.setId(oResultSet.getLong("id"));
                userList.add(user);
            }
            oResultSet.close();
            statement.close();
            return userList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            util.closeConnection();
        }
        return null;
    }

    public void cleanUsersTable() {

        Connection connection = null;
        try{
            connection = util.getConnection();
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.executeUpdate("TRUNCATE TABLE users;");
            connection.commit();
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            util.closeConnection();
        }
    }
}
