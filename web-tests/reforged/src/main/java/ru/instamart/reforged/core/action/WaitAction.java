package ru.instamart.reforged.core.action;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.instamart.reforged.core.component.Component;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.core.condition.KrakenCondition;
import ru.instamart.reforged.core.wait.KrakenWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import static ru.instamart.reforged.core.Kraken.getWebDriver;

@Slf4j
public final class WaitAction {

    public WebElement shouldBeClickable(final Component component) {
        return createWait(component, "элемент должен быть кликабельным")
                .until(ExpectedConditions.elementToBeClickable(component.getBy()));
    }

    public WebElement shouldBeClickable(final Component component, final WebElement webElement) {
        return createWait(component, "элемент должен быть кликабельным")
                .until(KrakenCondition.elementToBeClickable(webElement, component.getBy()));
    }

    public void shouldNotBeClickable(final Component component, final Object... args) {
        createWait(component, "элемент не должен быть кликабельным")
                .until(KrakenCondition.elementNotToBeClickable(component.getBy(args)));
    }

    public WebElement shouldBeVisible(final Component component, final WebElement webElement) {
        return createWait(component, "элемент должен отображаться")
                .until(KrakenCondition.visibilityOfElementLocated(webElement, component.getBy()));
    }

    public WebElement shouldBeVisible(final Component component, final Object... args) {
        return createWait(component, "элемент должен отображаться")
                .until(ExpectedConditions.visibilityOfElementLocated(component.getBy(args)));
    }

    public WebElement elementOfCollectionShouldBeVisible(final ElementCollection elementCollection, final int elementIndex) {
        return createWait(elementCollection, "элементы коллекции должны быть видимы")
                .until(ExpectedConditions.visibilityOf(elementCollection.getElements().get(elementIndex)));
    }

    public void elementOfCollectionShouldNotBeVisible(final ElementCollection elementCollection, final int elementIndex) {
        createWait(elementCollection, "элемент коллекции не должен отображаться")
                .until(ExpectedConditions.invisibilityOf(elementCollection.getElements().get(elementIndex)));
    }

    public WebElement shouldExist(final Component component) {
        return createWait(component, "элемент должен быть в DOM")
                .until(ExpectedConditions.presenceOfElementLocated(component.getBy()));
    }

    public WebElement shouldExist(final Component component, final WebElement webElement) {
        return createWait(component, "элемент должен быть в DOM")
                .until(KrakenCondition.presentsOfElementLocated(webElement, component.getBy()));
    }

    public void shouldNotBeAnimated(final Component component, final Object... args) {
        createWait(component, "анимация у элемента должна закончиться")
                .until(KrakenCondition.steadinessOfElementLocated(component.getBy(args)));
    }

    public boolean shouldNotBeVisible(final Component component, final WebElement webElement) {
        return createWait(component, "элемент не должен быть видим")
                .until(KrakenCondition.invisibilityOfElementLocated(webElement, component.getBy()));
    }

    public boolean shouldNotBeVisible(final Component component, final Object... args) {
        return createWait(component, "элемент не должен быть видим")
                .until(ExpectedConditions.invisibilityOfElementLocated(component.getBy(args)));
    }

    public boolean isVisible(final Component component) {
        return createWait(component, "элемент должен быть видим")
                .until(KrakenCondition.visibilityOfElementLocated(component.getBy()));
    }

    public boolean isInvisible(final Component component) {
        return createWait(component, "элемент не должен быть видим")
                .until(ExpectedConditions.invisibilityOfElementLocated(component.getBy()));
    }

    public List<WebElement> isElementsExist(final Component component) {
        return createWait(component, "коллекция элементов должна быть видима")
                .until(KrakenCondition.elementCollectionPresent(component.getBy()));
    }

    public List<WebElement> isElementsExist(final Component component, final WebElement webElement) {
        return createWait(component, "коллекция элементов должна быть видима")
                .until(KrakenCondition.elementCollectionPresent(component.getBy(), webElement));
    }

    public void isElementsShouldNotBeExist(final Component component) {
        createWait(component, "коллекции элементов не должно быть")
                .until(KrakenCondition.elementCollectionNotPresent(component.getBy()));
    }

    public void elementSelectCheckboxState(final WebElement element, final boolean selected) {
        createWait("Состояние чекбокса, отличается от ожидаемого")
                .until(KrakenCondition.elementSelectCheckboxState(element, selected));
    }

    public void urlEquals(final String url) {
        createWait(String.format("Текущая страница: %s отличается от ожидаемой: %s", getWebDriver().getCurrentUrl(), url))
                .until(ExpectedConditions.urlToBe(url));
    }

    public void urlContains(final String url) {
        createWait(String.format("Текущая страница: %s не содержит ожидаемого url: %s", getWebDriver().getCurrentUrl(), url))
                .until(ExpectedConditions.urlContains(url));
    }

    public boolean containText(final Component component, final String text, final Object... args) {
        return createWait(component, String.format("должен присутствовать текс: %s", text))
                .until(ExpectedConditions.textToBePresentInElementLocated(component.getBy(args), text));
    }

    public boolean containTextFromAttribute(final Component component, final String attribute, final String text, final Object... args) {
        return createWait(component, String.format("должен присутствовать текст: %s в атрибуте %s", text, attribute))
                .until(KrakenCondition.textToBePresentInAttributeLocated(component.getBy(args), attribute, text));
    }

    public void frameShouldBeVisible(final int frame) {
        createWait("Фрейм не загрузился")
                .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));
    }

    public void fillField(final WebElement element, final String data, final boolean isPhone) {
        createWait("Текущее содержимое поля отличается от ожидаемого")
                .until(KrakenCondition.keysSendCondition(element, data, isPhone));
    }

    public void elementCollectionSizeShouldBeEqual(ElementCollection collection, final int size) {
        createWait(collection, String.format("Кол-во элементов в коллекции: %s не совпадает с ожидаемым: %d", collection.elementCount(), size))
                .until((ExpectedCondition<Boolean>) wb -> collection.elementCount() == size);
    }

    public void cookieShouldBeExist(final String data) {
        createWait(String.format("Нет куки с таким именем: %s", data))
                .until(KrakenCondition.cookieExist(data));
    }

    public void cookiesShouldBeExist(final Set<String> data) {
        createWait(String.format("Нет куки с таким именем: %s", String.join(",", data)))
                .until(KrakenCondition.cookiesExist(data));
    }

    private KrakenWait<WebDriver> createWait(final String errorConditionMsg) {
        return new KrakenWait<>(getWebDriver())
                .withMessage(errorConditionMsg)
                .ignoring(NoSuchElementException.class)
                .ignoring(ElementClickInterceptedException.class)
                .ignoring(NotFoundException.class);
    }

    private KrakenWait<WebDriver> createWait(final Component component, final String errorConditionMsg) {
        return new KrakenWait<>(getWebDriver())
                .withTimeout(Duration.ofSeconds(component.getTimeout()))
                .withMessage(component.getErrorMsg() + errorConditionMsg)
                .ignoring(NoSuchElementException.class)
                .ignoring(ElementClickInterceptedException.class)
                .ignoring(NotFoundException.class);
    }
}
