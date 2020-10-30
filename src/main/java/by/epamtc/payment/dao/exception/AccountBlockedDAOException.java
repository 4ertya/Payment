package by.epamtc.payment.dao.exception;

public class AccountBlockedDAOException extends DAOException{
    public AccountBlockedDAOException() {
        super();
    }

    public AccountBlockedDAOException(Exception e) {
        super(e);
    }

    public AccountBlockedDAOException(String message, Exception e) {
        super(message, e);
    }
}
