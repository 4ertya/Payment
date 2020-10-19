package by.epamtc.payment.dao.impl;

import by.epamtc.payment.dao.AccountDAO;
import by.epamtc.payment.dao.connection.ConnectionPool;
import by.epamtc.payment.dao.exception.DAOException;
import by.epamtc.payment.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class SQLAccountDAO implements AccountDAO {
    private final static Logger log = LogManager.getLogger();
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final static String WITHDRAW_FROM_ACCOUNT = "UPDATE accounts SET balance=balance-? WHERE id=?;";
    private final static String DEPOSIT_TO_ACCOUNT = "UPDATE accounts SET balance=balance+(?*?) WHERE id=?;";
    private final static String RATE_FOR_TRANSACTION = "SELECT rate " +
            "FROM exchange " +
            "WHERE source_currency=? AND final_currency=?;";
    private final static String SELECT_USER_ACCOUNTS = "SELECT a.id, a.account_number, a.balance, a.openning_date, s.status_name, c.currency FROM accounts a LEFT JOIN status s ON s.id=a.status_id LEFT JOIN currencies c ON a.currences_id=c.id WHERE a.users_id=?;";
    private final static String CREATE_NEW_ACCOUNT = "INSERT INTO accounts SET account_number=?, openning_date=?, users_id=?, currences_id=(SELECT id FROM currencies WHERE currency=?);";
    private final static String SELECT_ALL_ACCOUNTS = "SELECT a.id, a.account_number, a.balance, a.openning_date, u.login, s.status_name, c.currency FROM accounts a JOIN users u ON u.id=a.users_id JOIN status s ON s.id=a.status_id JOIN currencies c ON c.id=a.currences_id;";
    private final static String ADD_TRANSACTION = "INSERT INTO transactions SET date=?, amount=?, currency_id=(SELECT id FROM currencies WHERE currency=?), destination=?, cards_id=?, transaction_types_id=(SELECT id FROM transactions_types WHERE type=?);";

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
                Account account = new Account();
                account.setId(resultSet.getInt("id"));
                account.setAccountNumber(resultSet.getString("account_number"));
                account.setOpeningDate(resultSet.getDate("openning_date"));
                account.setStatus(Status.valueOf(resultSet.getString("status_name")));
                account.setBalance(resultSet.getDouble("balance"));
                account.setCurrency(Currency.valueOf(resultSet.getString("currency")));
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
    public void createNewAccount(User user, Currency currency) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(CREATE_NEW_ACCOUNT);
            preparedStatement.setString(1, "2201BY000123435813");
            preparedStatement.setDate(2, new Date(new java.util.Date().getTime()));
            preparedStatement.setLong(3, user.getId());
            preparedStatement.setString(4, currency.name());
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
                Account account = new Account();
                System.out.println(resultSet.getInt("id"));
                account.setId(resultSet.getInt("id"));
                account.setAccountNumber(resultSet.getString("account_number"));
                account.setOpeningDate(resultSet.getDate("openning_date"));
                account.setStatus(Status.valueOf(resultSet.getString("status_name")));
                account.setBalance(resultSet.getDouble("balance"));
                account.setCurrency(Currency.valueOf(resultSet.getString("currency")));
                account.setUser_id(resultSet.getString("login"));
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
            rateStatement.setString(1, fromCard.getCurrency());
            rateStatement.setString(2, toCard.getCurrency());
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
            withdrawStatement = connection.prepareStatement(WITHDRAW_FROM_ACCOUNT);
            withdrawStatement.setDouble(1, amount);
            withdrawStatement.setString(2, fromCard.getAccount());
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
            depositStatement = connection.prepareStatement(DEPOSIT_TO_ACCOUNT);
            depositStatement.setDouble(1, amount);
            depositStatement.setDouble(2, rate);
            depositStatement.setString(3, toCard.getAccount());
            depositStatement.executeUpdate();

        } finally {
            if (depositStatement != null) {
                depositStatement.close();
            }
        }
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
}
