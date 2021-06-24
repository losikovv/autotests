package ru.instamart.reforged.core.component;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.action.WaitAction;

@ToString(callSuper = true)
@Slf4j
public final class Checkbox extends Component {

    public Checkbox(final By by) {
        super(by);
    }

    public Checkbox(final By by, final String description) {
        super(by, description);
    }

    public Checkbox(final By by, final String description, final String errorMsg) {
        super(by, description, errorMsg);
    }

    @Override
    protected WebElement getComponent() {
        log.info("Create {} with locator {}", getClass().getSimpleName(), getBy());
        if (component == null) {
            component = WaitAction.shouldBeClickable(this);
        }
        return component;
    }

    public void check() {
        getComponent().click();
        log.info("Check {} with locator {}", getClass().getSimpleName(), getBy());
    }

    public void uncheck() {
        getComponent().click();
        log.info("Uncheck {} with locator {}", getClass().getSimpleName(), getBy());
    }
}
