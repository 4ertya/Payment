package by.epamtc.payment.controller.command;

import by.epamtc.payment.controller.command.impl.*;
import by.epamtc.payment.controller.command.impl.go_to_page.*;

import java.util.HashMap;
import java.util.Map;

public class UserCommandProvider {
    private static final UserCommandProvider instance = new UserCommandProvider();
    private final Map<CommandName, Command> commands = new HashMap<>();

    public UserCommandProvider() {

        commands.put(CommandName.TO_USER_PAGE, new GoToUserPageCommand());
        commands.put(CommandName.TO_USER_CARDS_PAGE, new GoToUserCardsPageCommand());
        commands.put(CommandName.TO_CARD_INFO_PAGE, new GoToCardInfoPageCommand());
        commands.put(CommandName.TO_SETTING_PAGE, new GoToSettingPageCommand());
        commands.put(CommandName.TO_CARD_TRANSFER_PAGE, new GoToCardTransferPageCommand());
        commands.put(CommandName.TO_USER_ACCOUNTS_PAGE, new GoToAccountsPageCommand());
        commands.put(CommandName.TO_CREATE_NEW_CARD_PAGE, new GoToCreateNewCardPageCommand());

        commands.put(CommandName.CHANGE_CARD_STATUS, new UnblockCardCommand());
        commands.put(CommandName.LOGOUT, new LogoutCommand());
        commands.put(CommandName.TRANSFER, new TransferCommand());
        commands.put(CommandName.CREATE_NEW_ACCOUNT, new CreateNewAccountCommand());
        commands.put(CommandName.CREATE_NEW_CARD, new CreateNewCardCommand());
    }

    public Command getCommand(String commandName) {
        Command command;
        CommandName valueName;

        valueName = CommandName.valueOf(commandName.toUpperCase());
        command = commands.get(valueName);

        return command;
    }

    public static UserCommandProvider getInstance() {
        return instance;
    }
}

