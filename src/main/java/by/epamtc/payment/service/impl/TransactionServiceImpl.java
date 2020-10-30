package by.epamtc.payment.service.impl;

import by.epamtc.payment.dao.CardDAO;
import by.epamtc.payment.dao.DAOFactory;
import by.epamtc.payment.dao.TransactionDAO;
import by.epamtc.payment.dao.exception.DAOException;
import by.epamtc.payment.entity.Transaction;
import by.epamtc.payment.service.TransactionService;
import by.epamtc.payment.service.exception.ServiceException;

import java.util.List;

public class TransactionServiceImpl implements TransactionService {

    private final DAOFactory instance = DAOFactory.getInstance();
    private final TransactionDAO transactionDAO = instance.getSqlTransactionDAO();

    @Override
    public List<Transaction> getFiveLastPayments(Long userId) throws ServiceException {
        List<Transaction> transactions;

        try {
            transactions= transactionDAO.getFiveLastPayments(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return transactions;
    }
}
