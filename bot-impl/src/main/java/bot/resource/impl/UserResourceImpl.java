package bot.resource.impl;

import bot.dto.UserDto;
import bot.resource.UserResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * User resource implementation.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class UserResourceImpl implements UserResource {

    private final JDA jda;
    private final MapperFacade mapperFacade;

    @Override
    public UserDto getUser(String userId) {
        log.info("getUser() - start, userId = {}", userId);
        User user = jda.getUserById(userId);
        if (Objects.nonNull(user)) {
            UserDto userDto = mapperFacade.map(user, UserDto.class);
            log.info("getUser() - end, userDto = {}", userDto);
            return userDto;
        }
        log.info("getUser() - end");
        return null;
    }
}
