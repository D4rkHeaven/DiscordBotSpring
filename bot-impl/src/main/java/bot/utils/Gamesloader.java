package bot.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@Slf4j
@Component
public class Gamesloader {
    private static final String CLASSPATH = "C:/myClasses/GameOfSticks.jar";
    private static final String FILE_EXTENSION = ".class";

    public void run() {
        try {
            JarFile jarFile = new JarFile(CLASSPATH);
            Enumeration<JarEntry> entries = jarFile.entries();

            URL[] urls = {new URL("jar:file:" + CLASSPATH + "!/")};
            URLClassLoader classLoader = URLClassLoader.newInstance(urls);

            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                if (jarEntry.isDirectory() || !jarEntry.getName().endsWith(FILE_EXTENSION)) {
                    continue;
                }
                String className = jarEntry.getName().substring(0, jarEntry.getName().length() - 6);
                className = className.replace('/', '.');
                if (className.equals("SticksGame")) {
                    Class<?> clazz = classLoader.loadClass(className);
                    Method method = clazz.getMethod("main", String[].class);
                    method.invoke(null, (Object) new String[0]);
                }
                log.info("Class {}{} loaded!", className, FILE_EXTENSION);
            }
        } catch (ClassNotFoundException e) {
            log.error("File not found! " + e.getMessage());
        } catch (IOException | NoSuchMethodException e) {
            log.error("IO error while loading class! " + e.getMessage());
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("Error during reflection process! " + e.getMessage());
        }
    }
}
