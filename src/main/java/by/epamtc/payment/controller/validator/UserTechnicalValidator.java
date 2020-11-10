package by.epamtc.payment.controller.validator;

import by.epamtc.payment.entity.AuthorizationData;
import by.epamtc.payment.entity.RegistrationData;
import by.epamtc.payment.entity.UserDetail;

public class UserTechnicalValidator {

    private static final String LOGIN_REGEXP = "^[0-9a-zA-Z_-]{3,15}$";
    private static final String PASSWORD_REGEXP = "^[\\w\\-]{8,20}$";
    private static final String EMAIL_REGEXP = "^[^\\s]+@[^\\s]+\\.[^\\s]+$";
    private static final String EN_NAMES_REGEXP = "^[a-zA-Z]+$";
    private static final String RU_NAMES_REGEXP = "^[а-яА-Я]+$";
    private static final String GENDER_REGEXP = "^[МЖ]$";
    private static final String PASSPORT_SERIES_REGEXP = "^[А-Я]{2}$";
    private static final String PHONE_NUMBER_REGEXP = "\\+375-[0-9]{2}-[0-9]{3}-[0-9]{2}-[0-9]{2}";

    public static boolean loginValidation(AuthorizationData authorizationData) {
        String login = authorizationData.getLogin();
        String password = authorizationData.getPassword();

        return login != null && login.matches(LOGIN_REGEXP)
                && password != null && password.matches(PASSWORD_REGEXP);
    }

    public static boolean registrationValidation(RegistrationData data) {
        String login = data.getLogin();
        String password = data.getPassword();
        String email = data.getEmail();

        return email != null && email.matches(EMAIL_REGEXP)
                && login != null && login.matches(LOGIN_REGEXP)
                && password != null && password.matches(PASSWORD_REGEXP);
    }

    public static boolean userDetailValidation(UserDetail userDetails) {

        long id = userDetails.getId();
        String ruName = userDetails.getRuName();
        String ruSurname = userDetails.getRuSurname();
        String enName = userDetails.getEnName();
        String enSurname = userDetails.getEnSurname();
        String gender = userDetails.getGender();
        String passportSeries = userDetails.getPassportSeries();
        int passportNumber = userDetails.getPassportNumber();
        String phoneNumber = userDetails.getPhoneNumber();
        String location = userDetails.getLocation();


        return id > 0
                && ruName != null && ruName.matches(RU_NAMES_REGEXP)
                && ruSurname != null && ruSurname.matches(RU_NAMES_REGEXP)
                && enName != null && enName.matches(EN_NAMES_REGEXP)
                && enSurname != null && enSurname.matches(EN_NAMES_REGEXP)
                && (gender == null || gender.matches(GENDER_REGEXP))
                && passportSeries != null && passportSeries.matches(PASSPORT_SERIES_REGEXP)
                && passportNumber > 999999
                && phoneNumber != null && phoneNumber.matches(PHONE_NUMBER_REGEXP)
                && location != null;
    }
}
