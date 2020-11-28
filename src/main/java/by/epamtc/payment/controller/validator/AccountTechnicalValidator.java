package by.epamtc.payment.controller.validator;

import by.epamtc.payment.entity.Currency;
import by.epamtc.payment.entity.Transaction;
import by.epamtc.payment.entity.User;

import java.math.BigDecimal;

public class AccountTechnicalValidator {



    public static boolean accountIdValidation(long accountId) {
        return accountId > 0;
    }

    public static boolean crateNewAccountValidation(User user, Currency currency) {
        return user != null && currency != null;
    }

    public static boolean paymentValidation(Transaction transaction) {

        return transaction.getCardId() > 0
                && transaction.getAmount() != null
                && transaction.getAmount().compareTo(BigDecimal.ZERO)>0;
    }

    public static boolean transferValidation(long cardIdFrom, long cardIdTo, BigDecimal amount){
        return cardIdFrom>0
                && cardIdTo>0
                && amount.compareTo(BigDecimal.ZERO)>0;
    }
}
