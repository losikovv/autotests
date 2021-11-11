package ru.instamart.reforged.core.component;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import ru.instamart.kraken.function.TriConsumer;
import ru.instamart.reforged.core.Kraken;

import java.util.function.Consumer;

@RequiredArgsConstructor
public final class Actions {

    private final Component component;
    private final Consumer<WebElement> hover = (we) -> Kraken.action().moveToElement(we).perform();
    private final Consumer<Keys> sendKeys = (key) -> Kraken.action().sendKeys(key).perform();
    private final TriConsumer<WebElement, Integer, Integer> clickWithOffset = (we, xOffset, yOffset) -> Kraken.action().moveToElement(we, xOffset, yOffset).click().perform();

    public void mouseOver() {
        hover.accept(component.getComponent());
    }

    public void sendKeys(final Keys keys) {
        sendKeys.accept(keys);
    }

    public void clickWithOffset(final int xOffset, final int yOffset) {
        clickWithOffset.accept(component.getComponent(),xOffset, yOffset);
    }
}
