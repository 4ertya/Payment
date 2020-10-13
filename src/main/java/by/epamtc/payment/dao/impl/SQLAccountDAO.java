package by.epamtc.payment.dao.impl;

import by.epamtc.payment.dao.AccountDAO;
import by.epamtc.payment.dao.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLAccountDAO implements AccountDAO {
    private final static Logger log = LogManager.getLogger();
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final static String WITHDRAW_FROM_ACCOUNT = "UPDATE accounts SET balance=balance-? WHERE id=?;";
    private final static String DEPOSIT_TO_ACCOUNT = "UPDATE accounts SET balance=balance+? WHERE id=?;";

    @Override
    public void transfer(int AccountIdFrom, int AccountIdTo, double amount) {
        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            PreparedStatement withdrawStatement = null;
            try {
                withdrawStatement = connection.prepareStatement(WITHDRAW_FROM_ACCOUNT);
                withdrawStatement.setDouble(1, amount);
                withdrawStatement.setInt(2, AccountIdFrom);
                withdrawStatement.executeUpdate();
            } finally {
                if (withdrawStatement != null) {
                    withdrawStatement.close();
                }
            }
            PreparedStatement depositStatement = null;
            try {
                depositStatement = connection.prepareStatement(DEPOSIT_TO_ACCOUNT);
                depositStatement.setDouble(1, amount);
                depositStatement.setInt(2, AccountIdTo);
                depositStatement.executeUpdate();
            } finally {
                if (depositStatement != null) {
                    depositStatement.close();
                }
            }
            connection.commit();
        } catch (SQLException e) {
            log.info("недостаточно средств");
            System.out.println("недостаточно средств");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("база данных полетела");
            }
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection);
            }
        }
    }
}

