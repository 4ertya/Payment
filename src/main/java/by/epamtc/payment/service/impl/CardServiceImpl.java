package by.epamtc.payment.service.impl;

import by.epamtc.payment.dao.CardDAO;
import by.epamtc.payment.dao.DAOFactory;
import by.epamtc.payment.dao.exception.DAOException;
import by.epamtc.payment.entity.Card;
import by.epamtc.payment.entity.User;
import by.epamtc.payment.service.CardService;
import by.epamtc.payment.service.exception.ServiceException;

import java.util.List;

public class CardServiceImpl implements CardService {
    private final DAOFactory instance = DAOFactory.getInstance();
    private final CardDAO cardDAO = instance.getCardDAO();

    @Override
    public List<Card> getAllCards(User user) throws ServiceException {
        List<Card> cards;
        try {
            cards = cardDAO.getAllCards(user);

            for (Card card : cards) {
                String temp = card.getNumber();
                StringBuilder stringBuilder=new StringBuilder();
                for (int i = 0; i < temp.length(); i++) {
                    if (i %4== 0) {
                        stringBuilder.append(" ");
                    }
                    stringBuilder.append(temp.charAt(i));
                }
                card.setNumber(stringBuilder.toString().trim());
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return cards;
    }
}
