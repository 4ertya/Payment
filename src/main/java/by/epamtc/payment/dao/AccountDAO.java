package by.epamtc.payment.dao;

import by.epamtc.payment.entity.Card;
import by.epamtc.payment.entity.CardInfo;

public interface AccountDAO {
    void transfer(CardInfo fromCard, CardInfo toCard, double amount);
}
