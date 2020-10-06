package by.epamtc.payment.dao;

import by.epamtc.payment.dao.exception.DAOException;
import by.epamtc.payment.entity.Card;
import by.epamtc.payment.entity.User;


import java.util.List;

public interface CardDAO{
    List<Card> getAllCards(User user) throws DAOException;
}
