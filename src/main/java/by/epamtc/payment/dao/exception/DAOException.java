package by.epamtc.payment.dao.exception;

public class DAOException extends Exception {

    public DAOException() {
        super();
    }

    public DAOException(Exception e) {
        super(e);
    }

    public DAOException(String message, Exception e) {
        super(message, e);
    }
}
