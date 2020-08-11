package bot.handlers;

import bot.commands.About;
import bot.commands.Command;
import bot.listeners.MessageListener;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

@Component
public class AboutHandler implements InfoHandler<About> {

    MessageListener listener;

    public AboutHandler(MessageListener listener) {
        this.listener = listener;
    }

    @Override
    public About generateCommand(MessageReceivedEvent message, MessageListener listener) {
        About aboutCommand = new About();
        aboutCommand.setTargetChannel(message.getChannel());
        return aboutCommand;
    }

    @Override
    public void execute(Command command) {
        command.getTargetChannel().sendMessage(command.getAnswer()).submit();
    }
}
