package by.epamtc.payment.controller.command.impl.user;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.entity.User;
import by.epamtc.payment.service.ServiceFactory;
import by.epamtc.payment.service.UserService;
import by.epamtc.payment.service.exception.ServiceException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class UploadPassportScanCommand implements Command {
    private final static Logger log = LogManager.getLogger(UploadPassportScanCommand.class);
    private final static String USER_ATTRIBUTE = "user";
    private final static String WARNING_MESSAGE = "warning_message";
    private final static String ERROR = "error";
    private final static String GO_TO_SETTING_PAGE = "UserController?command=to_settings_page";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService service = serviceFactory.getUserService();
        User user = (User) request.getSession().getAttribute(USER_ATTRIBUTE);
        InputStream inputStream=null;

        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                List<FileItem> multiParts = new ServletFileUpload(
                        new DiskFileItemFactory()).parseRequest(request);

                for (FileItem item : multiParts) {
                    if (!item.isFormField()) {
                        inputStream = item.getInputStream();
                    }
                }
                service.uploadPassportScan(inputStream, user.getId());

            } catch (FileUploadException e) {
                log.error("Couldn't upload file",e);
                request.getSession().setAttribute(WARNING_MESSAGE, ERROR);
            } catch (ServiceException e) {
                request.getSession().setAttribute(WARNING_MESSAGE, ERROR);
            }

        } else {
            log.error("Couldn't upload file. Isn't Multipart Content");
            request.getSession().setAttribute(WARNING_MESSAGE, ERROR);
        }

        response.sendRedirect(GO_TO_SETTING_PAGE);;
    }
}
