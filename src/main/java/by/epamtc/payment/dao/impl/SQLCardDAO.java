package by.epamtc.payment.dao.impl;

import by.epamtc.payment.dao.CardDAO;
import by.epamtc.payment.dao.connection.ConnectionPool;
import by.epamtc.payment.dao.exception.DAOException;
import by.epamtc.payment.entity.Card;
import by.epamtc.payment.entity.PaymentSystem;
import by.epamtc.payment.entity.Status;
import by.epamtc.payment.entity.User;
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
    private final static String SELECT_ALL_CARDS_BY_USER = "Select * from payment_systems, cards " +
            "join accounts on cards.account_id=accounts.id " +
            "where users_id=? " +
            "AND payment_systems.id=cards.payment_systems_id;";

    @Override
    public List<Card> getAllCards(User user) throws DAOException {
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;
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
                card.setStatus(Status.ACTIVE);
                card.setPaymentSystem(PaymentSystem.valueOf(resultSet.getString("systems")));

                cards.add(card);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return cards;
    }
}
