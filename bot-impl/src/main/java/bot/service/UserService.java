package bot.service;

import bot.entity.User;
import bot.entity.UserCreateForm;

import java.util.List;
import java.util.Optional;

/**
 * Service for work with channel.
 */
public interface UserService {

    Optional<User> getUserById(Long id);

    Optional<User> getUserByTag(String tag);

    void update(net.dv8tion.jda.api.entities.User user);

    List<User> getAllUsers();

    User create(UserCreateForm form);

}
