package by.epamtc.payment.dao;

import by.epamtc.payment.dao.exception.DAOException;
import by.epamtc.payment.entity.Card;
import by.epamtc.payment.entity.CardInfo;
import by.epamtc.payment.entity.Status;
import by.epamtc.payment.entity.User;


import java.util.List;

public interface CardDAO{
    List<Card> getAllCards(User user) throws DAOException;
    void changeCardStatus(String cardNumber, Status status) throws DAOException;
    CardInfo getCardInfo(int id) throws DAOException;
}

