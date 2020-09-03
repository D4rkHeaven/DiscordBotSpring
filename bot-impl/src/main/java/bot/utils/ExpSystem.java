package bot.utils;

import bot.listeners.MessageListener;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ExpSystem {

    private static final long CHAT_ID = 732199841819787315L;
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final File json = new File("bot-impl/src/main/resources/exp.json");

    private HashMap<String, Integer> userXp = new HashMap<>();
    private HashMap<String, Integer> userTime = new HashMap<>();
    @Value("${exp.timer}")
    private Integer expTimer;

    @PostConstruct
    public void init() {
        loadJson();
    }

    public int getUserXp(User user) {
        try {
            if (Objects.isNull(userXp.get(user.getAsTag()))) {
                userXp.put(user.getAsTag(), 0);
                mapper.writeValue(json, userXp);
            }
        } catch (IOException e) {
            log.warn("Exception: ", e);
        }
        return userXp.get(user.getAsTag());

    }

    public void setUserXp(User user, int xp) {
        try {
            log.info("User {} have {} xp", user, xp);
            userXp.put(user.getAsTag(), xp);
            mapper.writeValue(json, userXp);
        } catch (IOException e) {
            log.warn("Exception: ", e);
        }

    }

    public int getUserTime(String user) {
        if (Objects.isNull(userTime.get(user))) {
            userTime.put(user, expTimer);
        }
        return userTime.get(user);
    }

    public void setUserTime(String user, int time) {
        userTime.put(user, time);
    }

    /**
     * Update xp for user, xp = n^3, n - number of messages
     *
     * @param user user who updates his xp
     */
    public void updateXp(User user) {

        setUserXp(user, (int) Math.pow(Math.pow(Math.E, Math.log(getUserXp(user)) / 3) + 1, 3));
        setUserTime(user.getAsTag(), expTimer);
        startTimer();
    }

    public boolean canGetXp(User user) {
        return !userTime.containsKey(user.getAsTag());
    }

    /**
     * Starts timer after receiving xp
     */
    public void startTimer() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                for (String user : userTime.keySet()) {
                    setUserTime(user, getUserTime(user) - 1);
                    if (getUserTime(user) == 0)
                        userTime.remove(user);
                }
            }
        };
        timer.schedule(task, 1000, 1000);
    }

    /**
     * Gives xp to users based on messages before bot startup
     *
     * @param event new event before bot startup
     */
    public void getXpAfterStartup(ReadyEvent event) {
        TextChannel textChannel = event.getJDA().getTextChannelById(CHAT_ID);
        if (textChannel != null) {
            Map<User, List<Message>> userXpMap = textChannel
                    .getIterableHistory().stream()
                    .takeWhile(message -> message.getAuthor().getIdLong() != MessageListener.getBOT_ID())
                    .collect(Collectors.groupingBy(Message::getAuthor));
            userXpMap.forEach((user, messages) -> setUserXp(user, (int) Math.pow(Math.pow(Math.E, Math.log(getUserXp(user)) / 3) + messages.size(), 3)));
        }
    }

    private void loadJson() {
        try {
            if (!json.exists()) {
                if (json.createNewFile()) {
                    log.info("Created new json file for user experience");
                    mapper.writeValue(json, new HashMap<String, Integer>());
                }
            }
            userXp = mapper.readValue(json, new TypeReference<>() {
            });
        } catch (IOException e) {
            log.warn("Exception: ", e);
        }
    }
}
