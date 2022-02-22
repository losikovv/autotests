package ru.instamart.reforged.core.service;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.testng.Reporter;
import ru.instamart.reforged.core.config.BrowserProperties;
import ru.instamart.reforged.core.enums.Browser;
import ru.instamart.reforged.core.page.Router;
import ru.instamart.reforged.core.provider.BrowserFactory;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.util.Objects.nonNull;

@Slf4j
public final class WebDriverContainer {

    private final Collection<Thread> allWebDriverThreads = new ConcurrentLinkedQueue<>();
    private final Map<Long, WebDriver> webDriverMap = new ConcurrentHashMap<>();
    private final AtomicBoolean isCleanStart = new AtomicBoolean(false);

    public WebDriver createOrGetDriver() {
        //Get browser from suite parameter or from -Pbrowser
        final String browser = Optional.ofNullable(
                Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("browser")
        ).orElse(BrowserProperties.BROWSER);
        //Get version from suite parameter or from -Pversion
        final String version = Optional.ofNullable(
                Reporter.getCurrentTestResult().getTestContext().getCurrentXmlTest().getParameter("version")
        ).orElse(BrowserProperties.BROWSER_VERSION);

        return this.webDriverMap.computeIfAbsent(Thread.currentThread().getId(), threadId ->
                this.makeAutoClosable(
                        Thread.currentThread(),
                        BrowserFactory.createBrowserInstance(Browser.getValue(browser), version)
                )
        );
    }

    public void closeDriver() {
        final WebDriver webDriver = this.webDriverMap.remove(Thread.currentThread().getId());
        if (nonNull(webDriver)) {
            webDriver.quit();
            Router.cleanPage();
        }
    }

    public boolean isStillAlive() {
        return nonNull(webDriverMap.get(Thread.currentThread().getId()));
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
