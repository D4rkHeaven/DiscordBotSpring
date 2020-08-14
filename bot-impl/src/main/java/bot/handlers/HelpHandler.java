package bot.handlers;

import bot.commands.Command;
import bot.commands.Help;
import bot.listeners.MessageListener;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HelpHandler implements InfoHandler<Help> {

    private final MessageListener listener;

    @Override
    public Help generateCommand(MessageReceivedEvent message, MessageListener listener) {
        Help helpCommand = new Help();
        helpCommand.setTargetChannel(message.getChannel());
        return helpCommand;
    }

    @Override
    public void execute(Command command) {
        command.getTargetChannel().sendMessage(command.getAnswer()).submit();
    }
}
