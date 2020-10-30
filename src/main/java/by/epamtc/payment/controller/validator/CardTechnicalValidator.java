package by.epamtc.payment.controller.validator;

public class CardTechnicalValidator {

    public static boolean blockCardValidation(long cardId){
        return cardId>0;
    }
}
