package by.epamtc.payment.dao.exception;

public class DAOUserNotFoundException extends DAOException {

    public DAOUserNotFoundException() {
        super();
    }

    public DAOUserNotFoundException(String message, Exception e) {
        super(message, e);
    }

    public DAOUserNotFoundException(Exception e) {
        super(e);
    }
}
