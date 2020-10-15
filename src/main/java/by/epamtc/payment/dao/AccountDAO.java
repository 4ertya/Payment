package by.epamtc.payment.dao;

import by.epamtc.payment.dao.exception.DAOException;
import by.epamtc.payment.entity.Account;
import by.epamtc.payment.entity.Card;
import by.epamtc.payment.entity.CardInfo;
import by.epamtc.payment.entity.User;

import java.util.List;

public interface AccountDAO {
    void transfer(CardInfo fromCard, CardInfo toCard, double amount);
    List<Account> getUserAccounts(User user) throws DAOException;

}
