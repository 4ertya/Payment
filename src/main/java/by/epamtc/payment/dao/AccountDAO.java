package by.epamtc.payment.dao;

import by.epamtc.payment.dao.exception.DAOException;
import by.epamtc.payment.entity.*;

import java.util.List;

public interface AccountDAO {
    List<Account> getAllAccounts() throws DAOException;

    List<Account> getUserAccounts(User user) throws DAOException;

    void createNewAccount(User user, Account account) throws DAOException;

//    void pay(Transaction transaction) throws DAOException;

    void transfer(CardInfo fromCard, CardInfo toCard, double amount);

}
