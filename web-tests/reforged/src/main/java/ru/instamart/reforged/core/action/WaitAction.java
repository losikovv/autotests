package ru.instamart.reforged.core.action;

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

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static ru.instamart.reforged.core.service.KrakenDriver.getWebDriver;

@Slf4j
public enum WaitAction {

    INSTANCE;

    public WebElement shouldBeClickable(final Component component) {
        return createWait(component)
                .until(ExpectedConditions.elementToBeClickable(component.getBy()));
    }

    public WebElement shouldBeVisible(final Component component) {
        return createWait(component)
                .until(ExpectedConditions.visibilityOfElementLocated(component.getBy()));
    }

    public boolean shouldNotBeVisible(final Component component) {
        return createWait(component)
                .until(ExpectedConditions.invisibilityOfElementLocated(component.getBy()));
    }

    public List<WebElement> isElementsExist(final Component component) {
        return createWait(component)
                .until((ExpectedCondition<List<WebElement>>) driver -> {
                    final List<WebElement> webElements = driver.findElements(component.getBy());
                    if (webElements.size() > 0) {
                        return webElements;
                    }
                    throw new NoSuchElementException("Elements not found or size < 1");
                });
    }

    public boolean urlEquals(final String url) {
        return createWait(Config.BASIC_TIMEOUT, "Текущая страница отличается от ожидаемой")
                .until(ExpectedConditions.urlToBe(url));
    }

    public boolean urlContains(final String url) {
        return createWait(Config.BASIC_TIMEOUT, "Текущая страница отличается от ожидаемой")
                .until(ExpectedConditions.urlContains(url));
    }

    public void frameShouldBeVisible(final int frame) {
        createWait(Config.BASIC_TIMEOUT, "Фрейм не загрузился")
                .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));
    }

    private FluentWait<WebDriver> createWait(final Component component) {
        return new FluentWait<>(getWebDriver())
                .withTimeout(component.getTimeout(), TimeUnit.SECONDS)
                .withMessage(component.getErrorMsg())
                .pollingEvery(Config.POLLING_INTERVAL, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(NotFoundException.class);
    }

    private FluentWait<WebDriver> createWait(final int wait, final String errorMsg) {
        return new FluentWait<>(getWebDriver())
                .withTimeout(wait, TimeUnit.SECONDS)
                .withMessage(errorMsg)
                .pollingEvery(Config.POLLING_INTERVAL, TimeUnit.MILLISECONDS);
    }

    public WaitAction getInstance() {
        return INSTANCE;
    }
}
