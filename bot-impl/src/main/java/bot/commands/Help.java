package bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class Help extends InfoCommand {
    public Help() {
        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor("Training bot", "https://github.com/D4rkHeaven/DiscordBot")
                .setDescription("Here is the list of all my commands\n")
                .addField("Info", "`!help` `!about` `!profile` `!xp`", true)
                .addField("Settings", "`!debug [on/off]` `!create channel <category_name> <channel_name>`", true);
        this.setAnswer(embed.build());
    }
}
