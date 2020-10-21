package by.epamtc.payment.service.impl;

import by.epamtc.payment.dao.AccountDAO;
import by.epamtc.payment.dao.DAOFactory;
import by.epamtc.payment.dao.exception.DAOException;
import by.epamtc.payment.entity.*;
import by.epamtc.payment.service.AccountService;
import by.epamtc.payment.service.exception.ServiceException;

import java.math.BigDecimal;
import java.util.Date;
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

    @Override
    public void createNewAccount(User user, Currency currency) throws ServiceException {

        Account account = new Account();

        account.setCurrency(currency);
        account.setStatus(Status.ACTIVE);
        account.setOpeningDate(new Date());
        account.setAccountNumber("1111" + currency + "222222222222");
        account.setUserId(user.getId());

        try {
            accountDAO.createNewAccount(user, account);
        } catch (DAOException e) {
            throw new ServiceException();
        }
    }

    @Override
    public List<Account> getAllAccounts() throws ServiceException {
        List<Account> accounts;

        try {
            accounts = accountDAO.getAllAccounts();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return accounts;
    }
}
