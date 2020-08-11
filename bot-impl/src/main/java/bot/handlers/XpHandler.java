package bot.handlers;

import bot.commands.Command;
import bot.commands.Xp;
import bot.listeners.MessageListener;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class XpHandler implements InfoHandler<Xp> {

    MessageListener listener;

    public XpHandler(MessageListener listener) {
        this.listener = listener;
    }

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
