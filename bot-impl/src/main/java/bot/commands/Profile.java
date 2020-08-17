package bot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class Profile extends InfoCommand {

    public Profile(User user) {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle(user.getName())
                .setThumbnail(user.getEffectiveAvatarUrl())
                .addField("ID", user.getId(), true)
                .addField("Tag", user.getAsTag(), true);
        this.setAnswer(embed.build());
    }
}
