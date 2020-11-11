package by.epamtc.payment.controller.command.impl.user;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.controller.command.impl.admin.UnblockCardCommand;
import by.epamtc.payment.controller.validator.AccountTechnicalValidator;
import by.epamtc.payment.entity.Currency;
import by.epamtc.payment.entity.Status;
import by.epamtc.payment.entity.User;
import by.epamtc.payment.service.AccountService;
import by.epamtc.payment.service.ServiceFactory;
import by.epamtc.payment.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * --------------------DONE-----------------------
 */

public class CreateNewAccountCommand implements Command {

    private final static Logger log = LogManager.getLogger(UnblockCardCommand.class);

    private final static ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final static AccountService accountService = serviceFactory.getAccountService();

    private final static String PREVIOUS_REQUEST = "previous_request";
    private final static String USER_ATTRIBUTE = "user";
    private final static String CURRENCY_PARAMETER = "currency";
    private final static String WARNING_MESSAGE = "warning_message";
    private final static String MESSAGE = "account_created";
    private final static String INVALID_DATA = "invalid_data";
    private final static String ERROR = "error";
    private final static String ADD_PERSONAL_DATA = "add_data";
    private final static String WAIT_VERIFICATION = "wait";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String previousRequest = (String) request.getSession().getAttribute(PREVIOUS_REQUEST);

        User user;
        Currency currency = null;
        String currencyName;

        currencyName = request.getParameter(CURRENCY_PARAMETER);
        user = (User) request.getSession().getAttribute(USER_ATTRIBUTE);

        if (user.getStatus() == Status.NEW) {
            request.getSession().setAttribute(WARNING_MESSAGE, ADD_PERSONAL_DATA);
            response.sendRedirect("UserController?command=to_settings_page");
        }

        if (user.getStatus() == Status.WAITING) {
            request.getSession().setAttribute(WARNING_MESSAGE, WAIT_VERIFICATION);
            response.sendRedirect(previousRequest);
        }

        if (user.getStatus() == Status.VERIFIED) {

            if (currencyName != null) {

                try {
                    currency = Currency.valueOf(request.getParameter("currency").toUpperCase());
                } catch (IllegalArgumentException ignored) {
                }

            }

            if (AccountTechnicalValidator.crateNewAccountValidation(user, currency)) {

                try {

                    accountService.createNewAccount(user, currency);
                    request.getSession().setAttribute(WARNING_MESSAGE, MESSAGE);
                    response.sendRedirect("UserController?command=to_user_accounts_page");

                } catch (ServiceException e) {
                    log.error("Exception in CreateNewAccountCommand", e);
                    request.getSession().setAttribute(WARNING_MESSAGE, ERROR);
                    response.sendRedirect(previousRequest);
                }

            } else {
                request.getSession().setAttribute(WARNING_MESSAGE, INVALID_DATA);
                response.sendRedirect(previousRequest);
            }
        }
    }
}
