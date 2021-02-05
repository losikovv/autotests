package instamart.core.service;

import instamart.core.factory.BrowserFactory;
import instamart.core.helpers.CleanupThread;
import org.openqa.selenium.WebDriver;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public final class WebDriverService {

    private final Collection<Thread> allWebDriverThreads = new ConcurrentLinkedQueue<>();
    private final AtomicBoolean isCleanStart = new AtomicBoolean(false);
    private final Map<Long, WebDriver> webDriverMap = new ConcurrentHashMap<>();

    public WebDriver createOrGetDriver() {
        return this.webDriverMap.computeIfAbsent(Thread.currentThread().getId(), threadId ->
                this.makeAutoClosable(Thread.currentThread(), BrowserFactory.createBrowserInstance("chrome_local", "latest"))
        );
    }

    public void closeDriver() {
        final WebDriver webDriver = this.webDriverMap.remove(Thread.currentThread().getId());
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    private WebDriver makeAutoClosable(final Thread thread, final WebDriver webDriver) {
        this.allWebDriverThreads.add(thread);

        if (!this.isCleanStart.get()) {
            synchronized(this) {
                if (!this.isCleanStart.get()) {
                    (new CleanupThread(this.allWebDriverThreads, this.webDriverMap)).start();
                    this.isCleanStart.set(true);
                }
            }
        }

        return webDriver;
    }
}
