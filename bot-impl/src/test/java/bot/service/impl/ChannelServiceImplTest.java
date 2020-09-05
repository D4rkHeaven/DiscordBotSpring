package bot.service.impl;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import net.dv8tion.jda.api.requests.restaction.ChannelAction;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * {@link ChannelServiceImpl} tests.
 */
@SpringBootTest
public class ChannelServiceImplTest {

    @Mock
    private JDA jda;

    @InjectMocks
    private ChannelServiceImpl channelServiceImpl;

    @Test
    public void testCreateChannelText() {
        Guild guild = mock(Guild.class);
        ChannelAction textChannel = mock(ChannelAction.class);
        GuildChannel mockChannel = mock(GuildChannel.class);
        when(guild.createVoiceChannel(anyString())).thenReturn(textChannel);
        when(textChannel.complete()).thenReturn(mockChannel);

        GuildChannel guildChannel = channelServiceImpl.createChannel(guild, "test", ChannelType.TEXT);

        assertNotNull(guildChannel);
    }

    @Test
    public void testDeleteChannel() {
        Guild guild = mock(Guild.class);
        GuildChannel guildChannel = mock(GuildChannel.class);
        AuditableRestAction auditableRestAction = mock(AuditableRestAction.class);
        when(guild.getGuildChannelById(anyLong())).thenReturn(guildChannel);
        when(guildChannel.delete()).thenReturn(auditableRestAction);

        channelServiceImpl.deleteChannel(guild, anyLong());

        verify(guildChannel).delete();
    }

    @Test
    public void testDeleteChannelWhenGuildChannelIsNull() {
        Guild guild = mock(Guild.class);
        GuildChannel guildChannel = mock(GuildChannel.class);

        channelServiceImpl.deleteChannel(guild, anyLong());

        verifyNoInteractions(guildChannel);
    }

    @Test
    public void testGetChannels() {
        Guild guild = mock(Guild.class);
        when(jda.getGuildById(anyString())).thenReturn(guild);

        List<GuildChannel> result = channelServiceImpl.getChannels(anyString());

        verify(guild).getChannels();
        assertNotNull(result);
    }

    @Test
    public void testGetChannelsWhenGuildIsNull() {
        List<GuildChannel> result = channelServiceImpl.getChannels(anyString());

        assertNotNull(result);
    }

    @Test
    public void testGetChannelsWhenIdIsNull() {
        List<GuildChannel> result = channelServiceImpl.getChannels(null);

        assertEquals(List.of(), result);
    }
}
