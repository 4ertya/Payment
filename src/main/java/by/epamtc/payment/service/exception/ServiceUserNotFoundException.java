package by.epamtc.payment.service.exception;

public class ServiceUserNotFoundException extends ServiceException {

    public ServiceUserNotFoundException() {
        super();
    }

    public ServiceUserNotFoundException(String message, Exception e) {
        super(message, e);
    }

    public ServiceUserNotFoundException(Exception e) {
        super(e);
    }
}
