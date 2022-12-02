package ru.instamart.reforged.core.component;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.ByKraken;

@ToString(callSuper = true)
@Slf4j
public final class Button extends AbstractComponent {

    public Button(final By by, final String description) {
        super(by, description);
    }

    public Button(final By by, final long timeout, final String description) {
        super(by, timeout, description);
    }

    @Override
    public WebElement getComponent() {
        log.debug("getComponent {} with locator {}", getDescription(), getBy());
        return shouldBe().clickable();
    }

    public synchronized void click(final Object... args) {
        setBy(ByKraken.xpathExpression(((ByKraken)getBy()).getDefaultXpathExpression(), args));
        click();
    }

    public void click() {
        log.debug("Click {} with locator {}", getDescription(), getBy());
        getComponent().click();
    }

    public String getText() {
        log.debug("Get text {} with locator {}", getDescription(), getBy());
        return getComponent().getText();
    }
}
