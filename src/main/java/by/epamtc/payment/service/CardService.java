package by.epamtc.payment.service;

import by.epamtc.payment.entity.Card;
import by.epamtc.payment.entity.User;
import by.epamtc.payment.service.exception.ServiceException;

import java.util.List;

public interface CardService {

    List<Card> getAllCards(User user) throws ServiceException;
}
