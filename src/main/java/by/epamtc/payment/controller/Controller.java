package by.epamtc.payment.controller;

import by.epamtc.payment.controller.command.Command;
import by.epamtc.payment.controller.command.CommandProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {

    private static final String COMMAND_NAME = "command";
    private final CommandProvider provider = CommandProvider.getInstance();

    public Controller() {
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
    }

}
