package by.epamtc.payment.dao;

import by.epamtc.payment.dao.exception.DAOException;
import by.epamtc.payment.entity.*;
import by.epamtc.payment.service.exception.ServiceException;


import java.util.List;

public interface CardDAO {
    List<Card> getUsersCards(User user) throws DAOException;

    Card getCardById(Long id) throws DAOException;

    void changeCardStatus(long cardNumber, Status status) throws DAOException;

    void createNewCard(User user, Card card) throws DAOException;

    List<Card> getAllCards() throws DAOException;

    List<Long> getCardsNumbers() throws DAOException;

}

