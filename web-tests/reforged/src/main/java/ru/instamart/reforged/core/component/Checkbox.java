package ru.instamart.reforged.core.component;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.Kraken;

import static java.util.Objects.isNull;

@ToString(callSuper = true)
@Slf4j
public final class Checkbox extends AbstractComponent {

    public Checkbox(final By by, final String description) {
        super(by, description);
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
        if (isNull(component) || isCacheDisable) {
            log.debug("Create {} with locator {}", getDescription(), getBy());
            component = shouldBe().clickable();
        }
        return component;
    }

    public void check() {
        Kraken.waitAction().elementSelectCheckboxState(getComponent(), true);
    }

    public void uncheck() {
        Kraken.waitAction().elementSelectCheckboxState(getComponent(), false);
    }

    public boolean checkboxState() {
        log.debug("Get {} state", getDescription());
        return getComponent().isSelected();
    }
}
