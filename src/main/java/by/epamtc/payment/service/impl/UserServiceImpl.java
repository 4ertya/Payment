package by.epamtc.payment.service.impl;

import by.epamtc.payment.dao.DAOFactory;
import by.epamtc.payment.dao.UserDAO;
import by.epamtc.payment.dao.exception.DAOException;
import by.epamtc.payment.dao.exception.DAOUserExistException;
import by.epamtc.payment.dao.exception.DAOUserNotFoundException;
import by.epamtc.payment.entity.*;
import by.epamtc.payment.service.UserService;
import by.epamtc.payment.service.exception.ServiceException;
import by.epamtc.payment.service.exception.ServiceUserExistException;
import by.epamtc.payment.service.exception.ServiceUserNotFoundException;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.InputStream;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final DAOFactory daoFactory = DAOFactory.getInstance();
    private final UserDAO userDAO = daoFactory.getUserDAO();

    @Override
    public void registration(RegistrationData registrationData) throws ServiceException, ServiceUserExistException {

        try {
            registrationData.setPassword(DigestUtils.md5Hex(registrationData.getPassword()));
            userDAO.registration(registrationData);
        } catch (DAOUserExistException e) {
            throw new ServiceUserExistException();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User login(AuthorizationData authorizationData) throws ServiceException, ServiceUserNotFoundException {

        User user;

        try {
            authorizationData.setPassword(DigestUtils.md5Hex(authorizationData.getPassword()));
            user = userDAO.login(authorizationData);
        } catch (DAOUserNotFoundException e) {
            throw new ServiceUserNotFoundException();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    @Override
    public UserData getUserData(Long id) throws ServiceException {
        UserData userData;
        try {
            userData = userDAO.getUserData(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception in UserServiceImpl: getUser()", e);
        }
        return userData;
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
    public List<UserDetail> getAllUserDetails() throws ServiceException {
        List<UserDetail> users;

        try {
            users = userDAO.getAllUserDetails();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return users;
    }

    @Override
    public void updateUserDetails(UserDetail userDetail) throws ServiceException {
        try {
            userDAO.updateUserDetails(userDetail);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void uploadPassportScan(InputStream inputStream, Long userId) throws ServiceException {
        try {
            userDAO.uploadPassportScan(inputStream, userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public InputStream downloadPassportScan(Long userId) throws ServiceException {
        InputStream scan;
        try {
            scan = userDAO.downloadPassportScan(userId);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return scan;
    }
}
