package by.epamtc.payment.dao.impl;

import by.epamtc.payment.dao.CardDAO;
import by.epamtc.payment.dao.connection.ConnectionPool;
import by.epamtc.payment.dao.exception.DAOException;
import by.epamtc.payment.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class SQLCardDAO implements CardDAO {
    private final static Logger log = LogManager.getLogger();
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final static String SELECT_ALL_CARDS_BY_USER = "Select * from payment_systems, status, cards " +
            "join accounts on cards.account_id=accounts.id " +
            "where users_id=? " +
            "AND payment_systems.id=cards.payment_systems_id " +
            "AND status.id=cards.status_id;";
    private final static String UPDATE_CARD_STATUS = "UPDATE cards SET status_id = (SELECT id from status WHERE status_name!=?) WHERE cards.card_number=?;";
    private final static String SELECT_CARD_INFO_BY_CARD_ID = "SELECT cards.*, accounts.balance, accounts.account_number, status.status_name, payment_systems.systems, currencies.currency " +
            "FROM cards " +
            "LEFT JOIN accounts ON cards.account_id=accounts.id " +
            "LEFT JOIN status ON cards.status_id=status.id " +
            "LEFT JOIN payment_systems ON cards.payment_systems_id=payment_systems.id " +
            "LEFT JOIN currencies ON accounts.currences_id=currencies.id " +
            "WHERE cards.id=?;";
    private final static String SELECT_TRANSACTIONS = "SELECT * FROM transactions JOIN cards ON transactions.cards_id=cards.id JOIN accounts ON cards.account_id=accounts.id WHERE accounts.user_id=?;";
    private final static String CREATE_NEW_CARD = "INSERT INTO cards SET card_number=?," +
            " exp_date=?, " +
            "owner_name=(SELECT name FROM user_details WHERE users_id=?), " +
            "owner_surname=(SELECT surname FROM user_details WHERE users_id=?), " +
            "pincode=?, " +
            "cvv=?, " +
            "account_id=?, " +
            "payment_systems_id=(SELECT id FROM payment_systems WHERE systems=?);";
    private final static String SELECT_ALL_CARDS = "SELECT c.id, c.card_number, c.exp_date, c.owner_name, c.owner_surname, c.pincode, c.cvv, a.account_number, s.status_name, p.systems FROM cards c JOIN accounts a ON c.account_id=a.id JOIN status s ON s.id=c.status_id JOIN payment_systems p ON c.payment_systems_id=p.id;";


    private final static String ID_PARAMETER = "id";
    private final static String CARD_NUMBER_PARAMETER = "card_number";
    private final static String EXP_DATE_PARAMETER = "exp_date";
    private final static String OWNER_NAME_PARAMETER = "owner_name";
    private final static String OWNER_SURNAME_PARAMETER = "owner_surname";
    private final static String PINCODE_PARAMETER = "pincode";
    private final static String CVV_PARAMETER = "cvv";
    private final static String ACCOUNT_ID_PARAMETER = "account_id";
    private final static String STATUS_NAME_PARAMETER = "status_name";
    private final static String PAYMENT_SYSTEM_PARAMETER = "systems";
    private final static String ACCOUNT_NUMBER_PARAMETER = "account_number";
    private final static String BALANCE_PARAMETER = "balance";
    private final static String CURRENCY_PARAMETER = "currency";


    @Override
    public List<Card> getUserCards(User user) throws DAOException {
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
                card = new Card();

                card.setId(resultSet.getInt(ID_PARAMETER));
                card.setNumber(resultSet.getString(CARD_NUMBER_PARAMETER));
                card.setExpDate(resultSet.getDate(EXP_DATE_PARAMETER));
                card.setOwnerName(resultSet.getString(OWNER_NAME_PARAMETER));
                card.setOwnerSurname(resultSet.getString(OWNER_SURNAME_PARAMETER));
                card.setPin(resultSet.getInt(PINCODE_PARAMETER));
                card.setCvv(resultSet.getInt(CVV_PARAMETER));
                card.setAccount(resultSet.getString(ACCOUNT_ID_PARAMETER));
                card.setStatus(Status.valueOf(resultSet.getString(STATUS_NAME_PARAMETER)));
                card.setPaymentSystem(PaymentSystem.valueOf(resultSet.getString(PAYMENT_SYSTEM_PARAMETER)));

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
    public void changeCardStatus(String cardNumber, Status status) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(UPDATE_CARD_STATUS);
            preparedStatement.setString(2, cardNumber);
            preparedStatement.setString(1, status.name());
            preparedStatement.executeUpdate();
            log.info("Status changed. Card " + cardNumber);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

    @Override
    public CardInfo getCardInfo(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        CardInfo cardInfo = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_CARD_INFO_BY_CARD_ID);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                cardInfo = new CardInfo();
                cardInfo.setId(resultSet.getInt(ID_PARAMETER));
                cardInfo.setNumber(resultSet.getString(CARD_NUMBER_PARAMETER));
                cardInfo.setExpDate(resultSet.getDate(EXP_DATE_PARAMETER));
                cardInfo.setOwnerName(resultSet.getString(OWNER_NAME_PARAMETER));
                cardInfo.setOwnerSurname(resultSet.getString(OWNER_SURNAME_PARAMETER));
                cardInfo.setPin(resultSet.getInt(PINCODE_PARAMETER));
                cardInfo.setCvv(resultSet.getInt(CVV_PARAMETER));
                cardInfo.setAccount(resultSet.getString(ACCOUNT_NUMBER_PARAMETER));
                cardInfo.setStatus(Status.valueOf(resultSet.getString(STATUS_NAME_PARAMETER)));
                cardInfo.setPaymentSystem(PaymentSystem.valueOf(resultSet.getString(PAYMENT_SYSTEM_PARAMETER)));
                cardInfo.setAccount_number(resultSet.getString(ACCOUNT_NUMBER_PARAMETER));
                cardInfo.setBalance(resultSet.getBigDecimal(BALANCE_PARAMETER));
                cardInfo.setCurrency(resultSet.getString(CURRENCY_PARAMETER));
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
        return cardInfo;
    }

    @Override
    public void createNewCard(User user, int accountId, int term, PaymentSystem system) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.add(Calendar.YEAR, term);
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(CREATE_NEW_CARD);
            preparedStatement.setString(1, "0111111111111110");
            preparedStatement.setDate(2, new Date(calendar.getTimeInMillis()));
            preparedStatement.setLong(3, user.getId());
            preparedStatement.setLong(4, user.getId());
            preparedStatement.setString(5, "234");
            preparedStatement.setString(6, "255");
            preparedStatement.setInt(7, accountId);
            preparedStatement.setString(8, system.name());
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
                card = new Card();
                card.setId(resultSet.getInt(ID_PARAMETER));
                card.setNumber(resultSet.getString(CARD_NUMBER_PARAMETER));
                card.setExpDate(resultSet.getDate(EXP_DATE_PARAMETER));
                card.setOwnerName(resultSet.getString(OWNER_NAME_PARAMETER));
                card.setOwnerSurname(resultSet.getString(OWNER_SURNAME_PARAMETER));
                card.setPin(resultSet.getInt(PINCODE_PARAMETER));
                card.setCvv(resultSet.getInt(CVV_PARAMETER));
                card.setAccount(resultSet.getString(ACCOUNT_NUMBER_PARAMETER));
                card.setStatus(Status.valueOf(resultSet.getString(STATUS_NAME_PARAMETER)));
                card.setPaymentSystem(PaymentSystem.valueOf(resultSet.getString(PAYMENT_SYSTEM_PARAMETER)));

                cards.add(card);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
        return cards;
    }
}
