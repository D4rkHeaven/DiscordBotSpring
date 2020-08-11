package bot.handlers;

import bot.commands.Command;
import bot.commands.SettingCommand;
import bot.listeners.MessageListener;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Interface for setting command handlers
 *
 * @param <T> setting command
 */
public interface SettingHandler<T extends SettingCommand> extends CommandHandler<SettingCommand> {

    @Override
    T generateCommand(MessageReceivedEvent message, MessageListener listener);

    @Override
    void execute(Command command);
}
