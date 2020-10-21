package by.epamtc.payment.dao.impl;

import by.epamtc.payment.dao.AccountDAO;
import by.epamtc.payment.dao.connection.ConnectionPool;
import by.epamtc.payment.dao.exception.DAOException;
import by.epamtc.payment.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class SQLAccountDAO implements AccountDAO {
    private final static Logger log = LogManager.getLogger();
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    //language=MySQL
    private final static String UPDATE_BALANCE_BY_ACCOUNT_ID = "UPDATE accounts SET balance=? WHERE account_number=?;";
    //language=MySQL
    private final static String SELECT_BALANCE_BY_ID = "SELECT balance FROM accounts WHERE account_id=? FOR UPDATE ;";
    //language=MySQL
    private final static String RATE_FOR_TRANSACTION = "SELECT rate " +
            "FROM exchange " +
            "WHERE source_currency=? AND final_currency=?;";
    //language=MySQL
    private final static String SELECT_USER_ACCOUNTS = "SELECT a.account_id, a.account_number, a.balance, " +
            "a.opening_date, a.user_id, s.status_name, c.currency " +
            "FROM accounts a " +
            "LEFT JOIN statuses s USING (status_id) " +
            "LEFT JOIN currencies c USING (currency_id) " +
            "WHERE a.user_id=?;";
    //language=MySQL
    private final static String CREATE_NEW_ACCOUNT = "INSERT INTO accounts " +
            "SET account_number=?, opening_date=?, user_id=?, " +
            "currency_id=(SELECT currency_id FROM currencies WHERE currency=?);";
    //language=MySQL
    private final static String SELECT_ALL_ACCOUNTS = "SELECT a.account_id, a.account_number, a.balance, " +
            "a.opening_date, u.login, s.status_name, c.currency " +
            "FROM accounts a " +
            "JOIN users u USING (user_id) " +
            "JOIN statuses s USING (status_id) " +
            "JOIN currencies c USING (currency_id);";
    //language=MySQL
    private final static String ADD_TRANSACTION = "INSERT INTO transactions " +
            "SET date=?, amount=?, destination=?, cards_id=?, " +
            "currency_id=(SELECT currency_id FROM currencies WHERE currency=?),  " +
            "transaction_types_id=(SELECT id FROM transaction_types WHERE type=?);";

    @Override
    public void transfer(CardInfo fromCard, CardInfo toCard, double amount) {
        Connection connection = null;
        double rate = 1;

        try {
            rate = getRate(fromCard, toCard);
            connection = connectionPool.takeConnection();

            connection.setAutoCommit(false);

            withdraw(connection, fromCard, amount);
            deposit(connection, toCard, amount, rate);

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                log.info("база данных полетела");
            }
        } finally {
            connectionPool.closeConnection(connection);
        }
    }

    @Override
    public List<Account> getUserAccounts(User user) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Account> accounts = new ArrayList<>();

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_USER_ACCOUNTS);
            preparedStatement.setLong(1, user.getId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Account account = getAccount(resultSet);
                accounts.add(account);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
        return accounts;
    }

    @Override
    public void createNewAccount(User user, Account account) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(CREATE_NEW_ACCOUNT);
            preparedStatement.setString(1, account.getAccountNumber());
            preparedStatement.setDate(2, new Date(account.getOpeningDate().getTime()));
            preparedStatement.setLong(3, user.getId());
            preparedStatement.setString(4, account.getCurrency().name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }

//    @Override
//    public void pay(Transaction transaction ) throws DAOException {
//        Connection connection;
//
//
//        connection = connectionPool.takeConnection();
//        try {
//            withdraw(connection, fromCard, amount);
//        } catch (SQLException e) {
//            throw new DAOException(e);
//        }
//
//    }

    @Override
    public List<Account> getAllAccounts() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Account> accounts = new ArrayList<>();

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_ACCOUNTS);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Account account = getAccount(resultSet);

                accounts.add(account);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
        return accounts;
    }

    private double getRate(CardInfo fromCard, CardInfo toCard) throws SQLException {
        Connection connection;
        connection = connectionPool.takeConnection();

        PreparedStatement rateStatement = null;
        ResultSet resultSet = null;
        double rate = 1;

        try {
            rateStatement = connection.prepareStatement(RATE_FOR_TRANSACTION);
            rateStatement.setString(1, fromCard.getCurrency().name());
            rateStatement.setString(2, toCard.getCurrency().name());
            resultSet = rateStatement.executeQuery();
            if (resultSet.next()) {
                rate = resultSet.getDouble("rate");
            }
        } finally {
            connectionPool.closeConnection(connection, rateStatement, resultSet);
        }
        return rate;
    }

    private void withdraw(Connection connection, CardInfo fromCard, double amount) throws SQLException {
        PreparedStatement withdrawStatement = null;

        try {
            withdrawStatement = connection.prepareStatement(UPDATE_BALANCE_BY_ACCOUNT_ID);
            withdrawStatement.setDouble(1, amount);
            withdrawStatement.setString(2, fromCard.getAccountNumber());
            withdrawStatement.executeUpdate();
        } finally {
            if (withdrawStatement != null) {
                withdrawStatement.close();
            }
        }
    }

    private void deposit(Connection connection, CardInfo toCard, double amount, double rate) throws SQLException {
        PreparedStatement depositStatement = null;
        try {
            depositStatement = connection.prepareStatement(UPDATE_BALANCE_BY_ACCOUNT_ID);
            depositStatement.setDouble(1, amount);
            depositStatement.setString(2, toCard.getAccountNumber());
            depositStatement.executeUpdate();

        } finally {
            if (depositStatement != null) {
                depositStatement.close();
            }
        }
    }

    private Account getAccount(ResultSet resultSet) throws SQLException {
        Account account = new Account();

        long id = resultSet.getLong(SQLParameter.ACCOUNT_ID);
        String accountNumber = resultSet.getString(SQLParameter.ACCOUNT_NUMBER);
        Date openingDate = resultSet.getDate(SQLParameter.OPENING_DATE);
        long userId = resultSet.getLong(SQLParameter.USER_ID);
        Status status = Status.valueOf(resultSet.getString(SQLParameter.STATUS_NAME));
        BigDecimal balance = resultSet.getBigDecimal(SQLParameter.BALANCE);
        Currency currency = Currency.valueOf(resultSet.getString(SQLParameter.CURRENCY));

        account.setId(id);
        account.setAccountNumber(accountNumber);
        account.setOpeningDate(openingDate);
        account.setUserId(userId);
        account.setStatus(status);
        account.setBalance(balance);
        account.setCurrency(currency);

        return account;
    }

    //        private void writeTransaction(Connection connection, Transaction transaction) throws SQLException {
//        PreparedStatement preparedStatement = null;
//        try {
//            preparedStatement = connection.prepareStatement(ADD_TRANSACTION);
//            preparedStatement.setDate(1, new Date(new java.util.Date().getTime()));
//            preparedStatement.setDouble(2, transaction.getAmount);
//
//        } finally {
//            if (depositStatement != null) {
//                depositStatement.close();
//            }
//        }
    private static class SQLParameter {

        private final static String USER_ID = "user_id";
        private final static String ACCOUNT_ID = "account_id";
        private final static String ACCOUNT_NUMBER = "account_number";
        private final static String OPENING_DATE = "opening_date";
        private final static String STATUS_NAME = "status_name";
        private final static String BALANCE = "balance";
        private final static String CURRENCY = "currency";
    }
}

