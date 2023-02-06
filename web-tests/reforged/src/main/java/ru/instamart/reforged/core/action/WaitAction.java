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
import java.util.regex.Pattern;

import static ru.instamart.reforged.core.Kraken.getWebDriver;

@Slf4j
public final class WaitAction {

    //######################## Clickable ########################
    public WebElement shouldBeClickable(final Component component) {
        return createWait(component, "элемент должен быть кликабельным")
                .until(ExpectedConditions.elementToBeClickable(component.getBy()));
    }

    public WebElement shouldBeClickable(final Component component, final WebElement webElement) {
        return createWait(component, "элемент должен быть кликабельным")
                .until(KrakenCondition.elementToBeClickable(webElement, component.getBy()));
    }

    //######################## Not Clickable ########################
    public boolean isUnclickable(final Component component, final Object... args) {
        return createWait(component, "элемент не должен быть кликабельным")
                .until(KrakenCondition.elementNotToBeClickable(component.getBy(args)));
    }

    //######################## Visible ########################
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

    public boolean isVisible(final Component component) {
        return createWait(component, "элемент должен быть видим")
                .until(KrakenCondition.visibilityOfElementLocated(component.getBy()));
    }

    public void frameShouldBeVisible(final int frame) {
        createWait("Фрейм не загрузился")
                .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame));
    }

    public void tabOpenCount(final int tabCount) {
        createWait("Таб не загрузился").until(ExpectedConditions.numberOfWindowsToBe(tabCount));
    }

    //######################## Not Visible ########################
    public boolean elementOfCollectionShouldNotBeVisible(final ElementCollection elementCollection, final int elementIndex) {
        return createWait(elementCollection, "элемент коллекции не должен отображаться")
                .until(ExpectedConditions.invisibilityOf(elementCollection.getElements().get(elementIndex)));
    }

    public boolean isInvisible(final Component component, final WebElement webElement) {
        return createWait(component, "элемент не должен быть видим")
                .until(KrakenCondition.invisibilityOfElementLocated(webElement, component.getBy()));
    }

    public boolean isInvisible(final Component component, final Object... args) {
        return createWait(component, "элемент не должен быть видим")
                .until(ExpectedConditions.invisibilityOfElementLocated(component.getBy(args)));
    }

    //######################## Exist ########################
    public WebElement shouldExist(final Component component) {
        return createWait(component, "элемент должен быть в DOM")
                .until(ExpectedConditions.presenceOfElementLocated(component.getBy()));
    }

    public WebElement shouldExist(final Component component, final WebElement webElement) {
        return createWait(component, "элемент должен быть в DOM")
                .until(KrakenCondition.presentsOfElementLocated(webElement, component.getBy()));
    }

    public List<WebElement> isElementsExist(final Component component) {
        return createWait(component, "коллекция элементов должна быть видима")
                .until(KrakenCondition.elementCollectionPresent(component.getBy()));
    }

    public List<WebElement> isElementsExist(final Component component, final WebElement webElement) {
        return createWait(component, "коллекция элементов должна быть видима")
                .until(KrakenCondition.elementCollectionPresent(component.getBy(), webElement));
    }

    //######################## Not Exist ########################
    public boolean isElementsShouldNotBeExist(final ElementCollection component) {
        return createWait(component, "коллекции элементов не должно быть")
                .until(KrakenCondition.elementCollectionNotPresent(component.getBy()));
    }

    //######################## Not Animated ########################
    public boolean isAnimationFinished(final Component component, final Object... args) {
        return createWait(component, "анимация у элемента должна закончиться")
                .until(KrakenCondition.steadinessOfElementLocated(component.getBy(args)));
    }

    //######################## CheckState ########################
    public void elementSelectCheckboxState(final WebElement element, final boolean selected) {
        createWait("Состояние чекбокса, отличается от ожидаемого")
                .until(KrakenCondition.elementSelectCheckboxState(element, selected));
    }

    //######################## Url equals ########################
    public boolean isUrlEquals(final String url) {
        return createWait(String.format("Текущая страница: %s отличается от ожидаемой: %s", getWebDriver().getCurrentUrl(), url))
                .until(ExpectedConditions.urlToBe(url));
    }

    //######################## Url Contains ########################
    public boolean isUrlContains(final String url) {
        return createWait(String.format("Текущая страница: %s не содержит ожидаемого url: %s", getWebDriver().getCurrentUrl(), url))
                .until(ExpectedConditions.urlContains(url));
    }

    //######################## Contains Text ########################
    public boolean textMatches(final Component component, final Pattern pattern, final Object... args) {
        return createWait(component, String.format("текст элемента должен соответствовать паттерну: %s", pattern))
                .until(ExpectedConditions.textMatches(component.getBy(args), pattern));
    }

    public boolean containText(final Component component, final String text, final Object... args) {
        return createWait(component, String.format("должен присутствовать текст: %s", text))
                .until(ExpectedConditions.textToBePresentInElementLocated(component.getBy(args), text));
    }

    public boolean containTextFromAttribute(final Component component, final String attribute, final String text, final Object... args) {
        return createWait(component, String.format("должен присутствовать текст: %s в атрибуте %s", text, attribute))
                .until(KrakenCondition.textToBePresentInAttributeLocated(component.getBy(args), attribute, text));
    }

    public void fillField(final Component component, final String data, final boolean isPhone) {
        createWait("Текущее содержимое поля отличается от ожидаемого")
                .until(KrakenCondition.keysSendCondition(component.getBy(), data, isPhone));
    }

    public boolean isElementCollectionSizeEqual(final ElementCollection collection, final int size) {
        return createWait(collection, String.format("Кол-во элементов в коллекции: %s не совпадает с ожидаемым: %d", collection.elementCount(), size))
                .until((ExpectedCondition<Boolean>) wb -> collection.elementCount() == size);
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
