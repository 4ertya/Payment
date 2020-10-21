package by.epamtc.payment.dao;

import by.epamtc.payment.dao.exception.DAOException;
import by.epamtc.payment.dao.exception.DAOUserExistException;
import by.epamtc.payment.dao.exception.DAOUserNotFoundException;
import by.epamtc.payment.entity.*;

import java.util.List;

public interface UserDAO {

    void registration(RegistrationData registrationData) throws DAOException, DAOUserExistException;

    User login(AuthorisationData authorisationData) throws DAOUserNotFoundException, DAOException;

    UserData getUserData(Long id) throws DAOException;

    UserDetail getUserDetail(Long id) throws DAOException;

    List<UserData> getAllUsers() throws DAOException;

}
