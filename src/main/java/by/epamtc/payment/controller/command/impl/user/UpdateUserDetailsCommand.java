package by.epamtc.payment.controller.command.impl.user;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.controller.validator.UserTechnicalValidator;
import by.epamtc.payment.entity.Role;
import by.epamtc.payment.entity.Status;
import by.epamtc.payment.entity.User;
import by.epamtc.payment.entity.UserDetail;
import by.epamtc.payment.service.ServiceFactory;
import by.epamtc.payment.service.UserService;
import by.epamtc.payment.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UpdateUserDetailsCommand implements Command {
    private final static Logger log = LogManager.getLogger(UpdateUserDetailsCommand.class);

    private final static String WARNING_MESSAGE = "warning_message";
    private final static String STORED_SUCCESSFUL = "data_successful_stored";
    private final static String ERROR = "error";
    private final static String INCORRECT_DATA = "incorrect_data";

    private final static String USER_ID_PARAMETER = "user_id";
    private final static String RU_NAME_PARAMETER = "ru_name";
    private final static String RU_SURNAME_PARAMETER = "ru_surname";
    private final static String EN_NAME_PARAMETER = "en_name";
    private final static String EN_SURNAME_PARAMETER = "en_surname";
    private final static String GENDER_PARAMETER = "gender";
    private final static String PASSPORT_SERIES_PARAMETER = "passport_series";
    private final static String PASSPORT_NUMBER_PARAMETER = "passport_number";
    private final static String PHONE_NUMBER_PARAMETER = "phone_number";
    private final static String LOCATION_PARAMETER = "location";
    private final static String STATUS = "user_status";
    private final static String ROLE = "user_role";


    private final static String GO_TO_SETTING_PAGE = "UserController?command=to_settings_page";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Role role=null;
        Status status=null;

        long id = 0;

        try {
            id = Long.parseLong(request.getParameter(USER_ID_PARAMETER));
        } catch (NumberFormatException ignored) {
        }

        if (id < 0) {
            id = 0;
        }


        String ruName = request.getParameter(RU_NAME_PARAMETER);
        String ruSurname = request.getParameter(RU_SURNAME_PARAMETER);
        String enName = request.getParameter(EN_NAME_PARAMETER);
        String enSurname = request.getParameter(EN_SURNAME_PARAMETER);
        String gender = request.getParameter(GENDER_PARAMETER);
        String passportSeries = request.getParameter(PASSPORT_SERIES_PARAMETER);
        String statusStr = request.getParameter(STATUS);
        String roleStr = request.getParameter(ROLE);

        if (statusStr!=null) {
            status = Status.valueOf(request.getParameter(STATUS));
        }
        if (roleStr!=null) {
            role = Role.valueOf(request.getParameter(ROLE));
        }


        int passportNumber = 0;

        try {
            passportNumber = Integer.parseInt(request.getParameter(PASSPORT_NUMBER_PARAMETER));
        } catch (NumberFormatException ignored) {
        }

        if (passportNumber < 0) {
            passportNumber = 0;
        }

        if (user.getRole() != Role.ADMIN) {
            role = user.getRole();
            status = Status.WAITING;
        }

        String phoneNumber = request.getParameter(PHONE_NUMBER_PARAMETER);
        String location = request.getParameter(LOCATION_PARAMETER);

        UserDetail userDetail = new UserDetail();

        userDetail.setId(id);
        userDetail.setRuName(ruName);
        userDetail.setRuSurname(ruSurname);
        userDetail.setEnName(enName);
        userDetail.setEnSurname(enSurname);
        userDetail.setGender(gender);
        userDetail.setPassportSeries(passportSeries);
        userDetail.setPassportNumber(passportNumber);
        userDetail.setPhoneNumber(phoneNumber);
        userDetail.setLocation(location);
        userDetail.setRole(role);
        userDetail.setStatus(status);

        if (UserTechnicalValidator.userDetailValidation(userDetail)) {
            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            UserService service = serviceFactory.getUserService();

            try {
                service.updateUserDetails(userDetail);

                session.setAttribute(WARNING_MESSAGE, STORED_SUCCESSFUL);

                log.info("Data is saved.\n" + userDetail);
                response.sendRedirect(GO_TO_SETTING_PAGE);

            } catch (ServiceException e) {
                session.setAttribute(WARNING_MESSAGE, ERROR);

                log.error("Couldn't save", e);

                response.sendRedirect(GO_TO_SETTING_PAGE);
            }
        } else {
            log.info("User details validation failed. Incorrect Data");

            session.setAttribute(WARNING_MESSAGE, INCORRECT_DATA);

            response.sendRedirect(GO_TO_SETTING_PAGE);
        }
    }
}
