package by.epamtc.payment.service;

import by.epamtc.payment.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {
    void registration(User user) throws ServiceException;
    User login (String login, String password) throws ServiceException;
}