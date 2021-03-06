package by.epamtc.payment.service;

import by.epamtc.payment.entity.Transaction;
import by.epamtc.payment.service.exception.ServiceException;

import java.util.List;

public interface TransactionService {
    List<Transaction> getFiveLastPayments(Long userId) throws ServiceException;

    List<Transaction> getFiveLastTransfers(Long userId) throws ServiceException;

    List<Transaction> getUserTransactions(Long userId) throws ServiceException;

    List<Transaction> getAllTransactions() throws ServiceException;
}
