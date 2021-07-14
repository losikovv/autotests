package ru.instamart.reforged.core.component;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.Kraken;

import static java.util.Objects.isNull;

@ToString(callSuper = true)
@Slf4j
public final class Link extends Component {

    public Link(final By by) {
        super(by);
    }

    public Link(final By by, final boolean isCashDisable) {
        super(by, isCashDisable);
    }

    public Link(final By by, final String description) {
        super(by, description);
    }

    public Link(final By by, final String description, final String errorMsg) {
        super(by, description, errorMsg);
    }

    @Override
    protected WebElement getComponent() {
        log.debug("Create {} with locator {}", getClass().getSimpleName(), getBy());
        if (isNull(component) || isCacheDisable) {
            component = Kraken.waitAction().shouldBeClickable(this);
        }
        return component;
    }

    public void click() {
        log.info("Click {} with locator {}", getClass().getSimpleName(), getBy());
        getComponent().click();
    }
}
