package by.epamtc.payment.service.impl;

import by.epamtc.payment.dao.DAOFactory;
import by.epamtc.payment.dao.UserDAO;
import by.epamtc.payment.dao.exception.DAOException;
import by.epamtc.payment.dao.exception.DAOUserExistException;
import by.epamtc.payment.dao.exception.DAOUserNotFoundException;
import by.epamtc.payment.entity.User;
import by.epamtc.payment.entity.UserDetail;
import by.epamtc.payment.service.UserService;
import by.epamtc.payment.service.exception.ServiceException;
import by.epamtc.payment.service.exception.ServiceUserExistException;
import by.epamtc.payment.service.exception.ServiceUserNotFoundException;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final DAOFactory instance = DAOFactory.getInstance();
    private final UserDAO userDAO = instance.getUserDAO();

    @Override
    public void registration(User user) throws ServiceException {

        try {
            userDAO.registration(user);
        } catch (DAOUserExistException e) {
            throw new ServiceUserExistException();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User login(String login, String password) throws ServiceException {

        User user;

        try {
            user = userDAO.login(login, password);
        } catch (DAOUserNotFoundException e) {
            throw new ServiceUserNotFoundException();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    @Override
    public User getUser(Long id) throws ServiceException {
        User user;
        try {
            user = userDAO.getUser(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception in UserServiceImpl: getUser()", e);
        }
        return user;
    }

    @Override
    public UserDetail getUserDetail(Long id) throws ServiceException {
        UserDetail userDetail;
        try {
            userDetail = userDAO.getUserDetail(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception in UserServiceImpl: getUserDetail()", e);
        }
        return userDetail;
    }

    @Override
    public List<User> getAllUsers() throws ServiceException {
        List<User> users;

        try {
            users = userDAO.getAllUsers();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return users;
    }
}
