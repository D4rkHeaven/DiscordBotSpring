package bot.service.impl;

import bot.handlers.AboutHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.GuildChannel;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * {@link ChannelServiceImpl} tests.
 */
@SpringBootTest
public class ChannelServiceImplTest {

    private final String VALID_ID = "731154486176907289";
    @Mock
    private JDA jda;

    @InjectMocks
    private ChannelServiceImpl channelServiceImpl;

    @BeforeTestClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetChannelsWhenIdIsNull() {
        List<GuildChannel> result = channelServiceImpl.getChannels(null);

        assertEquals(List.of(), result);
    }

    @Test
    public void testGetChannels() {
        List<GuildChannel> result = channelServiceImpl.getChannels(VALID_ID);

        assertNotNull(result);
    }
}
