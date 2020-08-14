package bot.handlers;

import bot.commands.Command;
import bot.commands.Xp;
import bot.listeners.MessageListener;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class XpHandler implements InfoHandler<Xp> {

    private final MessageListener listener;

    @Override
    public Xp generateCommand(MessageReceivedEvent message, MessageListener listener) {
        Xp xpCommand = new Xp(listener.expSystem.getUserXp(message.getAuthor()));
        xpCommand.setTargetChannel(message.getChannel());
        return xpCommand;
    }

    @Override
    public void execute(Command command) {
        command.getTargetChannel().sendMessage(command.getAnswer()).submit();
    }
}
