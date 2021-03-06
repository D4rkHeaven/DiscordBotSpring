package bot.handlers;

import bot.commands.About;
import bot.commands.Command;
import bot.listeners.MessageListener;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * {@link AboutHandler} tests.
 */
@SpringBootTest
public class AboutHandlerTest {

    @Mock
    private MessageReceivedEvent messageReceivedEvent;

    @InjectMocks
    private AboutHandler aboutHandler;

    @Test
    public void testGenerateCommand() {
        MessageListener messageListener = mock(MessageListener.class);

        About result = aboutHandler.generateCommand(messageReceivedEvent, messageListener);

        verify(messageReceivedEvent).getChannel();
        assertNotNull(result);
    }

    @Test
    public void testExecute() {
        Command command = mock(Command.class);
        MessageChannel targetChannel = mock(MessageChannel.class);
        MessageEmbed answer = mock(MessageEmbed.class);
        MessageAction action = mock(MessageAction.class);
        when(command.getTargetChannel()).thenReturn(targetChannel);
        when(command.getAnswer()).thenReturn(answer);
        when(targetChannel.sendMessage(answer)).thenReturn(action);

        aboutHandler.execute(command);

        verify(command).getTargetChannel();
        verify(command).getAnswer();
        verify(targetChannel).sendMessage(answer);
        verify(action).submit();
    }

    @Test
    public void testExecuteWhenChannelIsNull() {
        Command command = mock(Command.class);
        MessageEmbed answer = mock(MessageEmbed.class);
        when(command.getAnswer()).thenReturn(answer);

        aboutHandler.execute(command);

        verify(command).getTargetChannel();
        verify(command).getAnswer();
    }

    @Test
    public void testExecuteWhenAnswerIsNull() {
        Command command = mock(Command.class);
        MessageChannel targetChannel = mock(MessageChannel.class);
        when(command.getTargetChannel()).thenReturn(targetChannel);

        aboutHandler.execute(command);

        verify(command).getTargetChannel();
        verify(command).getAnswer();
    }
}
