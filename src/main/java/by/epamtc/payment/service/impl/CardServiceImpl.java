package by.epamtc.payment.service.impl;

import by.epamtc.payment.dao.CardDAO;
import by.epamtc.payment.dao.DAOFactory;
import by.epamtc.payment.dao.exception.DAOException;
import by.epamtc.payment.entity.*;
import by.epamtc.payment.service.CardService;
import by.epamtc.payment.service.exception.ServiceException;

import java.util.List;

public class CardServiceImpl implements CardService {
    private final DAOFactory instance = DAOFactory.getInstance();
    private final CardDAO cardDAO = instance.getCardDAO();

    @Override
    public List<Card> getUserCards(User user) throws ServiceException {
        List<Card> cards;
        try {
            cards = cardDAO.getUserCards(user);

//            for (Card card : cards) {
//                String temp = card.getNumber();
//                StringBuilder stringBuilder = new StringBuilder();
//                for (int i = 0; i < temp.length(); i++) {
//                    if (i % 4 == 0) {
//                        stringBuilder.append(" ");
//                    }
//                    stringBuilder.append(temp.charAt(i));
//                }
//                card.setNumber(stringBuilder.toString().trim());
//            }
            /*TODO: custom tag*/
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return cards;
    }

    @Override
    public void changeCardStatus(String cardNumber, Status status) throws ServiceException {
        try {
            cardDAO.changeCardStatus(cardNumber, status);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public CardInfo getCardInfo(int id) throws ServiceException {
        CardInfo cardInfo;
        try {
            cardInfo = cardDAO.getCardInfo(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return cardInfo;
    }

    @Override
    public void createNewCard(User user, int accountId, int term, PaymentSystem system) throws ServiceException {
        try {
            cardDAO.createNewCard(user, accountId, term, system);
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Card> getAllCards() throws ServiceException {
        List<Card> cards;

        try {
            cards = cardDAO.getAllCards();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return cards;
    }
}
