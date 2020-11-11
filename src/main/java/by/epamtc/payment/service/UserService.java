package by.epamtc.payment.service;

import by.epamtc.payment.entity.*;
import by.epamtc.payment.service.exception.ServiceException;
import by.epamtc.payment.service.exception.ServiceUserExistException;
import by.epamtc.payment.service.exception.ServiceUserNotFoundException;

import java.io.InputStream;
import java.util.List;

public interface UserService {
    void registration(RegistrationData registrationData) throws ServiceUserExistException, ServiceException;

    User login(AuthorizationData authorizationData) throws ServiceUserNotFoundException, ServiceException;

    UserData getUserData(Long id) throws ServiceException;

    UserDetail getUserDetail(Long id) throws ServiceException;

    List<UserDetail> getAllUserDetails() throws ServiceException;

    void updateUserDetails(UserDetail userDetail) throws ServiceException;

    void uploadPassportScan(InputStream inputStream, Long userId) throws ServiceException;

    InputStream downloadPassportScan(Long userId) throws ServiceException;
}