package by.epamtc.payment.dao.impl;

import by.epamtc.payment.dao.exception.DAOException;
import by.epamtc.payment.dao.exception.DAOUserExistException;
import by.epamtc.payment.dao.exception.DAOUserNotFoundException;
import by.epamtc.payment.dao.connection.ConnectionPool;
import by.epamtc.payment.dao.UserDAO;
import by.epamtc.payment.entity.Role;
import by.epamtc.payment.entity.User;
import org.w3c.dom.ls.LSOutput;

import java.sql.*;

public class SQLUserDAO implements UserDAO {

    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final static String INSERT_USERS_SQL = "INSERT INTO users (login,password,email,roles_id) VALUES(?,?,?,2);";
    private final static String SELECT_USER_BY_LOGIN_PASSWORD = "SELECT u.id, u.login, u.email, rol.role FROM users u, roles rol WHERE login=? AND password=? AND u.roles_id=rol.id;";
    private final static String SELECT_USER_BY_LOGIN = "SELECT login FROM users WHERE login=?;";
    private final static String SELECT_USER_BY_ID = "select login,password,email from users where id =?;";
    private final static String SELECT_ALL_USERS_SQL = "SELECT * FROM users;";
    private final static String DELETE_USER_BY_ID = "DELETE FROM users WHERE id=?;";
    private final static String UPDATE_USER_SQL = "UPDATE users SET login =?,password=?, email =? where id =?;";

    @Override
    public User login(String login, String password) throws DAOException, DAOUserNotFoundException {
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
                user.setId(Integer.parseInt(resultSet.getString("id")));
                user.setLogin(resultSet.getString("login"));
                user.setEmail(resultSet.getString("email"));
                user.setRole(Role.valueOf(resultSet.getString("role")));
            } else {
                throw new DAOUserNotFoundException();
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return user;
    }

    @Override
    public void registration(User user) throws DAOException, DAOUserExistException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {

            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(INSERT_USERS_SQL);

            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException ex) {
            throw new DAOUserExistException(ex);
        } catch (SQLException e) {
            throw new DAOException("Exception in SQLUserDAO: registration(User user)", e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
    }
}

