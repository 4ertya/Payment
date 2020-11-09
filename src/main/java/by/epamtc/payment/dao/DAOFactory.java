package by.epamtc.payment.dao;

import by.epamtc.payment.dao.impl.SQLAccountDAO;
import by.epamtc.payment.dao.impl.SQLCardDAO;
import by.epamtc.payment.dao.impl.SQLTransactionDAO;
import by.epamtc.payment.dao.impl.SQLUserDAO;

public class DAOFactory {

    private static final DAOFactory instance = new DAOFactory();

    private final SQLUserDAO sqlUserDAO = new SQLUserDAO();
    private final SQLCardDAO sqlCardDAO = new SQLCardDAO();
    private final SQLAccountDAO sqlAccountDAO = new SQLAccountDAO();
    private final SQLTransactionDAO sqlTransactionDAO = new SQLTransactionDAO();

    public static DAOFactory getInstance() {
        return instance;
    }

    public SQLUserDAO getUserDAO() {
        return sqlUserDAO;
    }

    public SQLCardDAO getCardDAO() {
        return sqlCardDAO;
    }

    public SQLAccountDAO getAccountDAO() {
        return sqlAccountDAO;
    }

    public SQLTransactionDAO getTransactionDAO() {
        return sqlTransactionDAO;
    }
}

