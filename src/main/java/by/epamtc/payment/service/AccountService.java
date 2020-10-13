package by.epamtc.payment.service;

public interface AccountService {
    void transfer(int AccountIdFrom, int AccountIdTo, double amount);
}
