package bot.handlers;

import bot.exceptions.InvalidParameterException;
import bot.listeners.MessageListener;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * {@link CreateHandler} tests.
 */
@SpringBootTest
public class CreateHandlerTest {

    @Mock
    private MessageReceivedEvent messageReceivedEvent;

    @InjectMocks
    private CreateHandler createHandler;

    @Test
    public void testGenerateCommandWhenInvalidCommandParameter() {
        MessageListener messageListener = mock(MessageListener.class);
        Message message = mock(Message.class);
        when(messageReceivedEvent.getMessage()).thenReturn(message);
        when(message.getContentRaw()).thenReturn("create channel");

        Exception exception = assertThrows(InvalidParameterException.class, () -> {
            createHandler.generateCommand(messageReceivedEvent, messageListener);
        });

        verify(messageReceivedEvent).getChannel();
        verify(messageReceivedEvent).getGuild();
        assertTrue(exception.getMessage().contains("Invalid command parameter"));
    }

    @Test
    public void testGenerateCommand() {
        MessageListener messageListener = mock(MessageListener.class);
        Message message = mock(Message.class);
        when(messageReceivedEvent.getMessage()).thenReturn(message);
        when(message.getContentRaw()).thenReturn("create channel test test");

        assertDoesNotThrow(() -> {
            createHandler.generateCommand(messageReceivedEvent, messageListener);
        });

        verify(messageReceivedEvent).getChannel();
        verify(messageReceivedEvent).getGuild();
    }
}

