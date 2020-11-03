package by.epamtc.payment.controller.validator;

import by.epamtc.payment.entity.Currency;
import by.epamtc.payment.entity.PaymentSystem;
import by.epamtc.payment.entity.User;

public class CardTechnicalValidator {

    public static boolean cardIdValidation(long cardId) {
        return cardId > 0;
    }

    public static boolean crateNewCardValidation(User user, String accountNumber, int term, PaymentSystem paymentSystem) {
        return user != null
                && accountNumber != null
                && term > 0
                && paymentSystem != null;
    }
}
