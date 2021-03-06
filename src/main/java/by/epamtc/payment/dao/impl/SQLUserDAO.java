package by.epamtc.payment.dao.impl;

import by.epamtc.payment.dao.UserDAO;
import by.epamtc.payment.dao.connection.ConnectionPool;
import by.epamtc.payment.dao.exception.DAOException;
import by.epamtc.payment.dao.exception.DAOUserExistException;
import by.epamtc.payment.dao.exception.DAOUserNotFoundException;
import by.epamtc.payment.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLUserDAO implements UserDAO {

    private final static Logger log = LogManager.getLogger(SQLUserDAO.class);
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    //language=MySQL
    private final static String INSERT_USER = "INSERT INTO users " +
            "SET login=?, password=?, email=?, " +
            "role_id=(SELECT role_id FROM roles WHERE role=?), " +
            "user_status_id=(SELECT user_status_id FROM user_statuses WHERE user_status=?);";
    //language=MySQL
    private final static String SELECT_USER_BY_LOGIN_PASSWORD = "SELECT u.user_id, rol.role, ud.en_name, ud.en_surname," +
            "s.user_status " +
            "FROM users u " +
            "Left JOIN roles rol USING (role_id) " +
            "Left JOIN user_details ud USING (user_id) " +
            "LEFT JOIN user_statuses s USING (user_status_id)" +
            "WHERE login=? " +
            "AND password=?;";
    //language=MySQL
    private final static String SELECT_USER_DETAIL_BY_ID = "SELECT ud.*, u.role_id, u.user_status_id, rol.role, us.user_status " +
            "FROM user_details ud " +
            "JOIN users u USING (user_id)" +
            "JOIN roles rol USING (role_id)" +
            "JOIN user_statuses us USING (user_status_id) WHERE user_id=?;";
    //language=MySQL
    private final static String SELECT_USER_DATA_BY_ID = "SELECT u.user_id, u.login, u.email, rol.role, us.user_status " +
            "FROM users u " +
            "JOIN roles rol USING (role_id) " +
            "JOIN user_statuses us USING (user_status_id) " +
            "WHERE u.user_id=?;";
    //language=MySQL
    private final static String SELECT_ALL_USERS = "SELECT ud.*, u.role_id, u.user_status_id, rol.role, us.user_status " +
            "FROM user_details ud " +
            "JOIN users u USING (user_id)" +
            "JOIN roles rol USING (role_id)" +
            "JOIN user_statuses us USING (user_status_id);";
    //language=MySQL
    private final static String INSERT_ID_IN_USER_DETAILS = "INSERT INTO user_details SET user_id=?;";
    //language=MySQL
    private final static String UPDATE_USER_DETAILS_BY_ID = "UPDATE user_details SET ru_name=?, ru_surname=?, " +
            "en_name=?, en_surname=?, gender=?, passport_series=?, passport_number=?, phone_number=?, location=? " +
            "WHERE user_id=?;";
    //language=MySQL
    private final static String UPDATE_USER_BY_ID = "UPDATE users  SET " +
            "user_status_id=(SELECT user_status_id FROM user_statuses WHERE user_status=?), " +
            "role_id=(SELECT role_id FROM roles WHERE role=?) " +
            "WHERE user_id=?;";
    //language=MySQL
    private final static String UPLOAD_PASSPORT_SCAN = "UPDATE user_details SET passport_scan=? WHERE user_id=?;";
    //language=MySQL
    private final static String DOWNLOAD_PASSPORT_SCAN = "SELECT passport_scan FROM user_details WHERE user_id=?;";

    /**
     * Returns {@code User} from the database.
     *
     * @param authorizationData contains data for the sql query.
     * @return {@code User} with properties from the database.
     * @throws DAOUserNotFoundException if there are no records in the database
     *                                  that match the query parameters
     * @throws DAOException             if a database access error occurs
     */
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
                log.info("User not found");
                throw new DAOUserNotFoundException();
            }

            long id = Long.parseLong(resultSet.getString(SQLParameter.USER_ID));
            String enName = resultSet.getString(SQLParameter.EN_NAME);
            String enSurname = resultSet.getString(SQLParameter.EN_SURNAME);
            Role role = Role.valueOf(resultSet.getString(SQLParameter.ROLE));
            Status status = Status.valueOf(resultSet.getString(SQLParameter.STATUS));

            if (enName == null) {
                enName = "User";
            }

            user = new User();

            user.setId(id);
            user.setName(enName);
            user.setSurname(enSurname);
            user.setRole(role);
            user.setStatus(status);

        } catch (SQLException e) {
            log.error("Couldn't login", e);
            throw new DAOException("Couldn't login", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
        return user;
    }

    /**
     * Adds a new user to the database.
     *
     * @param registrationData contains data for the sql query.
     * @throws DAOUserExistException if entry with this data already exists in the database.
     * @throws DAOException          if a database access error occurs
     */
    @Override
    public void registration(RegistrationData registrationData) throws DAOUserExistException, DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String login = registrationData.getLogin();
        String password = registrationData.getPassword();
        String email = registrationData.getEmail();
        String role = registrationData.getRole().name();
        String status = registrationData.getStatus().name();

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement((INSERT_USER), Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, role);
            preparedStatement.setString(5, status);
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                long id = resultSet.getLong(1);

                preparedStatement = connection.prepareStatement(INSERT_ID_IN_USER_DETAILS);
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            }
            connection.commit();


        } catch (SQLIntegrityConstraintViolationException ex) {
            log.info("Couldn't register", ex);
            throw new DAOUserExistException("Couldn't register", ex);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException roll) {
                log.error("Couldn't roll back connection", roll);
            }
            log.error("Couldn't register", e);
            throw new DAOException("Couldn't register", e);

        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    /**
     * Return {@code UserData} from the database.
     *
     * @param id user id in the database
     * @return {@code UserData} with properties from the database.
     * @throws DAOException if a database access error occurs.
     */
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
            Status status = Status.valueOf(resultSet.getString(SQLParameter.STATUS));

            userData.setId(id);
            userData.setLogin(login);
            userData.setEmail(email);
            userData.setRole(role);
            userData.setStatus(status);

        } catch (SQLException e) {
            log.error("Exception in SQLUserDAO: getUser()", e);
            throw new DAOException("Exception in SQLUserDAO: getUser()", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
        return userData;
    }

    /**
     * Return {@code UserDetail} from the database.
     *
     * @param id user id in the database
     * @return {@code UserDetails} with properties from the database.
     * @throws DAOException if a database access error occurs.
     */
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

            userDetail = fillInUserDetail(resultSet);

        } catch (SQLException e) {
            throw new DAOException("Exception in SQLUserDAO: getUserDetail()", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
        return userDetail;
    }

    /**
     * Return {@code List<UserDetails>} with all {@code UserDetail} from the database.
     *
     * @return {@code List<UserDetails>}
     * @throws DAOException if a database access error occurs.
     */
    @Override
    public List<UserDetail> getAllUserDetails() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<UserDetail> users = new ArrayList<>();
        UserDetail userDetail;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                userDetail = fillInUserDetail(resultSet);
                users.add(userDetail);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
        return users;
    }

    /**
     * Update entry in the database.
     *
     * @param userDetail contains data for the sql query.
     * @throws DAOException if a database access error occurs.
     */
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
            preparedStatement.setLong(10, userDetail.getId());

            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(UPDATE_USER_BY_ID);
            preparedStatement.setString(1, userDetail.getStatus().name());
            preparedStatement.setString(2, userDetail.getRole().name());
            preparedStatement.setLong(3, userDetail.getId());

            preparedStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                log.error("Couldn't roll back connection", ex);
            }
            throw new DAOException("Couldn't update user details! ", e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public void uploadPassportScan(InputStream inputStream, long userId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(UPLOAD_PASSPORT_SCAN);
            preparedStatement.setBinaryStream(1, inputStream);
            preparedStatement.setLong(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Couldn't upload file", e);
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public InputStream downloadPassportScan(Long userId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
InputStream scan = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(DOWNLOAD_PASSPORT_SCAN);
            preparedStatement.setLong(1, userId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                scan=resultSet.getBinaryStream(SQLParameter.PASSPORT_SCAN);
            }
        } catch (SQLException e) {
            log.error("Couldn't download file", e);
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement,resultSet);
        }
        return scan;
    }

    private UserDetail fillInUserDetail(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(SQLParameter.USER_ID);
        String ruName = resultSet.getString(SQLParameter.RU_NAME);
        String ruSurname = resultSet.getString(SQLParameter.RU_SURNAME);
        String enName = resultSet.getString(SQLParameter.EN_NAME);
        String enSurname = resultSet.getString(SQLParameter.EN_SURNAME);
        String gender = resultSet.getString(SQLParameter.GENDER);
        String passportSeries = resultSet.getString(SQLParameter.PASSPORT_SERIES);
        Integer passportNumber = resultSet.getInt(SQLParameter.PASSPORT_NUMBER);
        String phoneNumber = resultSet.getString(SQLParameter.PHONE_NUMBER);
        String location = resultSet.getString(SQLParameter.LOCATION);
        String role = resultSet.getString(SQLParameter.ROLE);
        String status = resultSet.getString(SQLParameter.STATUS);
        InputStream passportScan = resultSet.getBinaryStream(SQLParameter.PASSPORT_SCAN);


        UserDetail userDetail = new UserDetail();

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
        userDetail.setRole(Role.valueOf(role));
        userDetail.setStatus(Status.valueOf(status));
        userDetail.setPassportScan(passportScan);

        return userDetail;
    }

    private static class SQLParameter {
        private final static String USER_ID = "user_id";
        private final static String LOGIN = "login";
        private final static String EMAIL = "email";
        private final static String ROLE = "role";
        private final static String RU_NAME = "ru_name";
        private final static String RU_SURNAME = "ru_surname";
        private final static String EN_NAME = "en_name";
        private final static String EN_SURNAME = "en_surname";
        private final static String GENDER = "gender";
        private final static String PASSPORT_SERIES = "passport_series";
        private final static String PASSPORT_NUMBER = "passport_number";
        private final static String PHONE_NUMBER = "phone_number";
        private final static String LOCATION = "location";
        private final static String STATUS = "user_status";
        private final static String PASSPORT_SCAN = "passport_scan";
    }
}

