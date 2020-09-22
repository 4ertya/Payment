package by.epamtc.payment.dao.impl;

import by.epamtc.payment.dao.DAOException;
import by.epamtc.payment.dao.connection.ConnectionPool;
import by.epamtc.payment.dao.UserDAO;
import by.epamtc.payment.entity.Role;
import by.epamtc.payment.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLUserDAO implements UserDAO {

    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final static String INSERT_USERS_SQL = "INSERT INTO users (login,password,email,roles_id) VALUES(?,?,?,?);";
    private final static String SELECT_USER_BY_LOGIN_PASSWORD = "SELECT * FROM users WHERE login=? AND password=?;";
    private final static String SELECT_USER_BY_ID = "select login,password,email from users where id =?;";
    private final static String SELECT_ALL_USERS_SQL = "SELECT * FROM users;";
    private final static String DELETE_USER_BY_ID = "DELETE FROM users WHERE id=?;";
    private final static String UPDATE_USER_SQL = "UPDATE users SET login =?,password=?, email =? where id =?;";


    @Override
    public void registration(User user) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(INSERT_USERS_SQL);

            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setLong(4, user.getRole().ordinal() + 1);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Exception in SQLUserDAO: registration(User user)", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public boolean isUserExist(String login) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean exist;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_USER_BY_LOGIN_PASSWORD);

            preparedStatement.setString(1, login);

            ResultSet resultSet = preparedStatement.executeQuery();
            exist = resultSet != null;

        } catch (SQLException e) {
            throw new DAOException("Exception in SQLUserDAO: isUserExist(String login, String password)", e);
        }
        return exist;
    }

    @Override
    public User login(String login, String password) throws DAOException {
        Connection connection;
        PreparedStatement preparedStatement;
        User user = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_USER_BY_LOGIN_PASSWORD);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.setLogin(resultSet.getString("login"));
                user.setId(Integer.parseInt(resultSet.getString("id")));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return user;
    }
}
