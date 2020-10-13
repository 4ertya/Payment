package by.epamtc.payment.dao;

import by.epamtc.payment.entity.Card;

public interface AccountDAO {
    void transfer(int AccountIdFrom, int AccountIdTo, double amount);
}
