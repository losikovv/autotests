package ru.instamart.reforged.core.action;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import ru.instamart.reforged.core.component.Component;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.core.condition.KrakenCondition;
import ru.instamart.reforged.core.config.WaitProperties;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static ru.instamart.reforged.core.Kraken.getWebDriver;

@Slf4j
public final class WaitAction {

    public WebElement shouldBeClickable(final Component component) {
        return createWait(component)
                .until(ExpectedConditions.elementToBeClickable(component.getBy()));
    }

    public WebElement shouldBeClickable(final Component component, final WebElement webElement) {
        return createWait(component)
                .until(KrakenCondition.elementToBeClickable(webElement, component.getBy()));
    }

    public WebElement shouldBeVisible(final Component component) {
        return createWait(component)
                .until(ExpectedConditions.visibilityOfElementLocated(component.getBy()));
    }

    public WebElement shouldBeVisible(final Component component, final WebElement webElement) {
        return createWait(component)
                .until(KrakenCondition.visibilityOfElementLocated(webElement, component.getBy()));
    }

    public WebElement shouldBeVisible(final Component component, final Object... args) {
        return createWait(component)
                .until(ExpectedConditions.visibilityOfElementLocated(component.getBy(args)));
    }

    public void shouldNotBeAnimated(final Component component) {
        createWait(component)
                .until(KrakenCondition.steadinessOfElementLocated(component.getBy()));
    }

    public boolean shouldNotBeVisible(final Component component) {
        return createWait(component)
                .until(ExpectedConditions.invisibilityOfElementLocated(component.getBy()));
    }

    public boolean shouldNotBeVisible(final Component component, final WebElement webElement) {
        return createWait(component)
                .until(KrakenCondition.invisibilityOfElementLocated(webElement, component.getBy()));
    }

    public boolean shouldNotBeVisible(final Component component, final Object... args) {
        return createWait(component)
                .until(ExpectedConditions.invisibilityOfElementLocated(component.getBy(args)));
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

    public List<WebElement> isElementsExist(final Component component, final WebElement webElement) {
        return createWait(component)
                .until((ExpectedCondition<List<WebElement>>) driver -> {
                    final List<WebElement> webElements = webElement.findElements(component.getBy());
                    if (webElements.size() > 0) {
                        return webElements;
                    }
                    throw new NoSuchElementException("Elements not found or size < 1");
                });
    }

    public void elementSelectCheckboxState(final WebElement element, final boolean selected) {
        createWait(WaitProperties.BASIC_TIMEOUT, "Состояние чекбокса, отличается от ожидаемого")
                .until(KrakenCondition.elementSelectCheckboxState(element, selected));
    }

    public void shouldNotBeClickable(final Component component, final Object... args) {
        createWait(component)
                .until(KrakenCondition.elementNotToBeClickable(component.getBy(args)));
    }

    public void urlEquals(final String url) {
        createWait(WaitProperties.BASIC_TIMEOUT, "Текущая страница отличается от ожидаемой")
                .until(ExpectedConditions.urlToBe(url));
    }

    public void urlContains(final String url) {
        createWait(WaitProperties.BASIC_TIMEOUT, "Текущая страница отличается от ожидаемой")
                .until(ExpectedConditions.urlContains(url));
    }

    public void frameShouldBeVisible(final int frame) {
        createWait(WaitProperties.BASIC_TIMEOUT, "Фрейм не загрузился")
                .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));
    }

    public void fillField(final WebElement element, final String data, final boolean isPhone) {
        createWait(WaitProperties.BASIC_TIMEOUT, "Текущее содержимое поля отличается от ожидаемого")
                .until(KrakenCondition.keysSendCondition(element, data, isPhone));
    }

    public void elementCollectionSizeShouldBeEqual(ElementCollection collection, final int size) {
        createWait(WaitProperties.BASIC_TIMEOUT, "Кол-во элементов в коллекции не совпадает с ожидаемым")
                .until((ExpectedCondition<Boolean>) wb -> collection.elementCount() == size);
    }

    public void cookieShouldBeExist(final String data) {
        createWait(WaitProperties.BASIC_TIMEOUT, "Нет куки с таким именем: " + data)
                .until(KrakenCondition.cookieExist(data));
    }

    public void cookiesShouldBeExist(final Set<String> data) {
        createWait(WaitProperties.BASIC_TIMEOUT, "Нет куки с таким именем: " + String.join(",", data))
                .until(KrakenCondition.cookiesExist(data));
    }

    private FluentWait<WebDriver> createWait(final int wait, final String errorMsg) {
        return new FluentWait<>(getWebDriver())
                .withTimeout(wait, TimeUnit.SECONDS)
                .withMessage(errorMsg)
                .pollingEvery(WaitProperties.POLLING_INTERVAL, TimeUnit.MILLISECONDS);
    }

    private FluentWait<WebDriver> createWait(final Component component) {
        return new FluentWait<>(getWebDriver())
                .withTimeout(component.getTimeout(), TimeUnit.SECONDS)
                .withMessage(component.getErrorMsg())
                .pollingEvery(WaitProperties.POLLING_INTERVAL, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(ElementClickInterceptedException.class)
                .ignoring(NotFoundException.class);
    }
}
