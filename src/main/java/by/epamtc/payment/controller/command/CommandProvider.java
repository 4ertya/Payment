package by.epamtc.payment.controller.command;

import by.epamtc.payment.controller.command.impl.LoginCommand;
import by.epamtc.payment.controller.command.impl.MainPageCommand;
import by.epamtc.payment.controller.command.impl.RegistrationCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {

    private static final CommandProvider instance = new CommandProvider();
    private Map<CommandName, Command> commands = new HashMap<>();

    public CommandProvider() {
        commands.put(CommandName.MAIN_PAGE, new MainPageCommand());
        commands.put(CommandName.LOGIN, new LoginCommand());
        commands.put(CommandName.REGISTRATION, new RegistrationCommand());
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

