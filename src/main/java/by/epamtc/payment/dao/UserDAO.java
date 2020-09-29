package by.epamtc.payment.dao;

import by.epamtc.payment.dao.exception.DAOException;
import by.epamtc.payment.dao.exception.DAOUserExistException;
import by.epamtc.payment.dao.exception.DAOUserNotFoundException;
import by.epamtc.payment.entity.User;

public interface UserDAO {

    void registration(User user) throws DAOException, DAOUserExistException;

    User login(String login, String password) throws DAOException, DAOUserNotFoundException;
}
