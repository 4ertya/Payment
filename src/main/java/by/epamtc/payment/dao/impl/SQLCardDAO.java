package by.epamtc.payment.dao.impl;

import by.epamtc.payment.dao.CardDAO;
import by.epamtc.payment.dao.connection.ConnectionPool;
import by.epamtc.payment.dao.exception.DAOException;
import by.epamtc.payment.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLCardDAO implements CardDAO {
    private final static Logger log = LogManager.getLogger();
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    //language=MySQL
    private final static String SELECT_ALL_CARDS_BY_USER = "SELECT card_id, card_number, exp_date, " +
            "exp_date, owner_name, owner_surname, account_number, status_name, system_name " +
            "FROM cards c " +
            "JOIN accounts a USING (account_id) " +
            "JOIN statuses s ON c.status_id=s.status_id " +
            "JOIN payment_systems USING (payment_system_id) " +
            "WHERE a.user_id=?;";
    //language=MySQL
    private final static String UPDATE_CARD_STATUS = "UPDATE cards c " +
            "JOIN statuses s " +
            "SET c.status_id=s.status_id " +
            "WHERE card_id=? " +
            "AND status_name=?;";
    //language=MySQL
    private final static String SELECT_CARD_INFO_BY_CARD_ID = "SELECT c.card_id, c.card_number, c.exp_date, " +
            "c.owner_name, c.owner_surname, a.balance, cur.currency, a.account_number, s.status_name, p.system_name " +
            "FROM cards c " +
            "LEFT JOIN accounts a USING (account_id) " +
            "LEFT JOIN statuses s ON c.status_id=s.status_id " +
            "LEFT JOIN payment_systems p USING (payment_system_id) " +
            "LEFT JOIN currencies cur " +
            "USING (currency_id) " +
            "WHERE c.card_id=?;";
    //language=MySQL
    private final static String INSERT_INTO_CARDS = "INSERT INTO cards " +
            "SET pincode=FLOOR(100 + (RAND() * 900)), cvv=FLOOR(100 + (RAND() * 900)), card_number=?, exp_date=?, " +
            "account_id=(SELECT account_id FROM accounts WHERE account_number=?), " +
            "owner_name=(SELECT en_name FROM user_details WHERE user_id=?), " +
            "owner_surname=(SELECT en_surname FROM user_details WHERE user_id=?), " +
            "payment_system_id=(SELECT payment_system_id FROM payment_systems WHERE system_name=?)";
    //language=MySQL
    private final static String SELECT_ALL_CARDS = "SELECT c.card_id, c.card_number, c.exp_date, c.owner_name, " +
            "c.owner_surname, c.pincode, c.cvv, a.account_number, s.status_name, p.system_name " +
            "FROM cards c " +
            "JOIN accounts a USING (account_id) " +
            "JOIN statuses s ON s.status_id=c.status_id " +
            "JOIN payment_systems p USING (payment_system_id);";

    @Override
    public List<Card> getUsersCards(User user) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Card card;
        List<Card> cards = new ArrayList<>();

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_CARDS_BY_USER);
            preparedStatement.setLong(1, user.getId());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                card = getCard(resultSet);
                cards.add(card);
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
        return cards;
    }

    @Override
    public void changeCardStatus(long cardId, Status status) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(UPDATE_CARD_STATUS);
            preparedStatement.setLong(1, cardId);
            preparedStatement.setString(2, status.name());
            preparedStatement.executeUpdate();

            log.info("Card " + cardId + " is " + status);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }


    @Override
    public CardInfo getCardInfo(long id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        CardInfo cardInfo = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_CARD_INFO_BY_CARD_ID);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                long cardNumber = resultSet.getLong(SQLParameter.CARD_NUMBER);
                java.util.Date expDate = resultSet.getDate(SQLParameter.EXP_DATE);
                String ownerName = resultSet.getString(SQLParameter.OWNER_NAME);
                String ownerSurname = resultSet.getString(SQLParameter.OWNER_SURNAME);
                String accountNumber = resultSet.getString(SQLParameter.ACCOUNT_NUMBER);
                Status status = Status.valueOf(resultSet.getString(SQLParameter.STATUS_NAME));
                PaymentSystem paymentSystem = PaymentSystem.valueOf(resultSet.getString(SQLParameter.PAYMENT_SYSTEM));
                BigDecimal balance = resultSet.getBigDecimal(SQLParameter.BALANCE_PARAMETER);
                Currency currency = Currency.valueOf(resultSet.getString(SQLParameter.CURRENCY_PARAMETER));

                cardInfo = new CardInfo();
                cardInfo.setId(id);
                cardInfo.setNumber(cardNumber);
                cardInfo.setExpDate(expDate);
                cardInfo.setOwnerName(ownerName);
                cardInfo.setOwnerSurname(ownerSurname);
                cardInfo.setAccountNumber(accountNumber);
                cardInfo.setStatus(status);
                cardInfo.setPaymentSystem(paymentSystem);
                cardInfo.setBalance(balance);
                cardInfo.setCurrency(currency);
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
        return cardInfo;
    }

    @Override
    public void createNewCard(User user, Card card) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(INSERT_INTO_CARDS);
            preparedStatement.setLong(1, card.getNumber());
            preparedStatement.setDate(2, new Date(card.getExpDate().getTime()));
            preparedStatement.setLong(4, user.getId());
            preparedStatement.setLong(5, user.getId());
            preparedStatement.setString(3, card.getAccountNumber());
            preparedStatement.setString(6, card.getPaymentSystem().name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public List<Card> getAllCards() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Card card;
        List<Card> cards = new ArrayList<>();
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_CARDS);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                card = getCard(resultSet);
                cards.add(card);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
        return cards;
    }

    private Card getCard(ResultSet resultSet) throws SQLException {
        Card card = new Card();

        card.setId(resultSet.getLong(SQLParameter.CARD_ID));
        card.setNumber(resultSet.getLong(SQLParameter.CARD_NUMBER));
        card.setExpDate(resultSet.getDate(SQLParameter.EXP_DATE));
        card.setOwnerName(resultSet.getString(SQLParameter.OWNER_NAME));
        card.setOwnerSurname(resultSet.getString(SQLParameter.OWNER_SURNAME));
        card.setAccountNumber(resultSet.getString(SQLParameter.ACCOUNT_NUMBER));
        card.setStatus(Status.valueOf(resultSet.getString(SQLParameter.STATUS_NAME)));
        card.setPaymentSystem(PaymentSystem.valueOf(resultSet.getString(SQLParameter.PAYMENT_SYSTEM)));
        return card;
    }


    private static class SQLParameter {
        private final static String CARD_ID = "card_id";
        private final static String CARD_NUMBER = "card_number";
        private final static String EXP_DATE = "exp_date";
        private final static String OWNER_NAME = "owner_name";
        private final static String OWNER_SURNAME = "owner_surname";
        private final static String ACCOUNT_ID_PARAMETER = "account_id";
        private final static String STATUS_NAME = "status_name";
        private final static String PAYMENT_SYSTEM = "system_name";
        private final static String ACCOUNT_NUMBER = "account_number";
        private final static String BALANCE_PARAMETER = "balance";
        private final static String CURRENCY_PARAMETER = "currency";
    }
}
