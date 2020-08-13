package bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class About extends InfoCommand {

    public About() {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("Training bot", "https://github.com/D4rkHeaven/DiscordBotSpring")
                .setDescription("Bot for practice in Java");
        this.setAnswer(embed.build());
    }
}
