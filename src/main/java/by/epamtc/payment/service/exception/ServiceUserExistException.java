package by.epamtc.payment.service.exception;

public class ServiceUserExistException extends Exception{
    public ServiceUserExistException() {
        super();
    }

    public ServiceUserExistException(Exception e) {
        super(e);
    }

    public ServiceUserExistException(String message, Exception e) {
        super(message, e);
    }
}
