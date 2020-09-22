package by.epamtc.payment.service.impl;

import by.epamtc.payment.dao.DAOException;
import by.epamtc.payment.dao.DAOFactory;
import by.epamtc.payment.dao.UserDAO;
import by.epamtc.payment.entity.User;
import by.epamtc.payment.service.ServiceException;
import by.epamtc.payment.service.UserService;

import javax.servlet.ServletException;
import java.io.IOException;

public class UserServiceImpl implements UserService {
    private final DAOFactory instance = DAOFactory.getInstance();
    private final UserDAO userDAO = instance.getUserDAO();

    @Override
    public void registration(User user) throws ServiceException {
        String login = user.getLogin();

        try {
            if (!userDAO.isUserExist(login)) {
                userDAO.registration(user);
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User login(String login, String password) throws ServiceException {
        User user;
        try {
            user = userDAO.login(login, password);
        } catch (DAOException e) {
            throw new ServiceException();
        }
        return user;
    }

}
