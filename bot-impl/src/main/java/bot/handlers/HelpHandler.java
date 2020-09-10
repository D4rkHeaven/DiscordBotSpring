package bot.handlers;

import bot.commands.Command;
import bot.commands.Help;
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
        MessageChannel targetChannel = command.getTargetChannel();
        MessageEmbed answer = command.getAnswer();
        if (Objects.nonNull(targetChannel) & Objects.nonNull(answer)) {
            MessageAction messageAction = targetChannel.sendMessage(answer);
            messageAction.submit();
        }
    }
}
