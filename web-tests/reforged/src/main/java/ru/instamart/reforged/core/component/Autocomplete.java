package ru.instamart.reforged.core.component;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@ToString(callSuper = true)
@Slf4j
public final class Autocomplete extends AbstractComponent {

    public Autocomplete(final By by, final String description) {
        super(by, description);
    }

    public Autocomplete(final By by, final long timeout, final String description) {
        super(by, timeout, description);
    }

    @Override
    public WebElement getComponent() {
        log.debug("getComponent {} with locator {}", getDescription(), getBy());
        return shouldBe().clickable();
    }

    public void fill(final String data) {
        log.debug("Fill {} with locator {} and data {}", getDescription(), getBy(), data);
        getComponent().sendKeys(data);
    }
}
