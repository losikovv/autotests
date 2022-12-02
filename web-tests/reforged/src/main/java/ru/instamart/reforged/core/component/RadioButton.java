package ru.instamart.reforged.core.component;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@ToString(callSuper = true)
@Slf4j
public class RadioButton extends AbstractComponent {

    public RadioButton(final By by, final String description) {
        super(by, description);
    }

    public RadioButton(final By by, final long timeout, final String description) {
        super(by, timeout, description);
    }

    @Override
    public WebElement getComponent() {
        log.debug("getComponent {} with locator {}", getDescription(), getBy());
        return shouldBe().clickable();
    }

    public void set() {
        log.debug("Set {} with locator {}", getDescription(), getBy());
        getComponent().click();
    }

    public boolean radioButtonState() {
        log.debug("Get {} state", getDescription());
        return getComponent().isSelected();
    }
}
