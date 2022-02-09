package ru.instamart.reforged.core.page;

import ru.instamart.reforged.core.Kraken;

import java.util.List;
import java.util.Optional;

import static ru.instamart.reforged.core.Kraken.getWebDriver;

public interface Window {

    default void switchToNextWindow() {
        final var driver = getWebDriver();
        final String currentHandle = driver.getWindowHandle();
        final Optional<String> newTabHandle = driver.getWindowHandles()
                .stream()
                .filter(handle -> !handle.equals(currentHandle))
                .findFirst();
        newTabHandle.ifPresent(s -> driver.switchTo().window(s));
    }

    default void switchToFirstWindow() {
        final var driver = getWebDriver();
        final var windowHandles = List.copyOf(driver.getWindowHandles());
        driver.switchTo().window(windowHandles.get(0));
    }

    default void closeAndSwitchToNextWindow() {
        if (getWebDriver().getWindowHandles().size() > 1) {
            Kraken.closeWindow();
        }
        switchToNextWindow();
    }

    default void closeAndSwitchToPrevWindow() {
        if (getWebDriver().getWindowHandles().size() > 1) {
            Kraken.closeWindow();
        }
        switchToFirstWindow();
    }
}
