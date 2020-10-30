package by.epamtc.payment.dao.impl;

import by.epamtc.payment.dao.TransactionDAO;
import by.epamtc.payment.dao.connection.ConnectionPool;
import by.epamtc.payment.dao.exception.DAOException;
import by.epamtc.payment.entity.Currency;
import by.epamtc.payment.entity.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLTransactionDAO implements TransactionDAO {
    private final static Logger log = LogManager.getLogger();
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    //language=MySQL
    private final static String SELECT_FIVE_LAST_PAYMENTS="SELECT tr.date, tr.amount, tr.destination, c.currency, " +
            "cards.card_number FROM transactions tr " +
            "JOIN transaction_types ON tr.transaction_types_id=transaction_types.id " +
            "JOIN currencies c USING (currency_id) JOIN cards USING (card_id) " +
            "JOIN accounts " +
            "USING (account_id) " +
            "WHERE accounts.user_id=? " +
            "AND (transaction_types.type=? OR transaction_types.type=NULL);";

    @Override
    public List<Transaction> getFiveLastPayments(Long userId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Transaction transaction;
        List<Transaction> transactions = new ArrayList<>();
        try{
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_FIVE_LAST_PAYMENTS);
            preparedStatement.setLong(1,userId);
            preparedStatement.setString(2,"PAYMENT");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                transaction = new Transaction();
                transaction.setDate(resultSet.getDate("date"));
                transaction.setCardNumber(resultSet.getLong("card_number"));
                transaction.setAmount(resultSet.getBigDecimal("amount"));
                transaction.setCurrency(Currency.valueOf(resultSet.getString("currency")));
                transaction.setDestination(resultSet.getString("destination"));

                transactions.add(transaction);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }finally {
            connectionPool.closeConnection(connection,preparedStatement,resultSet);
        }
        return transactions;
    }
}
