package by.epamtc.payment.controller.command;

import by.epamtc.payment.controller.command.impl.admin.BlockAccountCommand;
import by.epamtc.payment.controller.command.impl.admin.UnblockAccountCommand;
import by.epamtc.payment.controller.command.impl.admin.UnblockCardCommand;
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
        commands.put(CommandName.UNBLOCK_CARD, new UnblockCardCommand());
        commands.put(CommandName.BLOCK_ACCOUNT, new BlockAccountCommand());
        commands.put(CommandName.UNBLOCK_ACCOUNT, new UnblockAccountCommand());
    }

    public Command getCommand(String commandName) {
        Command command = null;
        CommandName valueName;

        try {
            valueName = CommandName.valueOf(commandName.toUpperCase());
            command = commands.get(valueName);
        } catch (IllegalArgumentException ignored) {
        }

        return command;
    }

    public static AdminCommandProvider getInstance() {
        return instance;
    }
}

