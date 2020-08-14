package bot.handlers;

import bot.commands.Command;
import bot.commands.Debug;
import bot.exceptions.InvalidParameterException;
import bot.listeners.MessageListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class DebugHandler implements SettingHandler<Debug> {

    private final MessageListener listener;

    @Override
    public Debug generateCommand(MessageReceivedEvent message, MessageListener listener) {
        String[] commandArgs = Arrays.
                stream(message.getMessage()
                        .getContentRaw()
                        .split(" "))
                .skip(1)
                .toArray(String[]::new);

        if (commandArgs[0].equals("on")) {
            listener.setDebugMode(true);
            Debug debugCommand = new Debug(true);
            debugCommand.setTargetChannel(message.getChannel());
            log.info("Debug mod enabled");
            return debugCommand;
        } else if (commandArgs[0].equals("off")) {
            listener.setDebugMode(false);
            Debug debugCommand = new Debug(false);
            debugCommand.setTargetChannel(message.getChannel());
            log.info("Debug mod disabled");
            return debugCommand;
        } else {
            log.warn("Invalid command parameter");
            throw new InvalidParameterException("Invalid command parameter");
        }
    }

    @Override
    public void execute(Command command) {
        command.getTargetChannel().sendMessage(command.getAnswer()).submit();
    }
}
