package by.epamtc.payment.service.impl;

import by.epamtc.payment.dao.AccountDAO;
import by.epamtc.payment.dao.CardDAO;
import by.epamtc.payment.dao.DAOFactory;
import by.epamtc.payment.dao.exception.AccountBlockedDAOException;
import by.epamtc.payment.dao.exception.DAOException;
import by.epamtc.payment.dao.exception.InsufficientFundsDAOException;
import by.epamtc.payment.entity.*;
import by.epamtc.payment.service.AccountService;
import by.epamtc.payment.service.exception.AccountBlockedServiceException;
import by.epamtc.payment.service.exception.InsufficientFundsServiceException;
import by.epamtc.payment.service.exception.ServiceException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AccountServiceImpl implements AccountService {
    private final DAOFactory daoFactory = DAOFactory.getInstance();
    private final AccountDAO accountDAO = daoFactory.getAccountDAO();
    private final CardDAO cardDAO = daoFactory.getCardDAO();


    @Override
    public void transfer(long fromCardId, long toCardId, BigDecimal amount, User user) throws ServiceException {
        try {

            Card fromCard = cardDAO.getCardById(fromCardId);
            if (fromCard.getOwnerName().equalsIgnoreCase(user.getName())){
                throw new UnsupportedOperationException();
            }
            Card toCard = cardDAO.getCardById(toCardId);
            accountDAO.transfer(fromCard, toCard, amount);
        } catch (AccountBlockedDAOException be) {
            throw new AccountBlockedServiceException(be);
        } catch (InsufficientFundsDAOException ie) {
            System.out.println("ОШИБКА SERVICE");
            throw new InsufficientFundsServiceException(ie);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
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
        account.setAccountNumber(createAccountNumber(currency));
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

    @Override
    public void blockAccount(long accountId) throws ServiceException {
        Status status = Status.BLOCKED;
        try {
            accountDAO.changeAccountStatus(accountId, status);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void unblockAccount(long accountId) throws ServiceException {
        Status status = Status.ACTIVE;
        try {
            accountDAO.changeAccountStatus(accountId, status);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void pay(Transaction transaction) throws ServiceException {
        try {
            accountDAO.pay(transaction);
        } catch (AccountBlockedDAOException be) {
            throw new AccountBlockedServiceException(be);
        } catch (InsufficientFundsDAOException ie) {
            throw new InsufficientFundsServiceException(ie);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    private String createAccountNumber(Currency currency) throws ServiceException {
        List<String> accountsNumbers;

        try {
            accountsNumbers = accountDAO.getAccountsNumbers();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        String result;
        do {
            int generateInt = ThreadLocalRandom.current().nextInt(100000, 1000000);
            long generateLong = ThreadLocalRandom.current().nextLong(1000000000L, 10000000000L);
            result = generateInt + currency.name() + generateLong;
        } while (accountsNumbers.contains(result));
        return result;
    }
}
