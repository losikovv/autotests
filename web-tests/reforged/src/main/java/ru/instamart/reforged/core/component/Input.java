package ru.instamart.reforged.core.component;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.Kraken;

import static java.util.Objects.isNull;

@ToString(callSuper = true)
@Slf4j
public final class Input extends Component {

    public Input(final By by) {
        super(by);
    }

    public Input(final By by, final String description) {
        super(by, description);
    }

    public Input(final By by, final long timeout) {
        super(by, timeout);
    }

    public Input(final By by, final long timeout, final String description) {
        super(by, timeout, description);
    }

    public Input(final By by, final String description, final String errorMsg) {
        super(by, description, errorMsg);
    }

    public Input(final By by, final long timeout, final String description, final String errorMsg) {
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

    public void fill(final String data) {
        log.info("Fill {} with locator {} and data {}", getDescription(), getBy(), data);
        clear();
        getComponent().sendKeys(data);
    }

    /**
     * Устанавливает текст в инпут через js код
     *
     * @param data
     */
    public void jsFill(final String data) {
        log.info("JS Fill {} with locator {} and data {}", getDescription(), getBy(), data);
        Kraken.jsAction().setValue(component, data);
    }

    public void clear() {
        log.info("Clear input field");
        getComponent().clear();
    }

    public String getValue() {
        log.info("Get value");
        return getComponent().getAttribute("value");
    }
}
