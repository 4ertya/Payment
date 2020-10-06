package by.epamtc.payment.controller.command;

import by.epamtc.payment.controller.command.impl.*;
import by.epamtc.payment.controller.command.impl._goto.*;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {

    private static final CommandProvider instance = new CommandProvider();
    private final Map<CommandName, Command> commands = new HashMap<>();

    public CommandProvider() {
        commands.put(CommandName.TO_MAIN_PAGE, new GoToMainPageCommand());
        commands.put(CommandName.TO_LOGIN_PAGE, new GoToLoginPageCommand());
        commands.put(CommandName.TO_REGISTRATION_PAGE, new GoToRegistrationPageCommand());
        commands.put(CommandName.TO_SETTING_PAGE, new GoToSettingPageCommand());
        commands.put(CommandName.TO_USER_PAGE, new GoToUserPageCommand());
        commands.put(CommandName.TO_CARDS_PAGE, new GoToCardsPageCommand());

        commands.put(CommandName.LOGIN, new LoginCommand());
        commands.put(CommandName.REGISTRATION, new RegistrationCommand());
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

