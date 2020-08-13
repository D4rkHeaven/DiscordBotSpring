package bot.listeners;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

/**
 * Listens voice channels events
 */

@Slf4j
@Component
@NoArgsConstructor
public class VoiceChannelListener extends ListenerAdapter implements Runnable {

    @Getter
    private static final long GUILD_ID = 731154486176907289L;

    VoiceChannel voiceChannel;
    private Category dynamicVoiceChannels;

    public VoiceChannelListener(VoiceChannel voiceChannel) {
        this.voiceChannel = voiceChannel;
    }

    /**
     * Checks past events after bot startup
     *
     * @param event bot startup
     */
    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        try {
            super.onReady(event);
            Guild guild = event.getJDA().getGuildById(GUILD_ID);
            assert guild != null;
            Optional<Category> optionalCategory = guild.getCategories()
                    .stream()
                    .filter(isDynamicCategoryExist())
                    .findAny();
            dynamicVoiceChannels = optionalCategory.orElseGet(() -> {
                try {
                    return guild.createCategory("Dynamic (d)").submit().get();
                } catch (InterruptedException | ExecutionException e) {
                    log.warn("Exception: ", e);
                }
                return null;
            });
            createVoiceChannel();
        } catch (NullPointerException e) {
            log.warn("Exception: ", e);
        }
    }

    @Override
    public void onGuildVoiceJoin(@Nonnull GuildVoiceJoinEvent event) {
        createVoiceChannel();
    }

    @Override
    public void onGuildVoiceLeave(@Nonnull GuildVoiceLeaveEvent event) {
        Runnable voiceChannelListener = new VoiceChannelListener(event.getChannelLeft());
        Thread thread = new Thread(voiceChannelListener);
        thread.start();
    }

    /**
     * Closes voice channel after 1 minute if all members left
     */
    @Override
    public void run() {
        Runnable task = () -> {
            try {
                TimeUnit.MINUTES.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (voiceChannel.getMembers().size() == 0) voiceChannel.delete().submit();
        };
        Thread closeTask = new Thread(task);
        closeTask.start();
    }

    @NotNull
    private Predicate<Category> isDynamicCategoryExist() {
        return category -> category.getName().toLowerCase().equals("dynamic (d)");
    }

    /**
     * Creates voice channel if guild category doesn't have empty one
     */
    private void createVoiceChannel() {
        assert dynamicVoiceChannels != null;
        if (dynamicVoiceChannels.getVoiceChannels().stream().noneMatch(voiceChannel -> voiceChannel.getMembers().size() == 0)) {
            dynamicVoiceChannels.createVoiceChannel("Channel").submit();
        }
    }
}
