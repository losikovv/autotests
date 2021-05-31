package ru.instamart.reforged.action;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import ru.instamart.kraken.setting.Config;
import ru.instamart.reforged.stf.component.Component;
import ru.instamart.ui.manager.AppManager;

import java.util.List;
import java.util.concurrent.TimeUnit;

public final class WaitAction {

    public static WebElement shouldBeClickable(final Component component) {
        return createWait(component)
                .until(ExpectedConditions.elementToBeClickable(component.getBy()));
    }

    public static WebElement shouldBeVisible(final Component component) {
        return createWait(component)
                .until(ExpectedConditions.visibilityOfElementLocated(component.getBy()));
    }

    public static boolean shouldNotBeVisible(final Component component) {
        return createWait(component)
                .until(ExpectedConditions.invisibilityOfElementLocated(component.getBy()));
    }

    public static List<WebElement> isElementsExist(final Component component) {
        return createWait(component)
                .until((ExpectedCondition<List<WebElement>>) driver -> {
                    final List<WebElement> webElements = driver.findElements(component.getBy());
                    if (webElements.size() > 1) {
                        return webElements;
                    }
                    throw new NoSuchElementException("Elements not found or size <= 1");
                });
    }

    public static void urlToBe(final String url) {
        new FluentWait<>(AppManager.getWebDriver())
                .withTimeout(Config.BASIC_TIMEOUT, TimeUnit.SECONDS)
                .pollingEvery(250, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.urlToBe(url));
    }

    private static FluentWait<WebDriver> createWait(final Component component) {
        return new FluentWait<>(AppManager.getWebDriver())
                .withTimeout(component.getTimeout(), TimeUnit.SECONDS)
                .withMessage(component.getErrorMsg())
                .pollingEvery(250, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(NotFoundException.class);
    }

    private WaitAction() {}
}
