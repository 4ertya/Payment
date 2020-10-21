package by.epamtc.payment.controller.command;

import by.epamtc.payment.controller.command.impl.*;
import by.epamtc.payment.controller.command.impl.go_to_page.*;

import java.util.HashMap;
import java.util.Map;

public class AdminCommandProvider {
    private static final AdminCommandProvider instance = new AdminCommandProvider();
    private final Map<CommandName, Command> commands = new HashMap<>();

    public AdminCommandProvider() {
        commands.put(CommandName.TO_ALL_CARDS_PAGE, new GoToAllCardsPageCommand());
        commands.put(CommandName.TO_ALL_ACCOUNTS_PAGE, new GoToAllAccountsPageCommand());
        commands.put(CommandName.TO_ALL_USERS_PAGE, new GoToAllUsersPageCommand());
    }

    public Command getCommand(String commandName) {
        Command command;
        CommandName valueName;

        valueName = CommandName.valueOf(commandName.toUpperCase());
        command = commands.get(valueName);

        return command;
    }

    public static AdminCommandProvider getInstance() {
        return instance;
    }
}

