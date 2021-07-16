package ru.instamart.reforged.core.component;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.Kraken;

import static java.util.Objects.isNull;

@ToString(callSuper = true)
@Slf4j
public class RadioButton extends Component {

    public RadioButton(final By by) {
        super(by);
    }

    public RadioButton(final By by, final boolean isCashDisable) {
        super(by, isCashDisable);
    }

    public RadioButton(final By by, final String description) {
        super(by, description);
    }

    public RadioButton(final By by, final String description, final String errorMsg) {
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

    public void set() {
        log.info("Set {} with locator {}", getClass().getSimpleName(), getBy());
        getComponent().click();
    }
}
