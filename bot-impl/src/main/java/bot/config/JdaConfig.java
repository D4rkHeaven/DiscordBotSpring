package bot.config;

import bot.repository.UserRepository;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.security.auth.login.LoginException;
import java.util.List;

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = {"bot.entity"})
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class JdaConfig {

    @Value("${bot.token}")
    private String token;

    @Bean
    public JDA jda(List<ListenerAdapter> listenerAdapters) throws LoginException {
        JDA jda = JDABuilder.createDefault(token)
                .setActivity(Activity.listening("!help"))
                .build();
        jda.addEventListener(listenerAdapters.toArray());
        return jda;
    }
}
