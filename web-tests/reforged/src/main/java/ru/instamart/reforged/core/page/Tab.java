package ru.instamart.reforged.core.page;

import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.Optional;

import static ru.instamart.ui.manager.AppManager.getWebDriver;

public interface Tab {

    default void switchToNextTab() {
        final WebDriver driver = getWebDriver();
        final String currentTabHandle = driver.getWindowHandle();
        final Optional<String> newTabHandle = driver.getWindowHandles()
                .stream()
                .filter(handle -> !handle.equals(currentTabHandle))
                .findFirst();
        newTabHandle.ifPresent(s -> driver.switchTo().window(s));
    }

    default void switchToFirstTab() {
        final WebDriver driver = getWebDriver();
        final List<String> windowHandles = List.copyOf(driver.getWindowHandles());
        driver.switchTo().window(windowHandles.get(0));
    }

    default void closeAndSwitchToNextTab() {
        getWebDriver().close();
        switchToNextTab();
    }

    default void closeAndSwitchToPrevTab() {
        getWebDriver().close();
        switchToFirstTab();
    }
}
