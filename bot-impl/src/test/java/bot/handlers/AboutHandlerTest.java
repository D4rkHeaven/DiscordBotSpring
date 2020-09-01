package bot.handlers;

import bot.commands.About;
import bot.listeners.MessageListener;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * {@link AboutHandler} tests.
 */
@SpringBootTest
public class AboutHandlerTest {

    private final About VALID_ABOUT = new About();
    private MessageListener messageListener;

    @Mock
    private MessageReceivedEvent messageReceivedEvent;

    @InjectMocks
    private AboutHandler aboutHandler;


    @Test
    public void testGenerateCommand() {
        About result = aboutHandler.generateCommand(messageReceivedEvent, messageListener);

        verify(messageReceivedEvent).getChannel();
        assertEquals(VALID_ABOUT, result);
    }

   /* @Test
    public void testExecute() {
       when(command.getTargetChannel().sendMessage(command.getAnswer()).submit()).thenReturn(null);

        aboutHandler.execute(VALID_COMMAND);

        verify(command,times(1)).getTargetChannel();
    }*/
}
