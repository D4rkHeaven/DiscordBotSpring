package bot.handlers;

import bot.commands.Profile;
import bot.listeners.MessageListener;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

/**
 * {@link ProfileHandler} tests.
 */
@SpringBootTest
public class ProfileHandlerTest {

    private final Profile VALID_PROFILE = new Profile(null);
    private MessageListener messageListener;

    @Mock
    private MessageReceivedEvent messageReceivedEvent;

    @InjectMocks
    private ProfileHandler profileHandler;

    @Test
    public void testGenerateCommand() {
        Profile result = profileHandler.generateCommand(messageReceivedEvent, messageListener);

        verify(messageReceivedEvent).getChannel();
        verify(messageReceivedEvent).getAuthor();
        assertEquals(VALID_PROFILE, result);
    }
}
