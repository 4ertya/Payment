package by.epamtc.payment.dao;

import by.epamtc.payment.dao.exception.DAOException;
import by.epamtc.payment.entity.*;


import java.util.List;

public interface CardDAO {
    List<Card> getUsersCards(User user) throws DAOException;

    void changeCardStatus(long cardNumber, Status status) throws DAOException;

    CardInfo getCardInfo(long cardId) throws DAOException;

    void createNewCard(User user, Card card) throws DAOException;

    List<Card> getAllCards() throws DAOException;
}

