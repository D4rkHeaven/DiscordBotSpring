package bot.service.impl;

import bot.exceptions.UnsupportedTypeOfChannelException;
import bot.service.ChannelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.requests.restaction.ChannelAction;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Channel service implementation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final JDA jda;

    @Override
    public GuildChannel createChannel(Guild guild, String name, ChannelType channelType) throws UnsupportedTypeOfChannelException {
        return createChannel(guild, null, name, channelType);
    }

    @Override
    public GuildChannel createChannel(Category category, String name, ChannelType channelType) throws UnsupportedTypeOfChannelException {
        return createChannel(category.getGuild(), category, name, channelType);
    }

    private GuildChannel createChannel(Guild guild, Category category, String name, ChannelType channelType) throws UnsupportedTypeOfChannelException {
        if (channelType.equals(ChannelType.TEXT)) {
            ChannelAction<VoiceChannel> textChannel;
            if (Objects.isNull(category)) {
                textChannel = guild.createVoiceChannel(name);
            } else {
                textChannel = category.createVoiceChannel(name);
            }
            return textChannel.complete();
        } else if (channelType.equals(ChannelType.VOICE)) {
            ChannelAction<VoiceChannel> voiceChannel;
            if (Objects.isNull(category)) {
                voiceChannel = guild.createVoiceChannel(name);
            } else {
                voiceChannel = category.createVoiceChannel(name);
            }
            return voiceChannel.complete();
        } else {
            throw new UnsupportedTypeOfChannelException(String.format("Error while creating a channel. Unsupported type - %s", channelType));
        }
    }

    @Override
    public void deleteChannel(Guild guild, Long id) {
        GuildChannel guildChannelById = guild.getGuildChannelById(id);
        if (Objects.nonNull(guildChannelById)) {
            guildChannelById.delete().complete();
        }
    }

    @Override
    public List<GuildChannel> getChannels(String guildId) {
        Guild guildById = jda.getGuildById(guildId);
        List<GuildChannel> channels = List.of();
        if (Objects.nonNull(guildById)) {
            channels = new ArrayList<>(guildById.getChannels());
        }
        return channels;
    }
}
