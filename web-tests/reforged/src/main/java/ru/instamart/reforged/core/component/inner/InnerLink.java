package ru.instamart.reforged.core.component.inner;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.Kraken;

@Slf4j
public final class InnerLink extends InnerComponent {

    public InnerLink(WebElement webElement, By by, final String description) {
        super(webElement, by, description);
    }

    @Override
    protected WebElement getComponent() {
        return Kraken.waitAction().shouldBeClickable(this, getWebElement());
    }

    public void click() {
        log.debug("Click {} with locator {}", getDescription(), getBy());
        getComponent().click();
    }
}
