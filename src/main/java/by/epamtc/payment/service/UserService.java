package by.epamtc.payment.service;

import by.epamtc.payment.entity.User;
import by.epamtc.payment.entity.UserDetail;
import by.epamtc.payment.service.exception.ServiceException;

import java.util.List;

public interface UserService {
    void registration(User user) throws ServiceException;

    User login(String login, String password) throws ServiceException;

    User getUser(Long id) throws ServiceException;

    UserDetail getUserDetail(Long id) throws ServiceException;

    List<User> getAllUsers() throws ServiceException;
}