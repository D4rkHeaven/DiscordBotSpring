package bot.exceptions;

/**
 * Superclass of training bot exceptions
 */
public class DiscordBotException extends RuntimeException {
    public DiscordBotException(String message) {
        super(message);
    }
}
