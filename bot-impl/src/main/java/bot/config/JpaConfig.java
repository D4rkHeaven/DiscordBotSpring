package bot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 * JPA Configuration.
 */
@Configuration
public class JpaConfig {

    private static final String PERSISTENCE_UNIT_NAME = "bot-unit";

    @Bean
    public EntityManager entityManager() {
        return Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME).createEntityManager();
    }
}
