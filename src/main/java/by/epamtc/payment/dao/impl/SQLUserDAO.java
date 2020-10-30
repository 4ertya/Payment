package by.epamtc.payment.dao.impl;

import by.epamtc.payment.dao.UserDAO;
import by.epamtc.payment.dao.connection.ConnectionPool;
import by.epamtc.payment.dao.exception.DAOException;
import by.epamtc.payment.dao.exception.DAOUserExistException;
import by.epamtc.payment.dao.exception.DAOUserNotFoundException;
import by.epamtc.payment.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLUserDAO implements UserDAO {

    private final static Logger log = LogManager.getLogger(SQLUserDAO.class);
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    //language=MySQL
    private final static String INSERT_USER = "INSERT INTO users " +
            "SET login=?, password=?, email=?, role_id=(SELECT role_id FROM roles WHERE role=?);";
    //language=MySQL
    private final static String SELECT_USER_BY_LOGIN_PASSWORD = "SELECT u.user_id, rol.role, ud.en_name, ud.en_surname " +
            "FROM users u " +
            "JOIN roles rol USING (role_id) " +
            "LEFT JOIN user_details ud USING (user_id) " +
            "WHERE login=? " +
            "AND password=?;";
    //language=MySQL
    private final static String SELECT_USER_DETAIL_BY_ID = "SELECT * FROM user_details WHERE user_id=?;";
    //language=MySQL
    private final static String SELECT_USER_DATA_BY_ID = "SELECT u.user_id, u.login, u.email, rol.role " +
            "FROM users u " +
            "JOIN roles rol USING (role_id) " +
            "WHERE u.user_id=?;";
    //language=MySQL
    private final static String SELECT_ALL_USERS = "SELECT u.user_id, u.login, u.email, r.role " +
            "FROM users u " +
            "JOIN roles r USING (role_id);";
    //language=MySQL
    private final static String INSERT_ID_IN_USER_DETAILS = "INSERT INTO user_details SET user_id=?;";
    //language=MySQL
    private final static String UPDATE_USER_DETAILS_BY_ID = "UPDATE user_details SET ru_name=?, ru_surname=?, " +
            "en_name=?, en_surname=?, gender=?, passport_series=?, passport_number=?, phone_number=?, location=? " +
            "WHERE user_id=?;";

    @Override
    public User login(AuthorizationData authorizationData) throws DAOUserNotFoundException, DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user;

        String login = authorizationData.getLogin();
        String password = authorizationData.getPassword();

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_USER_BY_LOGIN_PASSWORD);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new DAOUserNotFoundException();
            }

            long id = Long.parseLong(resultSet.getString(SQLParameter.USER_ID));
            String name = resultSet.getString(SQLParameter.EN_NAME);
            String surname = resultSet.getString(SQLParameter.EN_SURNAME);
            Role role = Role.valueOf(resultSet.getString(SQLParameter.ROLE));

            user = new User();

            user.setId(id);
            user.setName(name);
            user.setSurname(surname);
            user.setRole(role);

        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
        return user;
    }

    @Override
    public void registration(RegistrationData registrationData) throws DAOUserExistException, DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String login = registrationData.getLogin();
        String password = registrationData.getPassword();
        String email = registrationData.getEmail();
        String role = registrationData.getRole().name();

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement((INSERT_USER), Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, role);
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                long id = resultSet.getLong(1);

                preparedStatement = connection.prepareStatement(INSERT_ID_IN_USER_DETAILS);
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            }


        } catch (SQLIntegrityConstraintViolationException ex) {
            throw new DAOUserExistException();
        } catch (SQLException e) {
            throw new DAOException("Exception in SQLUserDAO: registration(RegistrationData registrationData)", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public UserData getUserData(Long id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        UserData userData = new UserData();

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_USER_DATA_BY_ID);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return userData;
            }

            String login = resultSet.getString(SQLParameter.LOGIN);
            String email = resultSet.getString(SQLParameter.EMAIL);
            Role role = Role.valueOf(resultSet.getString(SQLParameter.ROLE));

            userData.setId(id);
            userData.setLogin(login);
            userData.setEmail(email);
            userData.setRole(role);

        } catch (SQLException e) {
            throw new DAOException("Exception in SQLUserDAO: getUser()", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
        return userData;
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

            if (!resultSet.next()) {
                return userDetail;
            }

            String ruName = resultSet.getString(SQLParameter.RU_NAME);
            String ruSurname = resultSet.getString(SQLParameter.RU_SURNAME);
            String enName = resultSet.getString(SQLParameter.EN_SURNAME);
            String enSurname = resultSet.getString(SQLParameter.EN_SURNAME);
            String gender = resultSet.getString(SQLParameter.GENDER);
            String passportSeries = resultSet.getString(SQLParameter.PASSPORT_SERIES);
            Integer passportNumber = resultSet.getInt(SQLParameter.PASSPORT_NUMBER);
            String phoneNumber = resultSet.getString(SQLParameter.PHONE_NUMBER);
            String location = resultSet.getString(SQLParameter.LOCATION);

            userDetail.setId(id);
            userDetail.setRuName(ruName);
            userDetail.setRuSurname(ruSurname);
            userDetail.setEnName(enName);
            userDetail.setEnSurname(enSurname);
            userDetail.setGender(gender);
            userDetail.setPassportSeries(passportSeries);
            userDetail.setPassportNumber(passportNumber);
            userDetail.setPhoneNumber(phoneNumber);
            userDetail.setLocation(location);

        } catch (SQLException e) {
            throw new DAOException("Exception in SQLUserDAO: getUserDetail()", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
        return userDetail;
    }

    @Override
    public List<UserData> getAllUsers() throws DAOException {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        List<UserData> users = new ArrayList<>();
        UserData userData;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong(SQLParameter.USER_ID);
                String login = resultSet.getString(SQLParameter.LOGIN);
                String email = resultSet.getString(SQLParameter.EMAIL);
                Role role = Role.valueOf(resultSet.getString(SQLParameter.ROLE));

                userData = new UserData();

                userData.setId(id);
                userData.setLogin(login);
                userData.setEmail(email);
                userData.setRole(role);

                users.add(userData);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return users;
    }

    @Override
    public void updateUserDetails(UserDetail userDetail) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(UPDATE_USER_DETAILS_BY_ID);

            preparedStatement.setString(1, userDetail.getRuName());
            preparedStatement.setString(2, userDetail.getRuSurname());
            preparedStatement.setString(3, userDetail.getEnName());
            preparedStatement.setString(4, userDetail.getEnSurname());
            preparedStatement.setString(5, userDetail.getGender());
            preparedStatement.setString(6, userDetail.getPassportSeries());
            preparedStatement.setInt(7, userDetail.getPassportNumber());
            preparedStatement.setString(8, userDetail.getPhoneNumber());
            preparedStatement.setString(9, userDetail.getLocation());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    private static class SQLParameter {
        private final static String USER_ID = "user_id";
        private final static String LOGIN = "login";
        private final static String EMAIL = "email";
        private final static String ROLE = "role";
        private final static String PASSWORD = "password";
        private final static String RU_NAME = "ru_name";
        private final static String RU_SURNAME = "ru_surname";
        private final static String EN_NAME = "en_name";
        private final static String EN_SURNAME = "en_surname";
        private final static String GENDER = "gender";
        private final static String PASSPORT_SERIES = "passport_series";
        private final static String PASSPORT_NUMBER = "passport_number";
        private final static String PHONE_NUMBER = "phone_number";
        private final static String LOCATION = "location";
    }
}

