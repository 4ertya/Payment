package by.epamtc.payment.service.impl;

import by.epamtc.payment.dao.*;
import by.epamtc.payment.dao.exception.DAOException;
import by.epamtc.payment.dao.exception.DAOUserExistException;
import by.epamtc.payment.dao.exception.DAOUserNotFoundException;
import by.epamtc.payment.entity.User;
import by.epamtc.payment.service.exception.ServiceException;
import by.epamtc.payment.service.exception.ServiceUserExistException;
import by.epamtc.payment.service.exception.ServiceUserNotFoundException;
import by.epamtc.payment.service.UserService;

public class UserServiceImpl implements UserService {
    private final DAOFactory instance = DAOFactory.getInstance();
    private final UserDAO userDAO = instance.getUserDAO();

    @Override
    public void registration(User user) throws ServiceException, ServiceUserExistException {

        try {
            userDAO.registration(user);
        } catch (DAOException e) {
            throw new ServiceException(e);
        } catch (DAOUserExistException e) {
            throw  new ServiceUserExistException();
        }
    }

    @Override
    public User login(String login, String password) throws ServiceException, ServiceUserNotFoundException {
        User user;
        try {
            user = userDAO.login(login, password);
        } catch (DAOException e) {
            throw new ServiceException(e);
        } catch (DAOUserNotFoundException e) {
            throw new ServiceUserNotFoundException();
        }
        return user;
    }

}
