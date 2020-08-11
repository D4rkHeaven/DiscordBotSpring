package bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class Xp extends InfoCommand {

    public Xp() {
        EmbedBuilder embed = new EmbedBuilder()
                .setDescription("Can't calculate your xp.");
        this.setAnswer(embed.build());
    }

    public Xp(int xp) {
        EmbedBuilder embed = new EmbedBuilder()
                .setDescription("You have " + xp + " xp.");
        this.setAnswer(embed.build());
    }
}
