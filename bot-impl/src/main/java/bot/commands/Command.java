package bot.commands;

import lombok.Data;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Superclass of training bot commands
 */
@Data
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public abstract class Command {
    protected MessageEmbed answer;
    protected MessageChannel targetChannel;
}
