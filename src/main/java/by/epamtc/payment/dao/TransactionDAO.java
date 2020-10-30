package by.epamtc.payment.dao;

import by.epamtc.payment.dao.exception.DAOException;
import by.epamtc.payment.entity.Transaction;

import java.util.List;

public interface TransactionDAO {

    List<Transaction> getFiveLastPayments(Long userId) throws DAOException;

}
