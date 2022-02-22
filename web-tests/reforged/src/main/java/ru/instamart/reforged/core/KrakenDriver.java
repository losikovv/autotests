package ru.instamart.reforged.core;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import ru.instamart.reforged.core.service.WebDriverContainer;

@Slf4j
class KrakenDriver {

    private static final WebDriverContainer CONTAINER = new WebDriverContainer();

    public static void refresh() {
        log.debug("Обновление страницы");
        getWebDriver().navigate().refresh();
    }

    public static void back() {
        log.debug("Вернуться на страницу назад");
        getWebDriver().navigate().back();
    }

    public static void forward() {
        log.debug("Перейти на страницу вперед");
        getWebDriver().navigate().forward();
    }

    public static String title() {
        log.debug("Получить заголовок страницы");
        return getWebDriver().getTitle();
    }

    public static String getSource() {
        log.debug("Получить исходники страницы");
        return getWebDriver().getPageSource();
    }

    public static SessionId getSessionId() {
        log.debug("Получить id сессии");
        return ((RemoteWebDriver)getWebDriver()).getSessionId();
    }

    public static WebDriver getWebDriver() {
        return CONTAINER.createOrGetDriver();
    }

    public static boolean isAlive() {
        return CONTAINER.isStillAlive();
    }

    public static void closeWebDriver() {
        CONTAINER.closeDriver();
    }
}
