package ru.instamart.reforged.core.component;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.Kraken;

import java.util.List;

import static java.util.Objects.isNull;

@ToString(callSuper = true)
@Slf4j
public final class DropDown extends CollectionComponent {

    public DropDown(final By by) {
        super(by);
    }

    public DropDown(final By by, final String description) {
        super(by, description);
    }

    public DropDown(final By by, final long timeout) {
        super(by, timeout);
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

    public void selectFirst() {
        log.info("Select first {} with locator {}", getDescription(), getBy());
        final WebElement webElement = getComponents().stream().findFirst().orElseThrow();
        webElement.click();
    }

    public void selectAny() {
        log.info("Select any {} with locator {}", getDescription(), getBy());
        final WebElement webElement = getComponents().stream().findAny().orElseThrow();
        webElement.click();
    }

    public void withText(final String text) {
        for (final WebElement wb : getComponents()) {
            if (wb.getText().equals(text)) {
                wb.click();
                log.info("Select {} with locator {} and text {}", getDescription(), getBy(), text);
                break;
            }
        }
    }

    public void containsText(final String text) {
        for (final WebElement wb : getComponents()) {
            if (wb.getText().contains(text)) {
                log.info("Select {} with locator {} and contain text {}", getDescription(), getBy(), text);
                wb.click();
                break;
            }
        }
    }

    @Override
    protected List<WebElement> getComponents() {
        log.debug("Get {}'s with locator {}", getDescription(), getBy());
        if (isNull(components) || isCacheDisable) {
            components = Kraken.waitAction().isElementsExist(this);
        }
        return components;
    }
}
