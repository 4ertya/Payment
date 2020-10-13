package by.epamtc.payment.service;

import by.epamtc.payment.entity.CardInfo;

public interface AccountService {
    void transfer(CardInfo fromCard, CardInfo toCard, double amount);
}
