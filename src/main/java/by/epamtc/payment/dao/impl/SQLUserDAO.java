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
import java.util.ArrayList;
import java.util.List;

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
    private final static String SELECT_ALL_USERS = "SELECT u.id, u.login, u.email, r.role FROM users u JOIN roles r ON u.roles_id=r.id;";

    private final static String ID_PARAMETER = "id";
    private final static String LOGIN_PARAMETER = "login";
    private final static String EMAIL_PARAMETER = "email";
    private final static String ROLE_PARAMETER = "role";
    private final static String PASSWORD_PARAMETER = "password";
    private final static String NAME_PARAMETER = "name";
    private final static String SURNAME_PARAMETER = "surname";
    private final static String GENDER_PARAMETER = "gender";
    private final static String PASSPORT_SERIES_PARAMETER = "passport_series";
    private final static String PASSPORT_NUMBER_PARAMETER = "passport_number";
    private final static String PHONE_NUMBER_PARAMETER = "phone_number";
    private final static String LOCATION_PARAMETER = "location";

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

            if (!resultSet.next()) {
                log.info("User already exist!");
                throw new DAOUserNotFoundException();
            }

            user = new User();
            user.setId(Integer.parseInt(resultSet.getString(ID_PARAMETER)));
            user.setLogin(resultSet.getString(LOGIN_PARAMETER));
            user.setRole(Role.valueOf(resultSet.getString(ROLE_PARAMETER)));

        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
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
            connectionPool.closeConnection(connection, preparedStatement);
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
                user.setLogin(resultSet.getString(LOGIN_PARAMETER));
                user.setEmail(resultSet.getString(EMAIL_PARAMETER));
                user.setPassword(resultSet.getString(PASSWORD_PARAMETER));
                user.setRole(Role.valueOf(resultSet.getString(ROLE_PARAMETER)));
            }
        } catch (SQLException e) {
            throw new DAOException("Exception in SQLUserDAO: getUser()", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
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
                userDetail.setName(resultSet.getString(NAME_PARAMETER));
                userDetail.setSurname(resultSet.getString(SURNAME_PARAMETER));
                userDetail.setGender(resultSet.getString(GENDER_PARAMETER));
                userDetail.setPassportSeries(resultSet.getString(PASSPORT_SERIES_PARAMETER));
                userDetail.setPassportNumber(resultSet.getString(PASSPORT_NUMBER_PARAMETER));
                userDetail.setPhoneNumber(resultSet.getString(PHONE_NUMBER_PARAMETER));
                userDetail.setLocation(resultSet.getString(LOCATION_PARAMETER));
            }
        } catch (SQLException e) {
            throw new DAOException("Exception in SQLUserDAO: getUserDetail()", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
        return userDetail;
    }

    @Override
    public List<User> getAllUsers() throws DAOException {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        List<User> users = new ArrayList<>();
        User user;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                user = new User();

                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getString("login"));
                user.setEmail(resultSet.getString("email"));
                user.setRole(Role.valueOf(resultSet.getString("role")));

                users.add(user);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return users;
    }
}

