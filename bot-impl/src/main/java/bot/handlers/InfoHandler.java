package bot.handlers;

import bot.commands.Command;
import bot.commands.InfoCommand;
import bot.listeners.MessageListener;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Interface for info command handlers
 *
 * @param <T> info command
 */
public interface InfoHandler<T extends InfoCommand> extends CommandHandler<InfoCommand> {

    @Override
    T generateCommand(MessageReceivedEvent message, MessageListener listener);

    @Override
    void execute(Command command);
}
