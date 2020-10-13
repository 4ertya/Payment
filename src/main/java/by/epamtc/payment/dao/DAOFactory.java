package by.epamtc.payment.dao;

import by.epamtc.payment.dao.impl.SQLAccountDAO;
import by.epamtc.payment.dao.impl.SQLCardDAO;
import by.epamtc.payment.dao.impl.SQLUserDAO;

public class DAOFactory {

    private static final DAOFactory instance = new DAOFactory();

    private static final SQLUserDAO sqlUserDAO = new SQLUserDAO();
    private static final SQLCardDAO sqlCardDAO = new SQLCardDAO();
    private static final SQLAccountDAO sqlAccountDAO = new SQLAccountDAO();

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
}
