package by.epamtc.payment.dao.exception;

public class DAOUserExistException extends Exception {
    public DAOUserExistException() {
        super();
    }

    public DAOUserExistException(Exception e) {
        super(e);
    }

    public DAOUserExistException(String message, Exception e) {
        super(message, e);
    }
}
