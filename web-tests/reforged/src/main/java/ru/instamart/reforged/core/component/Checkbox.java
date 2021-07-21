package ru.instamart.reforged.core.component;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.Kraken;

import static java.util.Objects.isNull;

@ToString(callSuper = true)
@Slf4j
public final class Checkbox extends Component {

    public Checkbox(final By by) {
        super(by);
    }

    public Checkbox(final By by, final String description) {
        super(by, description);
    }

    public Checkbox(final By by, final long timeout) {
        super(by, timeout);
    }

    public Checkbox(final By by, final long timeout, final String description) {
        super(by, timeout, description);
    }

    public Checkbox(final By by, final String description, final String errorMsg) {
        super(by, description, errorMsg);
    }

    public Checkbox(final By by, final long timeout, final String description, final String errorMsg) {
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

    public void check() {
        if (getComponent().isSelected()) {
            log.info("Checkbox {} with locator {} is checked before!", getDescription(), getBy());
        } else {
            log.info("Check {} with locator {}", getDescription(), getBy());
            getComponent().click();
        }
    }

    public void uncheck() {
        if (!getComponent().isSelected()) {
            log.info("Checkbox {} with locator {} is unchecked before!", getDescription(), getBy());
        } else {
            log.info("Uncheck {} with locator {}", getDescription(), getBy());
            getComponent().click();
        }
    }
}
