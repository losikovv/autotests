package ru.instamart.reforged.core.component;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static java.util.Objects.isNull;

@ToString(callSuper = true)
@Slf4j
public final class DropDown extends CollectionComponent {

    public DropDown(final By by, final String description) {
        super(by, description);
    }

    public DropDown(final By by, final long timeout, final String description) {
        super(by, timeout, description);
    }

    public DropDown(final By by, final String description, final String errorMsg) {
        super(by, description, errorMsg);
    }

    public DropDown(final By by, final long timeout, final String description, final String errorMsg) {
        super(by, timeout, description, errorMsg);
    }

    @Override
    protected WebElement getComponent() {
        log.debug("Create {} with locator {}", getDescription(), getBy());
        if (isNull(component) || isCacheDisable) {
            component = shouldBe().clickable();
        }
        return component;
    }

    @Override
    protected List<WebElement> getComponents() {
        log.debug("Get {}'s with locator {}", getDescription(), getBy());
        if (isNull(components) || isCacheDisable) {
            components = shouldBe().elementsExists();
        }
        return components;
    }

    public void selectFirst() {
        log.debug("Select first {} with locator {}", getDescription(), getBy());
        final WebElement webElement = getComponents().stream().findFirst().orElseThrow();
        webElement.click();
    }

    public void selectAny() {
        log.debug("Select any {} with locator {}", getDescription(), getBy());
        final WebElement webElement = getComponents().stream().findAny().orElseThrow();
        webElement.click();
    }

    public void withText(final String text) {
        for (final var wb : getComponents()) {
            if (wb.getText().equals(text)) {
                wb.click();
                log.debug("Select {} with locator {} and text {}", getDescription(), getBy(), text);
                break;
            }
        }
    }

    public void containsText(final String text) {
        for (final var wb : getComponents()) {
            if (wb.getText().contains(text)) {
                log.debug("Select {} with locator {} and contain text {}", getDescription(), getBy(), text);
                wb.click();
                break;
            }
        }
    }

    public void click() {
        log.debug("Click {} with locator {}", getDescription(), getBy());
        getComponent().click();
    }
}
