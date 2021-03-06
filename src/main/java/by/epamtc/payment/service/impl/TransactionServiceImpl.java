package by.epamtc.payment.service.impl;

import by.epamtc.payment.dao.TransactionDAO;
import by.epamtc.payment.dao.exception.DAOException;
import by.epamtc.payment.dao.DAOFactory;
import by.epamtc.payment.entity.Transaction;
import by.epamtc.payment.service.TransactionService;
import by.epamtc.payment.service.exception.ServiceException;

import java.util.List;

public class TransactionServiceImpl implements TransactionService {

    private final DAOFactory daoFactory = DAOFactory.getInstance();
    private final TransactionDAO transactionDAO = daoFactory.getTransactionDAO();

    @Override
    public List<Transaction> getFiveLastPayments(Long userId) throws ServiceException {
        List<Transaction> transactions;

        try {
            transactions = transactionDAO.getFiveLastPayments(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return transactions;
    }

    @Override
    public List<Transaction> getFiveLastTransfers(Long userId) throws ServiceException {
        List<Transaction> transactions;

        try {
            transactions = transactionDAO.getFiveLastTransfers(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return transactions;
    }

    @Override
    public List<Transaction> getUserTransactions(Long userId) throws ServiceException {
        List<Transaction> transactions;

        try {
            transactions = transactionDAO.getUserTransactions(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return transactions;
    }

    @Override
    public List<Transaction> getAllTransactions() throws ServiceException {
        List<Transaction> transactions;

        try {
            transactions = transactionDAO.getAllTransactions();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return transactions;
    }
}
