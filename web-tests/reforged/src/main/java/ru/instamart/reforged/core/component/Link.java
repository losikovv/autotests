package ru.instamart.reforged.core.component;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.Kraken;

import static java.util.Objects.isNull;

@ToString(callSuper = true)
@Slf4j
public final class Link extends AbstractComponent {

    public Link(final By by, final String description) {
        super(by, description);
    }

    public Link(final By by, final long timeout, final String description) {
        super(by, timeout, description);
    }

    public Link(final By by, final String description, final String errorMsg) {
        super(by, description, errorMsg);
    }

    public Link(final By by, final long timeout, final String description, final String errorMsg) {
        super(by, timeout, description, errorMsg);
    }

    @Override
    protected WebElement getComponent() {
        log.debug("Create {} with locator {}", getDescription(), getBy());
        if (isNull(component) || isCacheDisable) {
            component = Kraken.waitAction().shouldBeClickable(this);
        }
        return component;
    }

    public void click(final Object... args) {
        setBy(ByKraken.xpath(((ByKraken) getBy()).getDefaultXpathExpression(), args));
        click();
    }

    public void click() {
        log.debug("Click {} with locator {}", getDescription(), getBy());
        getComponent().click();
    }

    public String getText() {
        final String text = getComponent().getText();
        log.debug("Get text '{}' for {} with locator {}", text, getDescription(), getBy());
        return text;
    }
}
