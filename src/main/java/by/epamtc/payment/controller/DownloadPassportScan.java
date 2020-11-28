package by.epamtc.payment.controller;

import by.epamtc.payment.controller.command.impl.user.UploadPassportScanCommand;
import by.epamtc.payment.entity.User;
import by.epamtc.payment.service.ServiceFactory;
import by.epamtc.payment.service.UserService;
import by.epamtc.payment.service.exception.ServiceException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class DownloadPassportScan extends HttpServlet {
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final UserService service = serviceFactory.getUserService();
    private final static Logger log = LogManager.getLogger(UploadPassportScanCommand.class);
    private final static String USER_ATTRIBUTE = "user";
    private final static String WARNING_MESSAGE = "warning_message";
    private final static String ERROR = "error";
    private final static String GO_TO_SETTING_PAGE = "UserController?command=to_settings_page";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("user_id"));
        byte[] buf = null;
        try {
            InputStream scan = service.downloadPassportScan(id);
            buf = IOUtils.toByteArray(scan);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        resp.setContentType("image/jpeg");
        resp.getOutputStream().write(buf);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(USER_ATTRIBUTE);
        InputStream inputStream = null;

        if (ServletFileUpload.isMultipartContent(req)) {
            try {
                List<FileItem> multiParts = new ServletFileUpload(
                        new DiskFileItemFactory()).parseRequest(req);

                for (FileItem item : multiParts) {
                    if (!item.isFormField()) {
                        inputStream = item.getInputStream();
                    }
                }
                service.uploadPassportScan(inputStream, user.getId());

            } catch (FileUploadException e) {
                log.error("Couldn't upload file", e);
                req.getSession().setAttribute(WARNING_MESSAGE, ERROR);
            } catch (ServiceException e) {
                req.getSession().setAttribute(WARNING_MESSAGE, ERROR);
            }

        } else {
            log.error("Couldn't upload file. Isn't Multipart Content");
            req.getSession().setAttribute(WARNING_MESSAGE, ERROR);
        }

        resp.sendRedirect(GO_TO_SETTING_PAGE);
        ;
    }
}
