package ru.instamart.ui.service;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.testng.Reporter;
import ru.instamart.ui.factory.BrowserFactory;
import ru.instamart.ui.helper.CleanupThread;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.util.Objects.nonNull;
import static ru.instamart.ui.config.BrowserProperties.BROWSER_VERSION;
import static ru.instamart.ui.config.BrowserProperties.BROWSER;

@Slf4j
public final class WebDriverService {

    private final Collection<Thread> allWebDriverThreads = new ConcurrentLinkedQueue<>();
    private final Map<Long, WebDriver> webDriverMap = new ConcurrentHashMap<>();
    private final AtomicBoolean isCleanStart = new AtomicBoolean(false);

    public WebDriver createOrGetDriver() {
        //Get browser from suite parameter
        final Optional<String> browser = Optional.ofNullable(
                Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("browser")
        );
        //Get version from suite parameter
        final Optional<String> version = Optional.ofNullable(
                Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("version")
        );
        return this.webDriverMap.computeIfAbsent(Thread.currentThread().getId(), threadId ->
                this.makeAutoClosable(
                        Thread.currentThread(),
                        //Create browser. Get browser from suite parameter or from -Pbrowser
                        BrowserFactory.createBrowserInstance(browser.orElse(BROWSER), version.orElse(BROWSER_VERSION))
                )
        );
    }

    public void closeDriver() {
        final WebDriver webDriver = this.webDriverMap.remove(Thread.currentThread().getId());
        if (nonNull(webDriver)) {
            webDriver.quit();
        }
    }

    public boolean isStillAlive(final WebDriver webDriver) {
        try {
            webDriver.getTitle();
            return true;
        }
        catch (UnreachableBrowserException e) {
            log.error("Browser is unreachable");
            return false;
        }
        catch (NoSuchWindowException e) {
            log.error( "Browser window is not found");
            return false;
        }
        catch (NoSuchSessionException e) {
            log.error("Browser session is not found");
            return false;
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
