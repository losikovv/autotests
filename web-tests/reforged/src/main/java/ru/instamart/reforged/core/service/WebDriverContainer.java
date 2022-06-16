package ru.instamart.reforged.core.service;

import lombok.extern.slf4j.Slf4j;
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
    private final Map<Long, DriverSession> webDriverMap = new ConcurrentHashMap<>();
    private final AtomicBoolean isCleanStart = new AtomicBoolean(false);

    public DriverSession createOrGetDriver() {
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
                        BrowserFactory.createDriverSession(Browser.getValue(browser), version)
                )
        );
    }

    public void closeDriver() {
        final var driverSession = this.webDriverMap.remove(Thread.currentThread().getId());
        if (nonNull(driverSession) && nonNull(driverSession.getDriver())) {
            driverSession.getDriver().quit();
            Router.cleanPage();
        }
    }

    public boolean isStillAlive() {
        final var driverSession = this.webDriverMap.get(Thread.currentThread().getId());
        return nonNull(driverSession) && nonNull(driverSession.getDriver());
    }

    private DriverSession makeAutoClosable(final Thread thread, final DriverSession driverSession) {
        this.allWebDriverThreads.add(thread);

        if (!this.isCleanStart.get()) {
            synchronized(this) {
                if (!this.isCleanStart.get()) {
                    (new CleanupThread(this.allWebDriverThreads, this.webDriverMap)).start();
                    this.isCleanStart.set(true);
                }
            }
        }

        return driverSession;
    }
}
