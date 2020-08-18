package bot.utils;

import bot.listeners.MessageListener;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Deletes messages containing bad words
 */
@Slf4j
@Component
public class CensorshipFilter {

    private static final long CHAT_ID = 732199841819787315L;

    @Value("${bot.censor}")
    private List<String> badWords;

    /**
     * Clear past messages after bot startup
     *
     * @param event bot startup
     */
    public void censoring(ReadyEvent event) {
        TextChannel textChannel = event.getJDA().getTextChannelById(CHAT_ID);
        StringBuilder censor = new StringBuilder();
        if (textChannel != null) {
            Map<User, List<Message>> userListMap = textChannel
                    .getIterableHistory().stream()
                    .takeWhile(message -> message.getAuthor().getIdLong() != MessageListener.getBOT_ID())
                    .filter(message -> hasBadWords(message.getContentRaw().toLowerCase()))
                    .collect(Collectors.groupingBy(Message::getAuthor));
            userListMap.forEach((user, messages) -> {
                messages.forEach(message -> message.delete().submit());
                censor.append("Deleted ").append(messages.size()).append(" messages from ").append(user.getAsTag()).append("\n");
            });
            if (censor.length() != 0) {
                textChannel.sendMessage(censor).submit();
            }
        }
    }

    /**
     * Checks string for bad words
     *
     * @param message input string
     * @return boolean result
     */
    public boolean hasBadWords(final String message) {
        return badWords.stream().anyMatch(message::contains);
    }
}
