package bot.handlers;

import bot.commands.Command;
import bot.commands.Game;
import bot.exceptions.InvalidParameterException;
import bot.utils.Gamesloader;
import bot.listeners.MessageListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Slf4j
@Component
@RequiredArgsConstructor
public class GameHandler implements SettingHandler<Game> {

    private final MessageListener listener;

    private Gamesloader gamesloader = new Gamesloader();

    @Override
    public Game generateCommand(MessageReceivedEvent message, MessageListener listener) {
        String[] args = Arrays.
                stream(message.getMessage()
                        .getContentRaw()
                        .split(" "))
                .skip(1)
                .map(String::toLowerCase)
                .toArray(String[]::new);
        Game gameCommand = new Game();
        gameCommand.setTargetChannel(message.getChannel());
        if (args.length == 1) {
            //
        } else if (args.length == 0) {
            gamesloader.run();
        } else {
            log.warn("Invalid command parameter");
            throw new InvalidParameterException("Invalid command parameter");
        }
        return gameCommand;
    }

    @Override
    public void execute(Command command) {
        command.getTargetChannel().sendMessage(command.getAnswer()).submit();
    }
}
