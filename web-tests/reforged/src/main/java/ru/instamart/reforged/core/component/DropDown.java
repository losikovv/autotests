package ru.instamart.reforged.core.component;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.action.WaitAction;

import java.util.List;

import static java.util.Objects.isNull;

@ToString(callSuper = true)
@Slf4j
public final class DropDown extends CollectionComponent {

    public DropDown(final By by) {
        super(by);
    }

    public DropDown(final By by, final boolean isCashDisable) {
        super(by, isCashDisable);
    }

    public DropDown(final By by, final String description) {
        super(by, description);
    }

    public DropDown(final By by, final String description, final String errorMsg) {
        super(by, description, errorMsg);
    }

    public void selectFirst() {
        final WebElement webElement = getComponents().stream().findFirst().orElseThrow();
        webElement.click();
        log.info("Select first {} with locator {}", getClass().getSimpleName(), getBy());
    }

    public void selectAny() {
        final WebElement webElement = getComponents().stream().findAny().orElseThrow();
        webElement.click();
        log.info("Select any {} with locator {}", getClass().getSimpleName(), getBy());
    }

    public void withText(final String text) {
        for (final WebElement wb : getComponents()) {
            if (wb.getText().equals(text)) {
                wb.click();
                log.info("Select {} with locator {} and text {}", getClass().getSimpleName(), getBy(), text);
                return;
            }
        }
    }

    public void containsText(final String text) {
        for (final WebElement wb : getComponents()) {
            if (wb.getText().contains(text)) {
                wb.click();
                log.info("Select {} with locator {} and contain text {}", getClass().getSimpleName(), getBy(), text);
            }
        }
    }

    @Override
    protected List<WebElement> getComponents() {
        log.info("Get {}'s with locator {}", getClass().getSimpleName(), getBy());
        if (isNull(components) || isCashDisable) {
            components = WaitAction.isElementsExist(this);
        }
        return components;
    }
}
