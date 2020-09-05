package bot.handlers;

import bot.commands.About;
import bot.commands.Command;
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
public class AboutHandler implements InfoHandler<About> {

    private final MessageListener listener;

    @Override
    public About generateCommand(MessageReceivedEvent message, MessageListener listener) {
        About aboutCommand = new About();
        aboutCommand.setTargetChannel(message.getChannel());
        return aboutCommand;
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
