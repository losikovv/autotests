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

    public Input(final By by, final boolean isCashDisable) {
        super(by, isCashDisable);
    }

    public Input(final By by, final String description) {
        super(by, description);
    }

    public Input(final By by, final String description, final String errorMsg) {
        super(by, description, errorMsg);
    }

    @Override
    protected WebElement getComponent() {
        log.debug("Create {} with locator {}", getClass().getSimpleName(), getBy());
        if (isNull(component) || isCashDisable) {
            component = Kraken.waitAction().shouldBeClickable(this);
        }
        return component;
    }

    public void fill(final String data) {
        clear();
        getComponent().sendKeys(data);
        log.info("Fill {} with locator {} and data {}", getClass().getSimpleName(), getBy(), data);
    }

    public void clear() {
        getComponent().clear();
        log.info("Clear input field");
    }
}
