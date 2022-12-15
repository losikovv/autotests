package ru.instamart.reforged.core.driver;

import lombok.extern.slf4j.Slf4j;
import ru.instamart.reforged.core.config.BrowserProperties;
import ru.instamart.reforged.core.enums.Browser;
import ru.instamart.reforged.core.page.Router;
import ru.instamart.reforged.core.provider.BrowserFactory;

import java.util.Collection;
import java.util.Map;
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
        return this.webDriverMap.computeIfAbsent(Thread.currentThread().getId(), threadId ->
                this.makeAutoClosable(
                        Thread.currentThread(),
                        BrowserFactory.createDriverSession(
                                Browser.getValue(BrowserProperties.BROWSER),
                                BrowserProperties.BROWSER_VERSION
                        )
                )
        );
    }

    public void closeDriver() {
        final var driverSession = this.webDriverMap.remove(Thread.currentThread().getId());
        if (nonNull(driverSession) && nonNull(driverSession.getDriver())) {
            log.debug("Remove cdp session={} for thread_id={}", driverSession.getDevTools().getCdpSession(), Thread.currentThread().getId());
            driverSession.getDevTools().close();
            log.debug("Close cdp connection");
            driverSession.getDriver().quit();
            log.debug("Quit driver");
            Router.cleanPage();
            log.debug("Clean page");
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
