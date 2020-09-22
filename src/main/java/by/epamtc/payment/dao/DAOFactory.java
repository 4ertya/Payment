package by.epamtc.payment.dao;

import by.epamtc.payment.dao.impl.SQLUserDAO;

public class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();

    private static final SQLUserDAO sqlUserDAO = new SQLUserDAO();

    public SQLUserDAO getUserDAO() {
        return sqlUserDAO;
    }

    public static DAOFactory getInstance() {
        return instance;
    }
}
