package ru.instamart.reforged.core.component;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

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

    @Override
    public WebElement getComponent() {
        log.debug("getComponent {} with locator {}", getDescription(), getBy());
        return shouldBe().clickable();
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
        return new Select(getComponent());
    }
}
