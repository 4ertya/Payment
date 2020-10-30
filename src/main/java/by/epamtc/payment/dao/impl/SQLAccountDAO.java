package by.epamtc.payment.dao.impl;

import by.epamtc.payment.dao.AccountDAO;
import by.epamtc.payment.dao.connection.ConnectionPool;
import by.epamtc.payment.dao.exception.AccountBlockedDAOException;
import by.epamtc.payment.dao.exception.DAOException;
import by.epamtc.payment.dao.exception.InsufficientFundsDAOException;
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
    private final static String SELECT_ACCOUNTS_NUMBERS = "SELECT account_number FROM accounts;";
    //language=MySQL
    private final static String UPDATE_BALANCE_BY_ACCOUNT_ID = "UPDATE accounts SET balance=? WHERE account_id=?;";
    //language=MySQL
    private final static String SELECT_BALANCE_BY_ID = "SELECT balance FROM accounts WHERE account_id=? FOR UPDATE ;";
    //language=MySQL
    private final static String SELECT_CARD_STATUS_BY_ID = "SELECT status_name FROM statuses JOIN cards USING (status_id) WHERE card_id=?;";
    //language=MySQL
    private final static String RATE_FOR_TRANSACTION = "SELECT rate " +
            "FROM exchange " +
            "WHERE source_currency=? AND final_currency=? FOR UPDATE ;";
    //language=MySQL
    private final static String SELECT_USER_ACCOUNTS = "SELECT a.account_id, a.account_number, a.balance, " +
            "a.opening_date,  ud.user_id, ud.ru_name, ud.ru_surname, ud.en_name, ud.en_surname, s.status_name, c.currency " +
            "FROM accounts a " +
            "LEFT JOIN statuses s USING (status_id) " +
            "JOIN user_details ud USING (user_id) " +
            "LEFT JOIN currencies c USING (currency_id) " +
            "WHERE a.user_id=?;";
    //language=MySQL
    private final static String CREATE_NEW_ACCOUNT = "INSERT INTO accounts " +
            "SET account_number=?, opening_date=?, user_id=?, " +
            "currency_id=(SELECT currency_id FROM currencies WHERE currency=?);";
    //language=MySQL
    private final static String SELECT_ACCOUNT_BY_CARD_ID = "SELECT a.account_id, a.account_number, a.balance, " +
            "a.opening_date,  ud.user_id, ud.ru_name, ud.ru_surname, ud.en_name, ud.en_surname, s.status_name, c.currency " +
            "FROM accounts a " +
            "LEFT JOIN statuses s USING (status_id) " +
            "JOIN user_details ud USING (user_id) " +
            "LEFT JOIN currencies c USING (currency_id) " +
            "JOIN cards card ON(a.account_id=card.account_id) " +
            "WHERE card.card_id=?;";
    //language=MySQL
    private final static String SELECT_ALL_ACCOUNTS = "SELECT a.account_id, a.account_number, a.balance, " +
            "a.opening_date, ud.user_id, ud.ru_name, ud.ru_surname, ud.en_name, ud.en_surname, s.status_name, c.currency " +
            "FROM accounts a " +
            "JOIN user_details ud USING (user_id) " +
            "JOIN statuses s USING (status_id) " +
            "JOIN currencies c USING (currency_id);";
    //language=MySQL
    private final static String ADD_TRANSACTION = "INSERT INTO transactions " +
            "SET amount=?, destination=?, card_id=?, " +
            "currency_id=(SELECT currency_id FROM currencies WHERE currency=?),  " +
            "transaction_types_id=(SELECT id FROM transaction_types WHERE type=?);";
    //language=MySQL
    private final static String UPDATE_ACCOUNT_STATUS = "UPDATE accounts a " +
            "JOIN statuses s " +
            "SET a.status_id=s.status_id " +
            "WHERE account_id=? " +
            "AND status_name=?;";


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

    @Override
    public void transfer(Card fromCard, Card toCard, BigDecimal amount) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        BigDecimal rate = new BigDecimal(1);
        Account fromAccount = new Account();
        Account toAccount = new Account();

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(SELECT_ACCOUNT_BY_CARD_ID);
            preparedStatement.setLong(1, fromCard.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                fromAccount = getAccount(resultSet);
            }

            preparedStatement = connection.prepareStatement(SELECT_ACCOUNT_BY_CARD_ID);
            preparedStatement.setLong(1, toCard.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                toAccount = getAccount(resultSet);
            }

            System.out.println(fromAccount.getStatus());
            System.out.println(toAccount.getStatus());

            if (fromAccount.getStatus()!=Status.ACTIVE || toAccount.getStatus()!=Status.ACTIVE) {
                throw new AccountBlockedDAOException();
            }

            preparedStatement = connection.prepareStatement(RATE_FOR_TRANSACTION);
            preparedStatement.setString(1, fromAccount.getCurrency().name());
            preparedStatement.setString(2, toAccount.getCurrency().name());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                rate = resultSet.getBigDecimal("rate");
            }

            BigDecimal result = amount.multiply(rate);
            BigDecimal withdraw = fromAccount.getBalance().subtract(result);
            BigDecimal deposit = toAccount.getBalance().add(result);

            if (fromAccount.getBalance().compareTo(withdraw) < 0) {
                throw new InsufficientFundsDAOException();
            }
            Transaction transaction = new Transaction();
            updateBalance(connection, fromAccount.getId(), withdraw);

            transaction.setDestination(toCard.getNumber().toString());
            transaction.setCurrency(fromCard.getCurrency());
            transaction.setCardId(fromCard.getId());
            transaction.setAmount(amount);
            transaction.setTransactionType(TransactionType.TRANSFER);

            addTransaction(connection,transaction);

            updateBalance(connection, toAccount.getId(), deposit);

            transaction=new Transaction();
            transaction.setDestination(fromCard.getNumber().toString());
            transaction.setCurrency(fromCard.getCurrency());
            transaction.setAmount(amount);
            transaction.setCardId(toCard.getId());
            transaction.setTransactionType(TransactionType.DEPOSIT);

            addTransaction(connection,transaction);

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();

            } catch (SQLException ex) {
                log.error("couldn't roll back", ex);
            }
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

    @Override
    public void changeAccountStatus(long accountId, Status status) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(UPDATE_ACCOUNT_STATUS);
            preparedStatement.setLong(1, accountId);
            preparedStatement.setString(2, status.name());
            preparedStatement.executeUpdate();
            log.info("Account" + accountId + " is " + status);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }
    }


    @Override
    public void pay(Transaction transaction) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        BigDecimal rate = new BigDecimal(1);
        Account account = new Account();
        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(SELECT_ACCOUNT_BY_CARD_ID);
            preparedStatement.setLong(1, transaction.getCardId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                account = getAccount(resultSet);
            }

            if (account.getStatus() != Status.ACTIVE) {
                throw new AccountBlockedDAOException();
            }

            preparedStatement = connection.prepareStatement(RATE_FOR_TRANSACTION);
            preparedStatement.setString(1, Currency.BYN.name());
            preparedStatement.setString(2, account.getCurrency().name());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                rate = resultSet.getBigDecimal("rate");
            }

            BigDecimal amount = transaction.getAmount().multiply(rate);
            BigDecimal result = account.getBalance().subtract(amount);

            if (account.getBalance().compareTo(amount) < 0) {
                throw new InsufficientFundsDAOException();
            }

            preparedStatement = connection.prepareStatement(UPDATE_BALANCE_BY_ACCOUNT_ID);
            preparedStatement.setBigDecimal(1, result);
            preparedStatement.setLong(2, account.getId());
            preparedStatement.executeUpdate();

            addTransaction(connection, transaction);

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                log.error("couldn't roll back", ex);
            }
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
    }

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

    @Override
    public List<String> getAccountsNumbers() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<String> accountsNumbers = new ArrayList<>();

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(SELECT_ACCOUNTS_NUMBERS);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String accountNumber = resultSet.getString(SQLParameter.ACCOUNT_NUMBER);
                accountsNumbers.add(accountNumber);
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }
        return accountsNumbers;
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
        String userName;
        String userSurname;

        userName = (resultSet.getString("ru_name") == null) ? resultSet.getString("en_name") : resultSet.getString("ru_name");
        userSurname = (resultSet.getString("ru_surname") == null) ? resultSet.getString("en_surname") : resultSet.getString("ru_surname");

        account.setId(id);
        account.setAccountNumber(accountNumber);
        account.setOpeningDate(openingDate);
        account.setUserId(userId);
        account.setStatus(status);
        account.setBalance(balance);
        account.setCurrency(currency);
        account.setName(userName);
        account.setSurname(userSurname);

        return account;
    }

    private void updateBalance(Connection connection, Long id, BigDecimal amount) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BALANCE_BY_ACCOUNT_ID)) {
            preparedStatement.setBigDecimal(1, amount);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        }
    }

    private void addTransaction(Connection connection, Transaction transaction) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_TRANSACTION)) {
            preparedStatement.setBigDecimal(1, transaction.getAmount());
            preparedStatement.setString(2, transaction.getDestination());
            preparedStatement.setLong(3, transaction.getCardId());
            preparedStatement.setString(4, transaction.getCurrency().name());
            preparedStatement.setString(5, transaction.getTransactionType().name());
            preparedStatement.executeUpdate();
        }
    }

    private static class SQLParameter {
        private final static String ACCOUNT_ID = "account_id";
        private final static String ACCOUNT_NUMBER = "account_number";
        private final static String OPENING_DATE = "opening_date";
        private final static String USER_ID = "user_id";
        private final static String STATUS_NAME = "status_name";
        private final static String BALANCE = "balance";
        private final static String CURRENCY = "currency";

    }
}

