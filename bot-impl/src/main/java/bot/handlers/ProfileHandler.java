package bot.handlers;

import bot.commands.Command;
import bot.commands.Profile;
import bot.listeners.MessageListener;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileHandler implements InfoHandler<Profile> {

    private final MessageListener listener;

    @Override
    public Profile generateCommand(MessageReceivedEvent message, MessageListener listener) {
        User author = message.getAuthor();
        Profile profileCommand = new Profile(author);
        profileCommand.setTargetChannel(message.getChannel());
        return profileCommand;
    }

    @Override
    public void execute(Command command) {
        command.getTargetChannel().sendMessage(command.getAnswer()).submit();
    }
}

