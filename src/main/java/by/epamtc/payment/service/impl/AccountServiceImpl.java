package by.epamtc.payment.service.impl;

import by.epamtc.payment.dao.AccountDAO;
import by.epamtc.payment.dao.DAOFactory;
import by.epamtc.payment.dao.exception.DAOException;
import by.epamtc.payment.entity.Account;
import by.epamtc.payment.entity.Card;
import by.epamtc.payment.entity.CardInfo;
import by.epamtc.payment.entity.User;
import by.epamtc.payment.service.AccountService;
import by.epamtc.payment.service.exception.ServiceException;

import java.util.List;

public class AccountServiceImpl implements AccountService {
    private final DAOFactory instance = DAOFactory.getInstance();
    private final AccountDAO accountDAO = instance.getAccountDAO();

    @Override
    public void transfer(CardInfo fromCard, CardInfo toCard, double amount) {
        accountDAO.transfer(fromCard, toCard, amount);
    }

    @Override
    public List<Account> getUserAccounts(User user) throws ServiceException {
        List<Account> accounts;

        try {
            accounts = accountDAO.getUserAccounts(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return accounts;
    }
}
