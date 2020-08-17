package bot.exceptions;

/**
 * Unsupported type of channel exception.
 */
public class UnsupportedTypeOfChannelException extends DiscordBotException {
    public UnsupportedTypeOfChannelException(String message) {
        super(message);
    }
}
