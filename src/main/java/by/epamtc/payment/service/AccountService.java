package by.epamtc.payment.service;

import by.epamtc.payment.entity.*;
import by.epamtc.payment.service.exception.ServiceException;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    void transfer(long fromCardId, long toCardId, BigDecimal amount, User user) throws ServiceException;

    List<Account> getUserAccounts(User user) throws ServiceException;

    void createNewAccount(User user, Currency currency) throws ServiceException;

    List<Account> getAllAccounts() throws ServiceException;

    void blockAccount(long accountId) throws ServiceException;

    void unblockAccount(long accountId) throws ServiceException;

    void pay(Transaction transaction) throws ServiceException;
}
