package bot.utils;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * GameLoader loads game modules from specified jar
 */
@Slf4j
@Component
public class GamesLoader {
    private static final String CLASSPATH = "C:/myClasses/GameOfSticks.jar";
    private static final String FILE_EXTENSION = ".class";

    /**
     * Runs specified game
     *
     * @param game name of runnable game
     */
    public void runGame(String game) {
        try {
            URL[] urls = {new URL("jar:file:" + CLASSPATH + "!/")};
            URLClassLoader classLoader = URLClassLoader.newInstance(urls);

            Class<?> clazz = classLoader.loadClass(game);
            Method method = clazz.getMethod("main", String[].class);
            method.invoke(null, (Object) new String[0]);

        } catch (ClassNotFoundException e) {
            log.error("File not found! " + e.getMessage());
        } catch (IOException | NoSuchMethodException e) {
            log.error("IO error while loading class! " + e.getMessage());
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("Error during reflection process! " + e.getMessage());
        }
    }

    /**
     * Create embed with games found in jar
     *
     * @return embed for answer
     */
    public MessageEmbed getGameList() {
        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor("List of games")
                .setDescription("Here is the list of all my games\n");
        List<String> games = scanJar();
        for (String game : games) {
            embed.addField(game, "", true);
        }
        return embed.build();
    }

    private List<String> scanJar() {
        List<String> games = new ArrayList<>();
        try {
            JarFile jarFile = new JarFile(CLASSPATH);
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                if (jarEntry.isDirectory() || !jarEntry.getName().endsWith(FILE_EXTENSION)) {
                    continue;
                }
                String className = jarEntry.getName().substring(0, jarEntry.getName().length() - 6);
                games.add(className);
            }
        } catch (IOException e) {
            log.error("IO error while loading class! " + e.getMessage());
        }
        return games;
    }
}
