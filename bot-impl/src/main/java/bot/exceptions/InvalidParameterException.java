package bot.exceptions;

/**
 * This exception thrown when the user enters an invalid parameter
 */
public class InvalidParameterException extends DiscordBotException {
    public InvalidParameterException(String message) {
        super(message);
    }
}
