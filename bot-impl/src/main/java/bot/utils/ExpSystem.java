package bot.utils;

import bot.listeners.MessageListener;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ExpSystem extends ListenerAdapter {
    HashMap<User, Integer> userXp = new HashMap<>();
    HashMap<User, Integer> userTime = new HashMap<>();
    @Setter
    private Integer expTimer;

   // private ObjectMapper mapper;
    private File json;

    //TODO сделать инициализацию мап (в конструкторе?), чтобы не выкидывалось NPE при запуске без новых сообщений
    public ExpSystem() {
        json = new File("src/main/resources/exp.json");
     //   mapper = new ObjectMapper();
        loadTime();
     //   loadJson();
    }

    public int getUserXp(User user) {

        return userXp.get(user);
    }

    public void setUserXp(User user, int xp) {
        log.info("User {} have {} xp", user, xp);
        userXp.put(user, xp);
        //  mapper.writeValue(json, userXp);
    }

    public int getUserTime(User user) {
        return userTime.get(user);
    }

    public void setUserTime(User user, int time) {
        userTime.put(user, time);
    }

    /**
     * Update xp for user, xp = n^3, n - number of messages
     *
     * @param user user who updates his xp
     */
    public void updateXp(User user) {
        setUserXp(user, (int) Math.pow(Math.pow(Math.E, Math.log(getUserXp(user)) / 3) + 1, 3));
        setUserTime(user, expTimer);
        startTimer();
    }

    public boolean canGetXp(User user) {
        return !userTime.containsKey(user);
    }

    /**
     * Starts timer after receiving xp
     */
    public void startTimer() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                for (User user : userTime.keySet()) {
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
        TextChannel textChannel = event.getJDA().getTextChannelById(MessageListener.getCHAT_ID());
        assert textChannel != null;
        Map<User, List<Message>> userXpMap = textChannel
                .getIterableHistory().stream()
                .takeWhile(message -> message.getAuthor().getIdLong() != MessageListener.getBOT_ID())
                .collect(Collectors.groupingBy(Message::getAuthor));
        userXpMap.forEach((user, messages) -> {
            setUserXp(user, (int) Math.pow(messages.size(), 3));
            if (getUserXp(user) != 0)
                textChannel.sendMessage("User " + user.getName() + " have " + getUserXp(user) + " xp.").submit();
        });
    }

    private void loadTime() {
        try (FileInputStream file = new FileInputStream("bot-impl/src/main/resources/application.properties")) {
            Properties property = new Properties();
            property.load(file);
            expTimer = Integer.parseInt(property.getProperty("exp.timer"));
        } catch (IOException e) {
            log.error("Properties file not found!");
            e.printStackTrace();
        }
    }

   /* private void loadJson() {
        try {
            //   SimpleModule simpleModule = new SimpleModule();
            //   simpleModule.addKeyDeserializer(User.class, new UserKeyDeserializer());
            //   mapper.registerModule(simpleModule);
            userXp = mapper.readValue(json, new TypeReference<>() {
            });
            log.info(String.valueOf(userXp));
        } catch (IOException e) {
            log.warn("Exception: ", e);
        }
    }*/
}
