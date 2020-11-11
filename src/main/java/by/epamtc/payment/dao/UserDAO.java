package by.epamtc.payment.dao;

import by.epamtc.payment.dao.exception.DAOException;
import by.epamtc.payment.dao.exception.DAOUserExistException;
import by.epamtc.payment.dao.exception.DAOUserNotFoundException;
import by.epamtc.payment.entity.*;

import java.io.InputStream;
import java.util.List;

public interface UserDAO {

    void registration(RegistrationData registrationData) throws DAOException, DAOUserExistException;

    User login(AuthorizationData authorizationData) throws DAOUserNotFoundException, DAOException;

    UserData getUserData(Long id) throws DAOException;

    UserDetail getUserDetail(Long id) throws DAOException;

    List<UserDetail> getAllUserDetails() throws DAOException;

    void updateUserDetails(UserDetail userDetail) throws DAOException;

    void uploadPassportScan(InputStream inputStream, long userId) throws DAOException;

    InputStream downloadPassportScan(Long userId) throws DAOException;
}
