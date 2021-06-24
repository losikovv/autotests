package ru.instamart.reforged.core.component;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.action.WaitAction;

@ToString(callSuper = true)
@Slf4j
public final class Autocomplete extends Component {

    public Autocomplete(final By by) {
        super(by);
    }

    public Autocomplete(final By by, final String description) {
        super(by, description);
    }

    public Autocomplete(final By by, final String description, final String errorMsg) {
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

    public void fill(final String data) {
        getComponent().sendKeys(data);
        log.info("Fill {} with locator {} and data {}", getClass().getSimpleName(), getBy(), data);
    }
}
