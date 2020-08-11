package bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class Create extends SettingCommand {

    public Create() {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Created channel.");
        this.setAnswer(embed.build());
    }
}
