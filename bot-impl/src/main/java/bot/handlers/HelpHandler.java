package bot.handlers;

import bot.commands.Command;
import bot.commands.Help;
import bot.listeners.MessageListener;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

@Component
public class HelpHandler implements InfoHandler<Help> {

    MessageListener listener;

    public HelpHandler(MessageListener listener) {
        this.listener = listener;
    }

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
