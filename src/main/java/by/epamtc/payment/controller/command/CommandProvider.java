package by.epamtc.payment.controller.command;

import by.epamtc.payment.controller.command.impl.*;
import by.epamtc.payment.controller.command.impl._goto.GoToLoginPageCommand;
import by.epamtc.payment.controller.command.impl._goto.GoToMainPageCommand;
import by.epamtc.payment.controller.command.impl._goto.GoToRegistrationPageCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {

    private static final CommandProvider instance = new CommandProvider();
    private Map<CommandName, Command> commands = new HashMap<>();

    public CommandProvider() {
        commands.put(CommandName.TO_MAIN_PAGE, new GoToMainPageCommand());
        commands.put(CommandName.LOGIN, new LoginCommand());
        commands.put(CommandName.REGISTRATION, new RegistrationCommand());
        commands.put(CommandName.TO_LOGIN_PAGE, new GoToLoginPageCommand());
        commands.put(CommandName.TO_REGISTRATION_PAGE,new GoToRegistrationPageCommand());
        commands.put(CommandName.LOGOUT, new LogoutCommand());
        commands.put(CommandName.CHANGE_LANGUAGE, new ChangeLanguageCommand());
    }

    public Command getCommand(String commandName) {
        Command command;
        CommandName valueName;

        valueName = CommandName.valueOf(commandName.toUpperCase());
        command = commands.get(valueName);

        return command;
    }

    public static CommandProvider getInstance() {
        return instance;
    }
}

