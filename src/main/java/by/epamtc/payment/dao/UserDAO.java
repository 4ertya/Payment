package by.epamtc.payment.dao;

import by.epamtc.payment.dao.exception.DAOException;
import by.epamtc.payment.entity.User;
import by.epamtc.payment.entity.UserDetail;

import java.util.List;

public interface UserDAO {

    void registration(User user) throws DAOException;

    User login(String login, String password) throws DAOException;

    User getUser(Long id) throws DAOException;

    UserDetail getUserDetail(Long id) throws DAOException;

    List<User> getAllUsers() throws DAOException;

}
