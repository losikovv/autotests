package ru.instamart.reforged.core.component.condition;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.component.AbstractComponent;

import java.util.List;

@RequiredArgsConstructor
public final class ShouldBe {

    private final AbstractComponent component;

    public WebElement visible() {
        return Kraken.waitAction().shouldBeVisible(component);
    }

    public WebElement elementExists() {
        return Kraken.waitAction().shouldExist(component);
    }

    public List<WebElement> elementsExists() {
        return Kraken.waitAction().isElementsExist(component);
    }

    public WebElement clickable() {
        return Kraken.waitAction().shouldBeClickable(component);
    }
}
