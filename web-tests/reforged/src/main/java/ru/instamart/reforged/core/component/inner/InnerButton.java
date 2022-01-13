package ru.instamart.reforged.core.component.inner;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.Kraken;

@Slf4j
public final class InnerButton extends InnerComponent {

    public InnerButton(WebElement webElement, By by, final String description) {
        super(webElement, by, description);
    }

    @Override
    protected WebElement getComponent() {
        return Kraken.waitAction().shouldBeClickable(this, getWebElement());
    }

    public void click() {
        getComponent().click();
    }
}
