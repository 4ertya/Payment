package by.epamtc.payment.service.impl;

import by.epamtc.payment.dao.CardDAO;
import by.epamtc.payment.dao.DAOFactory;
import by.epamtc.payment.dao.exception.DAOException;
import by.epamtc.payment.entity.Card;
import by.epamtc.payment.entity.PaymentSystem;
import by.epamtc.payment.entity.Status;
import by.epamtc.payment.entity.User;
import by.epamtc.payment.service.CardService;
import by.epamtc.payment.service.exception.ServiceException;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CardServiceImpl implements CardService {
    private final DAOFactory daoFactory = DAOFactory.getInstance();
    private final CardDAO cardDAO = daoFactory.getCardDAO();

    @Override
    public List<Card> getUsersCards(User user) throws ServiceException {
        List<Card> cards;
        try {
            cards = cardDAO.getUsersCards(user);

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
    public void blockCard(long cardId) throws ServiceException {
        Status status = Status.BLOCKED;

        try {
            cardDAO.changeCardStatus(cardId, status);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void unblockCard(long cardId) throws ServiceException {
        Status status = Status.ACTIVE;
        try {
            cardDAO.changeCardStatus(cardId, status);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Card getCardById(long id) throws ServiceException {
        Card card;
        try {
            card = cardDAO.getCardById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return card;
    }

    @Override
    public void createNewCard(User user, String accountNumber, int term, PaymentSystem paymentSystem) throws ServiceException {
        Card card = new Card();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.add(Calendar.YEAR, term);

        card.setAccountNumber(accountNumber);
        card.setPaymentSystem(paymentSystem);
        card.setStatus(Status.ACTIVE);
        card.setExpDate(calendar.getTime());
        card.setNumber(createCardNumber(paymentSystem));

        try {
            cardDAO.createNewCard(user, card);
        } catch (DAOException e) {
            throw new ServiceException(e);
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

    private Long createCardNumber(PaymentSystem paymentSystem) throws ServiceException {
        List<Long> cardsNumbers;
        long cardNumber;
        switch (paymentSystem.name()) {
            case "VISA":
                cardNumber = 4000000000000000L;
                break;
            case "MASTERCARD":
                cardNumber = 5000000000000000L;
                break;
            default:
                cardNumber = 1000000000000000L;
                break;
        }
        try {
            cardsNumbers = cardDAO.getCardsNumbers();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        Long result;
        do {
            long generate = ThreadLocalRandom.current().nextLong(1000000000000000L);
            result = cardNumber + generate;
        } while (cardsNumbers.contains(result));
        return result;
    }
}


