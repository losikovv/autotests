package ru.instamart.reforged.core.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import ru.instamart.kraken.function.TriConsumer;
import ru.instamart.reforged.core.Kraken;

import java.util.function.Consumer;

@RequiredArgsConstructor
@Slf4j
public final class Actions {

    private final AbstractComponent component;
    private final Consumer<WebElement> hover = (we) -> Kraken.action().moveToElement(we).perform();
    private final Consumer<Keys> sendKeys = (key) -> Kraken.action().sendKeys(key).perform();
    private final TriConsumer<WebElement, Integer, Integer> clickWithOffset = (we, xOffset, yOffset) -> Kraken.action().moveToElement(we, xOffset, yOffset).click().perform();

    public void mouseOver() {
        log.debug("Element {} '{}' hover", component.getDescription(), component.getBy());
        hover.accept(component.getComponent());
    }

    public void sendKeys(final Keys keys) {
        log.debug("Send keys {} to element {}", keys, component.getDescription());
        sendKeys.accept(keys);
    }

    public void clickWithOffset(final int xOffset, final int yOffset) {
        log.debug("Click to element '{}' with offset x={}, y={}", component.getDescription(), xOffset, yOffset);
        clickWithOffset.accept(component.getComponent(), xOffset, yOffset);
    }

    /**
     * Наведение на элемент и клик через механизм Actions.MoveToElement()
     */
    public void moveToElementAndClick() {
        log.debug("Move to element and click {} '{}'", component.getDescription(), component.getBy());
        Kraken.action().moveToElement(component.getComponent()).click().perform();
    }

}
