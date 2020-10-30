package by.epamtc.payment.controller.validator;

import by.epamtc.payment.entity.AuthorizationData;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTechnicalValidatorTest {


    @ParameterizedTest
    @MethodSource("dataForPositiveLoginValidationTest")
    void positiveLoginValidation(String login, String password) {
        AuthorizationData authorizationData = new AuthorizationData();
        authorizationData.setLogin(login);
        authorizationData.setPassword(password);
        boolean actual = UserTechnicalValidator.loginValidation(authorizationData);
        assertTrue(actual);
    }

    @ParameterizedTest
    @MethodSource("dataForNegativeLoginValidationTest")
    void negativeLoginValidation() {
        AuthorizationData authorizationData = new AuthorizationData();
        String login = "ram1";
        String password = "123ab*_123";
        authorizationData.setLogin(login);
        authorizationData.setPassword(password);
        boolean actual = UserTechnicalValidator.loginValidation(authorizationData);
        assertFalse(actual);
    }

    @Disabled("registrationValidation has not been implemented yet")
    @Test
    void registrationValidation() {
    }

    @Disabled("userDetailValidation has not been implemented yet")
    @Test
    void userDetailValidation() {
    }

    private static Stream<Arguments> dataForPositiveLoginValidationTest() {
        return Stream.of(
                Arguments.of("login", "password"),
                Arguments.of("Dmitry", "123_password"),
                Arguments.of("Vasya123", "123_1232"));
    }

    private static Stream<Arguments> dataForNegativeLoginValidationTest() {
        return Stream.of(
                Arguments.of("login", "pass"),
                Arguments.of("Dmitry", "123_p*assword"),
                Arguments.of("Вася123", "123_1232"));
    }
}