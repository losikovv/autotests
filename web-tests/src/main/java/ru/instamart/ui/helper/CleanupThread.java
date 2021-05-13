package ru.instamart.ui.helper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

import java.util.Collection;
import java.util.Map;

import static java.util.Objects.isNull;

@Slf4j
public final class CleanupThread extends Thread {

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
        if (isNull(webDriver)) {
            log.info("No WebDriver found for thread : {}  - nothing to close", thread.getId());
        } else {
            webDriver.quit();
            log.info("Close WebDriver for threadId : {}", thread.getId());
        }
    }

    @RequiredArgsConstructor
    private final class FinalClean extends Thread {

        private final Thread thread;

        @Override
        public void run() {
            log.error("Cleanup all browsers");
            closeWebDriver(thread);
        }
    }
}
