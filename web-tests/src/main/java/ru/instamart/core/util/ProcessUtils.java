package instamart.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public final class ProcessUtils {

    private static final Logger log = LoggerFactory.getLogger(ProcessUtils.class);

    private ProcessUtils() {}

    public static void processKiller(String name){
        try {
            String[] COMPOSED_COMMAND = {
                    "/bin/bash",
                    "-c",
                    "/usr/bin/pgrep -f \""+name+"\" | xargs kill",};
            // Process p = Runtime.getRuntime().exec(COMPOSED_COMMAND);
            Runtime.getRuntime().exec(COMPOSED_COMMAND);
            log.warn("Процесс с PID: {} завершен принудительно", name);
        } catch (IOException e) {
            log.error("Failed when try to kill process {}", name);
        }
    }

    /**
     * Метод удаляет процесс по PID пока не нужно, но может пригодится
     */
    public static void processKillerByPID(String pid){
        String command = "kill -9 "+ pid;
        try {
            Runtime.getRuntime().exec(command);
            log.warn("Процесс с PID: {} завершен принудительно", pid);
        } catch (IOException e) {
            log.error("Failed when try to kill process {}", pid);
        }
    }

    /**
     * Метод поиска PID у процесса
     * @param args массив объектов ProcessHandle
     * @param browserName Имя объекта для поиска
     * @return возвращает PID
     */
    public static Long pidFinder(Object [] args, String browserName){
        Long pid;
        for (String arg:(String[]) args[0]){
            if (arg.contains("webdriver")){
                pid=ProcessHandle
                        .allProcesses()
                        .filter(p -> p.info().commandLine().map(c -> c.contains(browserName))
                                .orElse(false)).findFirst().get().pid();
                log.warn("Найден старый инстанс браузера запущенный Selenium с PID: {}", pid);
                return pid;
            }
        }
        return null;
    }
}
