package ru.instamart.reforged.core.component;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.Kraken;

import java.util.function.Consumer;

@RequiredArgsConstructor
public final class Action {

    private final Component component;
    private final Consumer<WebElement> hover = (we) -> Kraken.action().moveToElement(we).perform();

    public void mouseOver() {
        hover.accept(component.getComponent());
    }
}
