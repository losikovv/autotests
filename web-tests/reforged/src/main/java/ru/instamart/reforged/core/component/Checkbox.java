package ru.instamart.reforged.core.component;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.Kraken;

@ToString(callSuper = true)
@Slf4j
public final class Checkbox extends AbstractComponent {

    public Checkbox(final By by, final String description) {
        super(by, description);
    }

    public Checkbox(final By by, final long timeout, final String description) {
        super(by, timeout, description);
    }

    @Override
    public WebElement getComponent() {
        log.debug("getComponent {} with locator {}", getDescription(), getBy());
        return shouldBe().clickable();
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
