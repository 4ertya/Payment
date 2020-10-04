package by.epamtc.payment.dao.impl;

import by.epamtc.payment.dao.UserDAO;
import by.epamtc.payment.dao.connection.ConnectionPool;
import by.epamtc.payment.dao.exception.DAOException;
import by.epamtc.payment.dao.exception.DAOUserExistException;
import by.epamtc.payment.dao.exception.DAOUserNotFoundException;
import by.epamtc.payment.entity.Role;
import by.epamtc.payment.entity.User;
import by.epamtc.payment.entity.UserDetail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class SQLUserDAO implements UserDAO {

    private final static Logger log = LogManager.getLogger();
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final static String INSERT_USERS_SQL = "INSERT INTO users (login,password,email,roles_id) " +
            "VALUES(?,?,?,2);";

    private final static String SELECT_USER_BY_LOGIN_PASSWORD = "SELECT u.id, u.login,u.password, u.email, rol.role " +
            "FROM users u, roles rol " +
            "WHERE login=? " +
            "AND password=? " +
            "AND u.roles_id=rol.id;";

    private final static String SELECT_USER_DETAIL_BY_ID = "SELECT * FROM user_details WHERE users_id=?;";
    private final static String SELECT_USER_BY_ID = "SELECT * FROM users u, roles rol " +
            "WHERE u.id=? " +
            "AND u.roles_id=rol.id;";

    @Override
    public User login(String login, String password) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_USER_BY_LOGIN_PASSWORD);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.setId(Integer.parseInt(resultSet.getString("id")));
                user.setLogin(resultSet.getString("login"));
                user.setEmail(resultSet.getString("email"));
                user.setRole(Role.valueOf(resultSet.getString("role")));
            } else {
                log.info("User already exist!");
                throw new DAOUserNotFoundException();
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
        return user;
    }

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

    @Override
    public User getUser(Long id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = new User();

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user.setLogin(resultSet.getString("login"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(Role.valueOf(resultSet.getString("role")));
            }
        } catch (SQLException e) {
            throw new DAOException("Exception in SQLUserDAO: getUser()", e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
        return user;
    }

    @Override
    public UserDetail getUserDetail(Long id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        UserDetail userDetail = new UserDetail();

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_USER_DETAIL_BY_ID);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                userDetail.setName(resultSet.getString("name"));
                userDetail.setSurname(resultSet.getString("surname"));
                userDetail.setGender(resultSet.getString("gender"));
                userDetail.setPassportSeries(resultSet.getString("passport_series"));
                userDetail.setPassportNumber(resultSet.getString("passport_number"));
                userDetail.setPhoneNumber(resultSet.getString("phone_number"));
                userDetail.setLocation(resultSet.getString("location"));
            }
        } catch (SQLException e) {
            throw new DAOException("Exception in SQLUserDAO: getUserDetail()", e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
        return userDetail;
    }
}

