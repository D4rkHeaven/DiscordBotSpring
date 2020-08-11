package bot.utils;

import bot.listeners.MessageListener;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Deletes messages containing bad words
 */
@Component
@Slf4j
public class CensorshipFilter {

    private final Set<String> badWords;

    public CensorshipFilter() {
        badWords = new HashSet<>();
        getBadWords();
    }

    /**
     * Clear past messages after bot startup
     *
     * @param event bot startup
     */
    public void censoring(ReadyEvent event) {
        TextChannel textChannel = event.getJDA().getTextChannelById(MessageListener.getCHAT_ID());
        StringBuilder censor = new StringBuilder();
        assert textChannel != null;
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

    /**
     * Checks string for bad words
     *
     * @param message input string
     * @return boolean result
     */
    public boolean hasBadWords(final String message) {
        return badWords.stream().anyMatch(message::contains);
    }

    /**
     * Parse bad words from properties file
     */
    private void getBadWords() {
        try (InputStreamReader stream = new InputStreamReader(new FileInputStream("bot-impl/src/main/resources/application.properties"), StandardCharsets.UTF_8)) {
            Properties property = new Properties();
            property.load(stream);
            String[] words = property.getProperty("censor").split(",");
            for (String word : words) {
                if (!word.isEmpty())
                    badWords.add(word);
            }
        } catch (Exception e) {
            log.warn("Censorship filter disabled. Bad words list not found", e);
        }
    }
}
