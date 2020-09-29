package by.epamtc.payment.service;

import by.epamtc.payment.entity.User;
import by.epamtc.payment.service.exception.ServiceException;
import by.epamtc.payment.service.exception.ServiceUserExistException;
import by.epamtc.payment.service.exception.ServiceUserNotFoundException;

public interface UserService {
    void registration(User user) throws ServiceException, ServiceUserExistException;
    User login (String login, String password) throws ServiceException, ServiceUserNotFoundException;
}