package ru.instamart.reforged.core.page;

import org.openqa.selenium.WebDriver;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.service.KrakenDriver;

import java.util.List;
import java.util.Optional;

import static ru.instamart.reforged.core.service.KrakenDriver.getWebDriver;

public interface Window {

    default void switchToNextWindow() {
        final WebDriver driver = getWebDriver();
        final String currentHandle = driver.getWindowHandle();
        final Optional<String> newTabHandle = driver.getWindowHandles()
                .stream()
                .filter(handle -> !handle.equals(currentHandle))
                .findFirst();
        newTabHandle.ifPresent(s -> driver.switchTo().window(s));
    }

    default void switchToFirstWindow() {
        final WebDriver driver = getWebDriver();
        final List<String> windowHandles = List.copyOf(driver.getWindowHandles());
        driver.switchTo().window(windowHandles.get(0));
    }

    default void closeAndSwitchToNextWindow() {
        if (getWebDriver().getWindowHandles().size() > 1) {
            Kraken.close();
        }
        switchToNextWindow();
    }

    default void closeAndSwitchToPrevWindow() {
        if (getWebDriver().getWindowHandles().size() > 1) {
            Kraken.close();
        }
        switchToFirstWindow();
    }
}
