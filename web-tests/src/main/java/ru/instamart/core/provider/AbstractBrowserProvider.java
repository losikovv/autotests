package instamart.core.provider;

import instamart.core.settings.Config;
import instamart.core.util.ProcessUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public abstract class AbstractBrowserProvider {

    private static final Logger log = LoggerFactory.getLogger(AbstractBrowserProvider.class);

    protected WebDriver driver;

    public abstract void createDriver();

    protected void createRemoteDriver(final DesiredCapabilities capabilities) {
        try {
            driver = new RemoteWebDriver(
                    URI.create(Config.REMOTE_URL).toURL(),
                    capabilities);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            ((RemoteWebDriver)driver).setFileDetector(new LocalFileDetector());
        } catch (Exception e) {
            log.error("Protocol exception ", e);
        }
    }

    protected void createLocalChromeDriver(final ChromeOptions options) {
        driver = new ChromeDriver(options);
    }

    /**
     * Этот метод чистииит окружение после тестов и перед ними
     * удаляет процессы с запущенными браузерами, если используется докер, то скипается
     * Удаляет только инстансы созданные селениумом, нормальный браузер не убивается
     * Метод отлажен на MACOS и 100% не будет работать на линухе, или винде
     * @param name имя браузера
     */
    public void cleanProcessByName(final String name){
        if (name.equals("chrome")) {
            final String cdriver = "chromedriver";
            final String browserInst = "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome";
            final long amount = ProcessHandle.allProcesses()
                    .filter(p -> p.info().commandLine().map(c -> c.contains(cdriver))
                            .orElse(false)).count();
            final long amountBro = ProcessHandle.allProcesses()
                    .filter(p -> p.info().commandLine().map(c -> c.contains(browserInst))
                            .orElse(false)).count();
            //Если есть запущенные chromedriver, то убиваем их
            if (amount > 0) {
                ProcessUtils.processKiller(cdriver);
            }
            //если есть запущенные Инстансы Селениума, то и их киляем
            if (amountBro > 0) {
                ProcessUtils.processKiller("test-type=webdriver");
            }
        }
        if (name.equals("firefox")) {
            final String gdriver = "geckodriver";
            final String browserInst = "Firefox";
            final long amount = ProcessHandle.allProcesses()
                    .filter(p -> p.info().commandLine().map(c -> c.contains(gdriver))
                            .orElse(false)).count();
            final long amountBro = ProcessHandle.allProcesses()
                    .filter(p -> p.info().commandLine().map(c -> c.contains(browserInst))
                            .orElse(false)).count();
            //Если есть запущенные chromedriver, то убиваем их
            if (amount > 0) {
                ProcessUtils.processKiller(gdriver);
            }
            //если есть запущенные Инстансы Селениума, то и их киляем
            if (amountBro > 0) {
                ProcessUtils.processKiller("Firefox");
            }
        }
    }

    /**
     * Уровни логирования
     * @return
     */
    protected LoggingPreferences getLogPref() {
        final LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.BROWSER, Level.SEVERE);
        logs.enable(LogType.CLIENT, Level.OFF);
        logs.enable(LogType.DRIVER, Level.WARNING);
        logs.enable(LogType.PERFORMANCE, Level.INFO);
        logs.enable(LogType.SERVER, Level.ALL);

        return logs;
    }

    public WebDriver getWebDriver() {
        return driver;
    }
}
