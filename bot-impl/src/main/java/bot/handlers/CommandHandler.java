package bot.handlers;

import bot.commands.Command;
import bot.listeners.MessageListener;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Interface for command handlers
 */
public interface CommandHandler<T extends Command> {
    /**
     * Generates command based on event and listener
     *
     * @param event    - event received by bot
     * @param listener - current bot listener
     */
    T generateCommand(MessageReceivedEvent event, MessageListener listener);

    /**
     * Executes generated command
     *
     * @param command executable command
     */
    void execute(Command command);
}
