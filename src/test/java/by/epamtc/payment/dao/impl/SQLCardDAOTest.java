package by.epamtc.payment.dao.impl;

import by.epamtc.payment.dao.connection.ConnectionPool;
import by.epamtc.payment.dao.exception.DAOException;
import by.epamtc.payment.entity.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class SQLCardDAOTest {

    private final SQLCardDAO sqlCardDAO = new SQLCardDAO();
    private Card expectedCard;
    private static User user;

    @BeforeAll

    public static void initUser() {

        user = new User();
        user.setId(1L);
    }

    @BeforeEach
    public void initDB() throws IOException, SQLException, URISyntaxException {
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

    }

    @BeforeEach
    public void initExpectedCard() throws ParseException {
        expectedCard = new Card();
        expectedCard.setNumber(4444333322221111L);
        expectedCard.setExpDate(new Date(new SimpleDateFormat("yyyy-MM-dd").parse("2023-11-01").getTime()));
        expectedCard.setOwnerName("Dzmitry");
        expectedCard.setOwnerSurname("Palchynski");
        expectedCard.setAccountNumber("100000USD1000000000");
        expectedCard.setStatus(Status.ACTIVE);
        expectedCard.setPaymentSystem(PaymentSystem.VISA);
        expectedCard.setCurrency(Currency.USD);
        expectedCard.setBalance(BigDecimal.valueOf(1000.0));
    }

    @Test
    void getUsersCards() throws DAOException {
        List<Card> actual;
        List<Card> expected = new ArrayList<>();

        User user = new User();
        user.setId(1);


        expected.add(expectedCard);
        actual = sqlCardDAO.getUsersCards(user);

        assertIterableEquals(expected, actual);
    }

    @Test
    void getCardById() throws DAOException {
        Card actual = sqlCardDAO.getCardById(1L);
        assertEquals(expectedCard, actual);
    }

    @Test
    void changeCardStatus() throws DAOException, SQLException {

        //language=MySQL
        String SELECT_CARD_BY_ID = "SELECT c.card_id, c.card_number, c.exp_date, " +
                "c.owner_name, c.owner_surname, a.balance, cur.currency, a.account_number, s.status_name, p.system_name " +
                "FROM cards c " +
                "LEFT JOIN accounts a USING (account_id) " +
                "LEFT JOIN statuses s ON c.status_id=s.status_id " +
                "LEFT JOIN payment_systems p USING (payment_system_id) " +
                "LEFT JOIN currencies cur " +
                "USING (currency_id) " +
                "WHERE c.card_id=?;";

        expectedCard.setStatus(Status.BLOCKED);
        sqlCardDAO.changeCardStatus(1L, Status.BLOCKED);

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Card actual = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_CARD_BY_ID);
            preparedStatement.setLong(1, 1);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                actual = new Card();
                actual.setId(resultSet.getLong("card_id"));
                actual.setNumber(resultSet.getLong("card_number"));
                actual.setExpDate(resultSet.getDate("exp_date"));
                actual.setOwnerName(resultSet.getString("owner_name"));
                actual.setOwnerSurname(resultSet.getString("owner_surname"));
                actual.setAccountNumber(resultSet.getString("account_number"));
                actual.setStatus(Status.valueOf(resultSet.getString("status_name")));
                actual.setPaymentSystem(PaymentSystem.valueOf(resultSet.getString("system_name")));
                actual.setCurrency(Currency.valueOf(resultSet.getString("currency")));
                actual.setBalance(resultSet.getBigDecimal("balance"));
            }
        } finally {
            ConnectionPool.getInstance().closeConnection(connection, preparedStatement, resultSet);
        }

        assertEquals(expectedCard, actual);
    }

    @Test
    void createNewCard() throws DAOException, SQLException {
        //language=MySQL
        String SELECT_CARD_BY_NUMBER = "SELECT c.card_id, c.card_number, c.exp_date, " +
                "c.owner_name, c.owner_surname, a.balance, cur.currency, a.account_number, s.status_name, p.system_name " +
                "FROM cards c " +
                "LEFT JOIN accounts a USING (account_id) " +
                "LEFT JOIN statuses s ON c.status_id=s.status_id " +
                "LEFT JOIN payment_systems p USING (payment_system_id) " +
                "LEFT JOIN currencies cur " +
                "USING (currency_id) " +
                "WHERE c.card_number=?;";


        expectedCard.setNumber(1111000011110000L);
        expectedCard.setExpDate(new GregorianCalendar(2023, Calendar.NOVEMBER, 11).getTime());
        expectedCard.setAccountNumber("100000USD1000000000");
        expectedCard.setPaymentSystem(PaymentSystem.VISA);
        sqlCardDAO.createNewCard(user, expectedCard);

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Card actual = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_CARD_BY_NUMBER);
            preparedStatement.setLong(1, 1111000011110000L);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                actual = new Card();
                actual.setId(resultSet.getLong("card_id"));
                actual.setNumber(resultSet.getLong("card_number"));
                actual.setExpDate(resultSet.getDate("exp_date"));
                actual.setOwnerName(resultSet.getString("owner_name"));
                actual.setOwnerSurname(resultSet.getString("owner_surname"));
                actual.setAccountNumber(resultSet.getString("account_number"));
                actual.setStatus(Status.valueOf(resultSet.getString("status_name")));
                actual.setPaymentSystem(PaymentSystem.valueOf(resultSet.getString("system_name")));
                actual.setCurrency(Currency.valueOf(resultSet.getString("currency")));
                actual.setBalance(resultSet.getBigDecimal("balance"));
            }
        } finally {
            ConnectionPool.getInstance().closeConnection(connection, preparedStatement, resultSet);
        }
        assertEquals(expectedCard, actual);
    }

    @Test
    void getAllCards() throws DAOException {
        List<Card> actual = sqlCardDAO.getAllCards();
        List<Card> expected = new ArrayList<>();
        expected.add(expectedCard);

        assertIterableEquals(expected, actual);
    }

    @Test
    void getCardsNumbers() throws DAOException {
        List<Long> actual = sqlCardDAO.getCardsNumbers();
        List<Long> expected = new ArrayList<>();
        expected.add(4444333322221111L);
        assertIterableEquals(expected, actual);
    }
}