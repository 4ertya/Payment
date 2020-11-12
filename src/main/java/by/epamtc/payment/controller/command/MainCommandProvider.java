package by.epamtc.payment.controller.command;

import by.epamtc.payment.controller.command.impl.*;
import by.epamtc.payment.controller.command.impl.go_to_page.*;

import java.util.HashMap;
import java.util.Map;

public class MainCommandProvider {

    private static final MainCommandProvider instance = new MainCommandProvider();
    private final Map<CommandName, Command> commands = new HashMap<>();

    public MainCommandProvider() {
        commands.put(CommandName.TO_MAIN_PAGE, new GoToMainPageCommand());
        commands.put(CommandName.TO_LOGIN_PAGE, new GoToLoginPageCommand());
        commands.put(CommandName.TO_REGISTRATION_PAGE, new GoToRegistrationPageCommand());
        commands.put(CommandName.TO_ABOUT_PAGE, new GoToAboutPageCommand());

        commands.put(CommandName.LOGIN, new LoginCommand());
        commands.put(CommandName.REGISTRATION, new RegistrationCommand());
        commands.put(CommandName.CHANGE_LANGUAGE, new ChangeLanguageCommand());
    }

    public Command getCommand(String commandName) {
        Command command = null;
        CommandName valueName;

        if (commandName != null) {

            try {
                valueName = CommandName.valueOf(commandName.toUpperCase());
                command = commands.get(valueName);
            } catch (IllegalArgumentException ignored) {
            }

        }

        return command;
    }

    public static MainCommandProvider getInstance() {
        return instance;
    }
}

