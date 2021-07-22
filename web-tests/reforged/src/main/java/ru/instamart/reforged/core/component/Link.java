package ru.instamart.reforged.core.component;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.kraken.setting.Config;
import ru.instamart.reforged.core.Kraken;

import static java.util.Objects.isNull;

@ToString(callSuper = true)
@Slf4j
public final class Link extends Component {

    public Link(final By by) {
        super(by);
    }

    public Link(final By by, final String description) {
        super(by, description);
    }

    public Link(final By by, final long timeout) {
        super(by, timeout);
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

    public void click() {
        if (Config.USE_JS_CLICK) {
            jsClick();
        } else {
            log.info("Click {} with locator {}", getDescription(), getBy());
            getComponent().click();
        }
    }

    public String getText() {
        log.info("Get text {} with locator {}", getDescription(), getBy());
        return getComponent().getText();
    }
}
