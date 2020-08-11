package bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class Debug extends SettingCommand {

    public Debug(Boolean mode) {
        EmbedBuilder embed = new EmbedBuilder()
                .setDescription("Debug mode " + mode.toString());
        this.setAnswer(embed.build());
    }
}
