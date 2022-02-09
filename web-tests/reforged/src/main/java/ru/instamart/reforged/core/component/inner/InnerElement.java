package ru.instamart.reforged.core.component.inner;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.Kraken;

@Slf4j
public final class InnerElement extends InnerComponent {

    public InnerElement(final WebElement webElement, final By by, final String description) {
        super(webElement, by, description);
    }

    @Override
    protected WebElement getComponent() {
        return Kraken.waitAction().shouldBeVisible(this, getWebElement());
    }

    public String getText() {
        log.debug("Get text {} with locator {}", getDescription(), getBy());
        return getComponent().getText();
    }
}
