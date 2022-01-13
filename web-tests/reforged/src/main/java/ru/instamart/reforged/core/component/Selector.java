package ru.instamart.reforged.core.component;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import ru.instamart.reforged.core.Kraken;

import static java.util.Objects.isNull;

@ToString(callSuper = true)
@Slf4j
public final class Selector extends AbstractComponent {

    private Select select;

    public Selector(final By by, final String description) {
        super(by, description);
    }

    public Selector(final By by, final long timeout, final String description) {
        super(by, timeout, description);
    }

    public Selector(final By by, final String description, final String errorMsg) {
        super(by, description, errorMsg);
    }

    public Selector(final By by, final long timeout, final String description, final String errorMsg) {
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

    public void selectByText(final String text) {
        log.debug("Select {} with locator {} and text {}", getDescription(), getBy(), text);
        getSelect().selectByVisibleText(text);
    }

    public void selectByIndex(final int index) {
        log.debug("Select {} with locator {} and index {}", getDescription(), getBy(), index);
        getSelect().selectByIndex(index);
    }

    private Select getSelect() {
        log.debug("Create Select with locator {}", getBy());
        if (isNull(component)) {
            select = new Select(getComponent());
        }
        return select;
    }
}
