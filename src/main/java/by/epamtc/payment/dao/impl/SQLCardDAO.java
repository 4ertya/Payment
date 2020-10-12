package by.epamtc.payment.dao.impl;

import by.epamtc.payment.dao.CardDAO;
import by.epamtc.payment.dao.connection.ConnectionPool;
import by.epamtc.payment.dao.exception.DAOException;
import by.epamtc.payment.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    @Override
    public List<Card> getAllCards(User user) throws DAOException {
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

                card.setId(resultSet.getInt("id"));
                card.setNumber(resultSet.getString("card_number"));
                card.setExpDate(resultSet.getDate("exp_date"));
                card.setOwnerName(resultSet.getString("owner_name"));
                card.setOwnerSurname(resultSet.getString("owner_surname"));
                card.setPin(resultSet.getInt("pincode"));
                card.setCvv(resultSet.getInt("cvv"));
                card.setAccount(resultSet.getString("account_id"));
                card.setStatus(Status.valueOf(resultSet.getString("status_name")));
                card.setPaymentSystem(PaymentSystem.valueOf(resultSet.getString("systems")));

                cards.add(card);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
        return cards;
    }

    @Override
    public void changeCardStatus(String cardNumber, Status status) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            System.out.println("status in sql - " + status.name());
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(UPDATE_CARD_STATUS);
            preparedStatement.setString(2, cardNumber);
            preparedStatement.setString(1, status.name());
            preparedStatement.executeUpdate();
            System.out.println("изменил статус");
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
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
                cardInfo.setId(resultSet.getInt("id"));
                cardInfo.setNumber(resultSet.getString("card_number"));
                cardInfo.setExpDate(resultSet.getDate("exp_date"));
                cardInfo.setOwnerName(resultSet.getString("owner_name"));
                cardInfo.setOwnerSurname(resultSet.getString("owner_surname"));
                cardInfo.setPin(resultSet.getInt("pincode"));
                cardInfo.setCvv(resultSet.getInt("cvv"));
                cardInfo.setAccount(resultSet.getString("account_id"));
                cardInfo.setStatus(Status.valueOf(resultSet.getString("status_name")));
                cardInfo.setPaymentSystem(PaymentSystem.valueOf(resultSet.getString("systems")));
                cardInfo.setAccount_number(resultSet.getString("account_number"));
                cardInfo.setBalance(resultSet.getBigDecimal("balance"));
                cardInfo.setCurrency(resultSet.getString("currency"));
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
        return cardInfo;
    }
}
