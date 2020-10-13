package by.epamtc.payment.service.impl;

import by.epamtc.payment.dao.AccountDAO;
import by.epamtc.payment.dao.DAOFactory;
import by.epamtc.payment.service.AccountService;

public class AccountServiceImpl implements AccountService {
    private final DAOFactory instance = DAOFactory.getInstance();
    private final AccountDAO accountDAO = instance.getAccountDAO();

    @Override
    public void transfer(int accountIdFrom, int accountIdTo, double amount) {
        accountDAO.transfer(accountIdFrom, accountIdTo, amount);
    }
}
