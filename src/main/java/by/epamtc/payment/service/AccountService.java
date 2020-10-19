package by.epamtc.payment.service;

import by.epamtc.payment.entity.Account;
import by.epamtc.payment.entity.CardInfo;
import by.epamtc.payment.entity.Currency;
import by.epamtc.payment.entity.User;
import by.epamtc.payment.service.exception.ServiceException;

import java.util.List;

public interface AccountService {
    void transfer(CardInfo fromCard, CardInfo toCard, double amount);
    List<Account> getUserAccounts(User user) throws ServiceException;
    void createNewAccount(User user, Currency currency) throws ServiceException;
    List<Account> getAllAccounts() throws ServiceException;
}
