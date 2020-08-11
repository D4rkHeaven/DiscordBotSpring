package bot.commands;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Superclass of setting commands
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public abstract class SettingCommand extends Command {
}
