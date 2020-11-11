package by.epamtc.payment.service.exception;

public class InsufficientFundsServiceException extends ServiceException{
    public InsufficientFundsServiceException() {
        super();
    }

    public InsufficientFundsServiceException(Exception e) {
        super(e);
    }

    public InsufficientFundsServiceException(String message, Exception e) {
        super(message, e);
    }
}
