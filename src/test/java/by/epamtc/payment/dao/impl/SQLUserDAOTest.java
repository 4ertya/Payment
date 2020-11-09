package by.epamtc.payment.dao.impl;

import by.epamtc.payment.dao.connection.ConnectionPool;
import by.epamtc.payment.dao.exception.DAOException;
import by.epamtc.payment.dao.exception.DAOUserExistException;
import by.epamtc.payment.dao.exception.DAOUserNotFoundException;
import by.epamtc.payment.entity.*;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SQLUserDAOTest {


    @BeforeEach
    public void init() throws IOException, SQLException, URISyntaxException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection;
        connection = connectionPool.takeConnection();
        URL url1 = SQLUserDAOTest.class.getClassLoader()
                .getResource("create_test_db.sql");
        URL url2 = SQLUserDAOTest.class.getClassLoader()
                .getResource("fill_in_test_db.sql");


        assert url1 != null;
        List<String> str1 = Files.readAllLines(Paths.get(url1.toURI()));
        String sql1 = String.join("", str1);

        assert url2 != null;
        List<String> str2 = Files.readAllLines(Paths.get(url2.toURI()));
        String sql2 = String.join("", str2);

        Statement statement = connection.createStatement();
        statement.executeUpdate(sql1);
        statement.executeUpdate(sql2);
        connectionPool.closeConnection(connection, statement);
        System.out.println("бефор");
    }

    @Test
    void positiveLogin() throws DAOUserNotFoundException, DAOException {
        SQLUserDAO sqlUserDAO = new SQLUserDAO();

        AuthorizationData authorizationData = new AuthorizationData();
        authorizationData.setLogin("admin");
        authorizationData.setPassword("admin");

        User expected = new User();
        expected.setName("Dzmitry");
        expected.setSurname("Palchynski");
        expected.setId(1);
        expected.setRole(Role.ADMIN);
        expected.setStatus(Status.VERIFIED);

        User actual = sqlUserDAO.login(authorizationData);

        assertEquals(expected, actual);
    }

    @Test
    void loginForNonexistentUser() {
        SQLUserDAO sqlUserDAO = new SQLUserDAO();

        AuthorizationData authorizationData = new AuthorizationData();
        authorizationData.setLogin("nonexistent");
        authorizationData.setPassword("nonexistent");

        Assertions.assertThrows(DAOUserNotFoundException.class, () -> sqlUserDAO.login(authorizationData));
    }

    @Test
    void registration() throws DAOUserExistException, DAOException, SQLException {
        SQLUserDAO sqlUserDAO = new SQLUserDAO();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.takeConnection();

        RegistrationData registrationData = new RegistrationData();
        registrationData.setLogin("user");
        registrationData.setPassword("password");
        registrationData.setEmail("chertyac@gmail.com");
        registrationData.setRole(Role.USER);
        registrationData.setStatus(Status.NEW);

        sqlUserDAO.registration(registrationData);

        //language=MySQL
        String sql = "SELECT * FROM users " +
                "JOIN user_details USING (user_id) " +
                "JOIN roles USING(role_id) " +
                "JOIN user_statuses USING (user_status_id) " +
                "WHERE login=? AND password=?;";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, registrationData.getLogin());
        preparedStatement.setString(2, registrationData.getPassword());
        ResultSet resultSet = preparedStatement.executeQuery();
        String login = null;
        String password = null;
        String email = null;
        Role role = null;
        Status status = null;

        if (resultSet.next()) {
            login = resultSet.getString("login");
            password = resultSet.getString("password");
            email = resultSet.getString("email");
            role = Role.valueOf(resultSet.getString("role"));
            status = Status.valueOf(resultSet.getString("user_status"));
        }
        connectionPool.closeConnection(connection, preparedStatement, resultSet);
        assertEquals(registrationData.getLogin(), login);
        assertEquals(registrationData.getPassword(), password);
        assertEquals(registrationData.getEmail(), email);
        assertEquals(registrationData.getRole(), role);
        assertEquals(registrationData.getStatus(), status);
    }

    @Test
    void registrationWithExistingData() {
        SQLUserDAO sqlUserDAO = new SQLUserDAO();

        RegistrationData registrationData = new RegistrationData();
        registrationData.setLogin("admin");
        registrationData.setPassword("admin");
        registrationData.setEmail("palchmail@gmail.com");
        registrationData.setRole(Role.ADMIN);
        registrationData.setStatus(Status.VERIFIED);

        Assertions.assertThrows(DAOUserExistException.class, () -> sqlUserDAO.registration(registrationData));
    }

    @Test
    void getUserData() throws DAOException {
        SQLUserDAO sqlUserDAO = new SQLUserDAO();
        UserData actual = sqlUserDAO.getUserData(1L);
        UserData expected = new UserData();
        expected.setId(1);
        expected.setEmail("palchmail@gmail.com");
        expected.setLogin("admin");
        expected.setRole(Role.ADMIN);
        assertEquals(expected, actual);
    }

    @Test
    void getUserDetail() throws DAOException {
        SQLUserDAO sqlUserDAO = new SQLUserDAO();
        UserDetail expected = new UserDetail();
        expected.setId(1L);
        expected.setRuName("Дмитрий");
        expected.setRuSurname("Пальчинский");
        expected.setEnName("Dzmitry");
        expected.setEnSurname("Palchynski");
        expected.setGender("М");
        expected.setPassportSeries("МР");
        expected.setPassportNumber(2756569);
        expected.setPhoneNumber("+375-29-593-67-98");
        expected.setLocation("г.Минск ул.Славинского");

        UserDetail actual = sqlUserDAO.getUserDetail(1L);

        assertEquals(expected, actual);
    }

    @Test
    void getAllUsers() throws DAOException {
        SQLUserDAO sqlUserDAO = new SQLUserDAO();
        List<UserDetail> expected = new ArrayList<>();
        UserDetail userDetail = new UserDetail();
        userDetail.setId(1L);
        userDetail.setRuName("Дмитрий");
        userDetail.setRuSurname("Пальчинский");
        userDetail.setEnName("Dzmitry");
        userDetail.setEnSurname("Palchynski");
        userDetail.setGender("М");
        userDetail.setPassportSeries("МР");
        userDetail.setPassportNumber(2756569);
        userDetail.setPhoneNumber("+375-29-593-67-98");
        userDetail.setLocation("г.Минск ул.Славинского");
        userDetail.setRole(Role.ADMIN);
        userDetail.setStatus(Status.VERIFIED);

        expected.add(userDetail);

        List<UserDetail> actual = sqlUserDAO.getAllUsers();

        assertEquals(expected, actual);
    }

    @Test
    void updateUserDetails() throws DAOException, SQLException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.takeConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        SQLUserDAO sqlUserDAO = new SQLUserDAO();

        UserDetail actual = new UserDetail();
        UserDetail expected = new UserDetail();

        expected.setId(1L);
        expected.setRuName("Дмитрий");
        expected.setRuSurname("Пальчинский");
        expected.setEnName("Dmitry");
        expected.setEnSurname("Palchynski");
        expected.setGender("М");
        expected.setPassportSeries("МР");
        expected.setPassportNumber(2756569);
        expected.setPhoneNumber("+375-29-593-67-98");
        expected.setLocation("г.Минск");
        expected.setRole(Role.ADMIN);
        expected.setStatus(Status.VERIFIED);

        sqlUserDAO.updateUserDetails(expected);

        //Language=MySQL
        String sql = "SELECT ud.*, u.role_id,u.user_status_id, role, user_status FROM user_details ud " +
                "JOIN users u USING(user_id) " +
                "JOIN roles USING(role_id) " +
                "JOIN user_statuses USING(user_status_id) " +
                "WHERE user_id=?;";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, expected.getId());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                actual.setId(resultSet.getLong("user_id"));
                actual.setRuName(resultSet.getString("ru_name"));
                actual.setRuSurname(resultSet.getString("ru_surname"));
                actual.setEnName(resultSet.getString("en_name"));
                actual.setEnSurname(resultSet.getString("en_surname"));
                actual.setGender(resultSet.getString("gender"));
                actual.setPassportSeries(resultSet.getString("passport_series"));
                actual.setPassportNumber(resultSet.getInt("passport_number"));
                actual.setPhoneNumber(resultSet.getString("phone_number"));
                actual.setLocation(resultSet.getString("location"));
                actual.setRole(Role.valueOf(resultSet.getString("role")));
                actual.setStatus(Status.valueOf(resultSet.getString("user_status")));
            }
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }

        assertEquals(expected, actual);
    }
}