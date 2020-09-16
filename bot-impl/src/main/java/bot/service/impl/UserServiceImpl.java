package bot.service.impl;

import bot.entity.User;
import bot.entity.UserCreateForm;
import bot.repository.UserRepository;
import bot.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * User service implementation.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> getUserById(Long id) {
        log.debug("Getting user={}", id);
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByTag(String tag) {
        log.debug("Getting user by email={}", tag);
        return userRepository.findByTag(tag);
    }

    @Override
    public void update(net.dv8tion.jda.api.entities.User jdaUser) {
        if (Objects.isNull(jdaUser)) {
            log.error("update() - error: user is null");
            return;
        }
        User user = new User();
        user.setTag(jdaUser.getAsTag()).setExperience(2);
        log.debug("update() - update user {}", user);
        userRepository.save(user);
    }

    @Override
    public User create(UserCreateForm form) {
        User user = new User();
       // user.setEmail(form.getEmail());
       // user.setPasswordHash(new BCryptPasswordEncoder().encode(form.getPassword()));
      //  user.setRole(form.getRole());
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        log.debug("Getting all users");
        return userRepository.findAll();
    }
}
