package bot.utils;

import bot.commands.Command;
import bot.handlers.*;
import lombok.Getter;

/**
 * Enum with all possible bot commands
 */
@Getter
public enum CommandType {
    HELP("!help", HelpHandler.class),
    ABOUT("!about", AboutHandler.class),
    DEBUG("!debug", DebugHandler.class),
    PROFILE("!profile", ProfileHandler.class),
    CREATE("!create", CreateHandler.class),
    XP("!xp", XpHandler.class),
    GAMES("!games", GameHandler.class);

    public String commandName;
    private Class<? extends CommandHandler<? extends Command>> commandHandler;

    CommandType(String commandName, Class<? extends CommandHandler<? extends Command>> commandHandler) {
        this.commandName = commandName;
        this.commandHandler = commandHandler;
    }
}

