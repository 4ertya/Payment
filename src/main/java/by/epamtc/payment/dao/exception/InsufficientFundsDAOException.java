package by.epamtc.payment.dao.exception;

public class InsufficientFundsDAOException extends DAOException{
    public InsufficientFundsDAOException() {
        super();
    }

    public InsufficientFundsDAOException(Exception e) {
        super(e);
    }

    public InsufficientFundsDAOException(String message, Exception e) {
        super(message, e);
    }
}
