package by.epamtc.payment.dao.impl;

import by.epamtc.payment.dao.AccountDAO;
import by.epamtc.payment.dao.connection.ConnectionPool;
import by.epamtc.payment.entity.CardInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLAccountDAO implements AccountDAO {
    private final static Logger log = LogManager.getLogger();
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private final static String WITHDRAW_FROM_ACCOUNT = "UPDATE accounts SET balance=balance-? WHERE id=?;";
    private final static String DEPOSIT_TO_ACCOUNT = "UPDATE accounts SET balance=balance+(?*?) WHERE id=?;";
    private final static String RATE_FOR_TRANSACTION = "SELECT rate " +
            "FROM exchange " +
            "WHERE source_currency=? AND final_currency=?;";

    @Override
    public void transfer(CardInfo fromCard, CardInfo toCard, double amount) {
        Connection connection = null;
        double rate=1;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            PreparedStatement rateStatement = null;
            ResultSet resultSet = null;
            try {
                rateStatement = connection.prepareStatement(RATE_FOR_TRANSACTION);
                rateStatement.setString(1, fromCard.getCurrency());
                rateStatement.setString(2, toCard.getCurrency());
                resultSet = rateStatement.executeQuery();
                if (resultSet.next()) {
                    rate = resultSet.getDouble("rate");
                }
                System.out.println("rate = "+ rate);
            } finally {
                if (rateStatement != null) {
                    rateStatement.close();
                }
            }

            PreparedStatement withdrawStatement = null;
            try {
                withdrawStatement = connection.prepareStatement(WITHDRAW_FROM_ACCOUNT);
                withdrawStatement.setDouble(1, amount);
                withdrawStatement.setString(2, fromCard.getAccount());
                withdrawStatement.executeUpdate();
                System.out.println("списано "+amount+" "+fromCard.getCurrency());
            } finally {
                if (withdrawStatement != null) {
                    withdrawStatement.close();
                }
            }
            PreparedStatement depositStatement = null;
            try {
                depositStatement = connection.prepareStatement(DEPOSIT_TO_ACCOUNT);
                depositStatement.setDouble(1, amount);
                depositStatement.setDouble(2, rate);
                depositStatement.setString(3, toCard.getAccount());
                depositStatement.executeUpdate();
                System.out.println("начислено "+(amount*rate)+" "+toCard.getCurrency());
            } finally {
                if (depositStatement != null) {
                    depositStatement.close();
                }
            }
            connection.commit();
        } catch (SQLException e) {
            log.info("недостаточно средств");
            System.out.println("недостаточно средств");
            e.printStackTrace();
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

