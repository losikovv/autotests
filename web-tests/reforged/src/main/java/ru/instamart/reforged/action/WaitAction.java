package ru.instamart.reforged.action;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import ru.instamart.kraken.setting.Config;
import ru.instamart.reforged.core.component.Component;
import ru.instamart.ui.manager.AppManager;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
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

    public static List<WebElement> isOneOrMoreElementsExist(final Component component) {
        return createWait(component)
                .until((ExpectedCondition<List<WebElement>>) driver -> {
                    final List<WebElement> webElements = driver.findElements(component.getBy());
                    log.error("WE {}", webElements.size());
                    if (webElements.size() > 0) {
                        return webElements;
                    }
                    throw new NoSuchElementException("Elements not found or size == 0");
                });
    }

    public static boolean urlEquals(final String url) {
        return createWait(Config.BASIC_TIMEOUT, "Текущая страница отличается от ожидаемой")
                .until(ExpectedConditions.urlToBe(url));
    }

    public static boolean urlContains(final String url) {
        return createWait(Config.BASIC_TIMEOUT, "Текущая страница отличается от ожидаемой")
                .until(ExpectedConditions.urlContains(url));
    }

    private static FluentWait<WebDriver> createWait(final Component component) {
        return new FluentWait<>(AppManager.getWebDriver())
                .withTimeout(component.getTimeout(), TimeUnit.SECONDS)
                .withMessage(component.getErrorMsg())
                .pollingEvery(250, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(NotFoundException.class);
    }

    private static FluentWait<WebDriver> createWait(final int wait, final String errorMsg) {
        return new FluentWait<>(AppManager.getWebDriver())
                .withTimeout(wait, TimeUnit.SECONDS)
                .withMessage(errorMsg)
                .pollingEvery(250, TimeUnit.MILLISECONDS);
    }

    private WaitAction() {}
}
