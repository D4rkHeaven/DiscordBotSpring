package bot.handlers;

import bot.commands.Command;
import bot.commands.Create;
import bot.exceptions.InvalidParameterException;
import bot.listeners.MessageListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Slf4j
@Component
@RequiredArgsConstructor
public class CreateHandler implements SettingHandler<Create> {

    private final MessageListener listener;

    private Guild targetGuild;
    private String category;
    private String channel;

    @Override
    public Create generateCommand(MessageReceivedEvent message, MessageListener listener) {
        String[] args = Arrays.
                stream(message.getMessage()
                        .getContentRaw()
                        .split(" "))
                .skip(2)
                .map(String::toLowerCase)
                .toArray(String[]::new);
        Create createCommand = new Create();
        createCommand.setTargetChannel(message.getChannel());
        targetGuild = message.getGuild();
        if (args.length == 2) {
            category = args[0];
            channel = args[1];
        } else if (args.length == 1) {
            channel = args[0];
        } else {
            log.warn("Invalid command parameter");
            throw new InvalidParameterException("Invalid command parameter");
        }
        return createCommand;
    }

    @Override
    public void execute(Command command) {
        MessageChannel targetChannel = command.getTargetChannel();
        EmbedBuilder answer = new EmbedBuilder();
        try {
            if (category != null) {
                if (targetGuild.getCategories()
                        .stream()
                        .map(Category::getName)
                        .map(String::toLowerCase)
                        .anyMatch(string -> string.equals(category.toLowerCase()))) {
                    List<Category> categoryList = targetGuild.getCategoriesByName(category, true);
                    createChannel(categoryList.get(0), answer);
                } else {
                    createChannel(targetGuild.createCategory(category).submit().get(), answer);
                }
            } else {
                if (targetGuild.getTextChannels()
                        .stream()
                        .filter(textChannel -> textChannel.getParent() == null)
                        .map(TextChannel::getName)
                        .map(String::toLowerCase)
                        .anyMatch(string -> string.equals(channel.toLowerCase()))) {
                    answer.setDescription("Channel \"" + channel + "\" already exists");
                } else {
                    targetGuild.createTextChannel(channel).submit();
                    answer.setDescription("Created channel " + channel);
                }
            }
        } catch (Exception e) {
            log.warn("Error during creating channel", e);
            answer.setDescription(e.toString());
        } finally {
            if (Objects.nonNull(targetChannel)) {
                MessageAction messageAction = targetChannel.sendMessage(answer.build());
                messageAction.submit();
            }
        }
    }

    /**
     * Try to create channel if it's not exists
     *
     * @param currentCategory category for new channel
     * @param answer          output message
     */
    private void createChannel(Category currentCategory, EmbedBuilder answer) {
        if (currentCategory.getTextChannels()
                .stream()
                .map(TextChannel::getName)
                .map(String::toLowerCase)
                .anyMatch(string -> string.equals(channel.toLowerCase()))) {
            answer.setDescription("Channel \"" + channel + "\" in category \"" + category + "\" already exists");
        } else {
            currentCategory.createTextChannel(channel).submit();
            answer.setDescription("Created channel \"" + channel + "\" in category \"" + category + "\"");
        }
    }
}
