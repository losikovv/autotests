package instamart.core.helpers;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;

public final class CleanupThread extends Thread {

    private static final Logger log = LoggerFactory.getLogger(CleanupThread.class);

    private final Collection<Thread> allWebDriverThreads;
    private final Map<Long, WebDriver> threadWebDriver;

    public CleanupThread(final Collection<Thread> allWebDriverThreads, final Map<Long, WebDriver> threadWebDriver) {
        this.allWebDriverThreads = allWebDriverThreads;
        this.threadWebDriver = threadWebDriver;
        this.setDaemon(true);
        this.setName("WebDriver Cleanupper");
        Runtime.getRuntime().addShutdownHook(new FinalClean(currentThread()));
    }

    public void run() {
        while(true) {
            this.closeUnusedWebDrivers();
            try {
                Thread.sleep(100L);
            } catch (InterruptedException var2) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    private void closeUnusedWebDrivers() {
        this.allWebDriverThreads
                .forEach(thread -> {
                    if (!thread.isAlive()) {
                        log.info("Thread {} is dead. Clean WebDriver {}", thread.getId(), this.threadWebDriver.get(thread.getId()));
                        this.closeWebDriver(thread);
                    }
                });
    }

    private void closeWebDriver(final Thread thread) {
        this.allWebDriverThreads.remove(thread);
        final WebDriver webDriver = this.threadWebDriver.remove(thread.getId());
        if (webDriver == null) {
            log.info("No WebDriver found for thread : {}  - nothing to close", thread.getId());
        } else {
            webDriver.quit();
            log.info("Close WebDriver for threadId : {}", thread.getId());
        }
    }

    private class FinalClean extends Thread {

        private final Thread thread;

        public FinalClean(final Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            log.error("Cleanup all browsers");
            closeWebDriver(thread);
        }
    }
}
