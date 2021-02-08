package instamart.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public final class ProcessUtils {

    private static final Logger log = LoggerFactory.getLogger(ProcessUtils.class);

    private ProcessUtils() {}

    /**
     * Этот метод чистииит окружение после тестов и перед ними
     * удаляет процессы с запущенными браузерами, если используется докер, то скипается
     * Удаляет только инстансы созданные селениумом, нормальный браузер не убивается
     * Метод отлажен на MACOS и 100% не будет работать на линухе, или винде
     * @param name имя браузера
     */
    public static void cleanProcessByName(final String name) {
        switch (name) {
            case "chrome":
                closeProcess("chromedriver", "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome");
                break;
            case "firefox" :
                closeProcess("geckodriver", "Firefox");
                break;
        }
    }

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
    public static void processKillerByPID(final String pid) {
        final String command = "kill -9 "+ pid;
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
    public static Long pidFinder(final Object [] args, final String browserName) {
        Long pid;
        for (String arg:(String[]) args[0]) {
            if (arg.contains("webdriver")) {
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

    private static void closeProcess(final String driver, final String instance) {
        final long amount = ProcessHandle.allProcesses()
                .filter(p -> p.info().commandLine().map(c -> c.contains(driver))
                        .orElse(false)).count();
        final long amountBro = ProcessHandle.allProcesses()
                .filter(p -> p.info().commandLine().map(c -> c.contains(instance))
                        .orElse(false)).count();
        //Если есть запущенные chromedriver, то убиваем их
        if (amount > 0) {
            ProcessUtils.processKiller(driver);
        }
        //если есть запущенные Инстансы Селениума, то и их киляем
        if (amountBro > 0) {
            ProcessUtils.processKiller("Firefox");
        }
    }

}
