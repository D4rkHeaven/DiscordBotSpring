package bot.handlers;

import bot.commands.Command;
import bot.commands.Xp;
import bot.listeners.MessageListener;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.springframework.stereotype.Component;

import java.util.Objects;

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
        MessageChannel targetChannel = command.getTargetChannel();
        MessageEmbed answer = command.getAnswer();
        if (Objects.nonNull(targetChannel) & Objects.nonNull(answer)) {
            MessageAction messageAction = targetChannel.sendMessage(answer);
            messageAction.submit();
        }
    }
}
