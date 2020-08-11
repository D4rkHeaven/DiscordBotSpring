package bot.commands;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Superclass of info commands
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public abstract class InfoCommand extends Command {
}
