package by.epamtc.payment.dao;

import by.epamtc.payment.entity.Role;
import by.epamtc.payment.entity.User;

import java.util.List;

public interface UserDAO {

    void registration(User user) throws DAOException;

    boolean isUserExist(String login) throws DAOException;

    User login(String login, String password) throws DAOException;
}
