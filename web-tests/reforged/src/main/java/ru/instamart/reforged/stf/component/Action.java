package ru.instamart.reforged.stf.component;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.function.Consumer;

import static ru.instamart.ui.manager.AppManager.getWebDriver;

@RequiredArgsConstructor
public final class Action {

    private final Component component;
    private final Actions actions = new Actions(getWebDriver());
    private final Consumer<WebElement> hover = (we) -> actions.moveToElement(we).perform();

    public void mouseOver() {
        hover.accept(component.getComponent());
    }
}
