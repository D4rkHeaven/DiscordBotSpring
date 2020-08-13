package bot.listeners;

import bot.BotUser;
import bot.utils.CensorshipFilter;
import bot.utils.ExpSystem;
import bot.utils.Filter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Listens message channels events
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MessageListener extends ListenerAdapter {
    @Getter
    private static final long BOT_ID = 732151740379693076L;
    @Getter
    private static final long CHAT_ID = 732199841819787315L;
    @Setter
    private boolean debugMode;

    final public ExpSystem expSystem;
    final private CensorshipFilter censorshipFilter;
    private Filter filter;

    @Autowired
    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    /**
     * Checks past events after bot startup
     *
     * @param event new events before bot startup
     */
    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        super.onReady(event);
        censorshipFilter.censoring(event);
        expSystem.getXpAfterStartup(event);
    }

    /**
     * Triggers after receiving new message
     *
     * @param event new message
     */
    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        log.info("Bot received message: {}", event.getMessage().toString());
        try {
            if (censorshipFilter.hasBadWords(event.getMessage().getContentRaw().toLowerCase())) {
                event.getMessage().delete().submit();
                event.getChannel().sendMessage("Message from "
                        + event.getAuthor().getAsTag() + " deleted because it contains bad words.").submit();
            } else if (filter.isCommand(event.getMessage().getContentRaw()))
                filter.execute(event);
            else if (expSystem.canGetXp(event.getAuthor()) & (event.getAuthor().getIdLong() != getBOT_ID())) {
                expSystem.updateXp(event.getAuthor());
            }
        } catch (Exception e) {
            log.warn("Exception: ", e);
            if (debugMode) {
                event.getChannel().sendMessage(e.toString()).submit();
            }
        }
    }
}
