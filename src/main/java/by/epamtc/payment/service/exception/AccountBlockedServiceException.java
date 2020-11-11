package by.epamtc.payment.service.exception;

public class AccountBlockedServiceException extends ServiceException {
    public AccountBlockedServiceException() {
        super();
    }

    public AccountBlockedServiceException(Exception e) {
        super(e);
    }

    public AccountBlockedServiceException(String message, Exception e) {
        super(message, e);
    }
}
