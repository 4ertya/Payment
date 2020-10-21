package by.epamtc.payment.controller;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.controller.command.MainCommandProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainController extends HttpServlet {

    private final static String COMMAND_NAME = "command";
    private final MainCommandProvider provider = MainCommandProvider.getInstance();

    public MainController() {
        super();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String currentCommand;
        Command command;
        currentCommand = req.getParameter(COMMAND_NAME);
        command = provider.getCommand(currentCommand);

        command.execute(req, resp);


//        StringBuilder stringBuffer = new StringBuilder();
//        Map map = req.getParameterMap();
//        for (Object key: map.keySet())
//        {
//            String keyStr = (String)key;
//            stringBuffer.append(keyStr);
//            stringBuffer.append("=");
//            String[] value = (String[])map.get(keyStr);
//            for (String val:value){
//                stringBuffer.append(val);
//            }
//            stringBuffer.append("&");
//        }

//        String parameters = stringBuffer.toString();
//        HttpSession session=req.getSession();
//        session.setAttribute("prev_request_params", parameters);

    }
}
