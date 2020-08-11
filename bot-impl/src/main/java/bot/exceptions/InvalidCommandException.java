package bot.exceptions;

/**
 * This exception thrown when the user enters an invalid command
 */
public class InvalidCommandException extends DiscordBotException {
    public InvalidCommandException(String message) {
        super(message);
    }
}
