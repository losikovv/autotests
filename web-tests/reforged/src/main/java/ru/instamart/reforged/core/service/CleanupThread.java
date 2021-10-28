package ru.instamart.reforged.core.service;

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
        this.setName("WebDriver Cleanup");
        Runtime.getRuntime().addShutdownHook(new FinalClean());
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
                        log.debug("Thread {} is dead. Clean WebDriver {}", thread.getId(), this.threadWebDriver.get(thread.getId()));
                        this.closeWebDriver(thread);
                    }
                });
    }

    private void closeWebDriver(final Thread thread) {
        this.allWebDriverThreads.remove(thread);
        final WebDriver webDriver = this.threadWebDriver.remove(thread.getId());
        if (isNull(webDriver)) {
            log.debug("No WebDriver found for thread : {}  - nothing to close", thread.getId());
        } else {
            webDriver.quit();
            log.debug("Close WebDriver for threadId : {}", thread.getId());
        }
    }

    private void closeAllDriver() {
        this.allWebDriverThreads.forEach(thread -> {
            final WebDriver webDriver = this.threadWebDriver.remove(thread.getId());
            if (isNull(webDriver)) {
                log.debug("No WebDriver found for thread : {}  - nothing to close", thread.getId());
            } else {
                webDriver.quit();
                log.debug("Close WebDriver for threadId : {}", thread.getId());
            }
        });
    }

    private class FinalClean extends Thread {
        @Override
        public void run() {
            log.debug("Cleanup all browsers");
            closeAllDriver();
        }
    }
}
