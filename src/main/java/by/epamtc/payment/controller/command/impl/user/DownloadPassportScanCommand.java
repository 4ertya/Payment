package by.epamtc.payment.controller.command.impl.user;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.service.ServiceFactory;
import by.epamtc.payment.service.UserService;
import by.epamtc.payment.service.exception.ServiceException;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

public class DownloadPassportScanCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService service = serviceFactory.getUserService();

        long id = Long.parseLong(request.getParameter("user_id"));
        byte[] buf = null;
        try {
            InputStream scan = service.downloadPassportScan(id);
            buf = IOUtils.toByteArray(scan);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        response.setContentType("image/jpeg");
        response.getOutputStream().write(buf);
    }
}
